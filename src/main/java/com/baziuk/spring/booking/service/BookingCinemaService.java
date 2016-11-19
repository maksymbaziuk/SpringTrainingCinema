package com.baziuk.spring.booking.service;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.booking.dao.BookingDAO;
import com.baziuk.spring.discount.bean.Discount;
import com.baziuk.spring.discount.service.DiscountService;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.EventRating;
import com.baziuk.spring.events.bean.Show;
import com.baziuk.spring.user.bean.User;
import org.apache.commons.lang3.ArrayUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Maks on 9/25/16.
 */
public class BookingCinemaService implements BookingService {

    private BookingDAO bookingDAO;

    private DiscountService discountService;
    private double vipMultiplier;
    private double rankMultiplier;

    @Override
    public double getTicketsPrice(Event event, Show show, User user, int... seats) {
        final Collection<Ticket> result = new ArrayList<>();
        List<Integer> requestedSeats = Arrays.asList(ArrayUtils.toObject(seats));
        requestedSeats.stream().forEach(sitNumber -> {
            Ticket ticket = new Ticket(event, show, sitNumber);
            ticket.setPrice(getRawPrice(event, show, sitNumber));
            result.add(ticket);
        });
        checkForDiscount(user, result);
        return result.stream().mapToDouble(ticket -> ticket.getPrice()).sum();
    }

    @Override
    public Collection<Ticket> bookTickets(Event event, Show show, User user, int... seats) {
        if (isAvailable(event, show, seats)){
            final Collection<Ticket> result = new ArrayList<>();
            List<Integer> requestedSeats = Arrays.asList(ArrayUtils.toObject(seats));
            requestedSeats.stream().forEach(sitNumber -> {
                Ticket ticket = new Ticket(event, show, sitNumber);
                ticket = bookingDAO.create(ticket);
                ticket.setPrice(getRawPrice(event, show, sitNumber));
                result.add(ticket);
            });
            checkForDiscount(user, result);
            user.getBoughtTickets().addAll(result);
            return result;
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isAvailable(Event event, Show show, int... seats) {
        if (!event.getSchedule().contains(show)){
            throw new IllegalArgumentException("Show is not related to Event!!");
        }
        List<Integer> requestedSeats = Arrays.asList(ArrayUtils.toObject(seats));
        if (requestedSeats.stream().anyMatch(sit -> show.getAuditorium().getSits() < sit || sit <= 0)
                || show.getEnd().isBefore(LocalDateTime.now())){
            // Show passed already or there is no such sit
            return false;
        }
        Collection<Ticket> bookedSits = bookingDAO.getTicketsForEvent(event, show.getStart().minusMinutes(1), show.getEnd().plusMinutes(1));
        return !bookedSits.stream().anyMatch(ticket -> requestedSeats.contains(ticket.getSitNumber()));
    }

    @Override
    public Ticket getTicketById(long ticketId) {
        return bookingDAO.get(ticketId);
    }

    private void checkForDiscount(User user, Collection<Ticket> result) {
        Optional<Discount> discount = discountService.getBestDiscount(user, result);
        if (discount.isPresent() && discount.get().getDiscountAmount() != 0)
            discount.get().getDiscountStrategy().applyDiscount(discount.get());
    }

    private double getRawPrice(Event event, Show show, int sitNumber){
        double resultPrice = event.getPrice();
        if (show.getAuditorium().getVipSits().contains(sitNumber)){
            resultPrice *= vipMultiplier;
        }
        if (event.getEventRating() == EventRating.HIGH){
            resultPrice *= rankMultiplier;
        }
        return resultPrice;
    }

    public BookingDAO getBookingDAO() {
        return bookingDAO;
    }

    public void setBookingDAO(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    public double getVipMultiplier() {
        return vipMultiplier;
    }

    public void setVipMultiplier(double vipMultiplier) {
        this.vipMultiplier = vipMultiplier;
    }

    public double getRankMultiplier() {
        return rankMultiplier;
    }

    public void setRankMultiplier(double rankMultiplier) {
        this.rankMultiplier = rankMultiplier;
    }

    public DiscountService getDiscountService() {
        return discountService;
    }

    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }
}
