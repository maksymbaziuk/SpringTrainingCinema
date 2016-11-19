package com.baziuk.spring.booking.web;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.booking.service.BookingService;
import com.baziuk.spring.booking.web.bean.BookingRequest;
import com.baziuk.spring.booking.web.bean.UserBookingRequest;
import com.baziuk.spring.common.web.binding.EventByIdDataBinder;
import com.baziuk.spring.common.web.binding.ShowByIdDataBinder;
import com.baziuk.spring.common.web.binding.UserDataBinder;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.Show;
import com.baziuk.spring.user.bean.User;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;


/**
 * Created by Maks on 11/8/16.
 */
@Controller
@RequestMapping("/purchase")
public class BookingController {

    private static final Logger log = Logger.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserDataBinder userDataBinder;
    @Autowired
    private ShowByIdDataBinder showByIdDataBinder;
    @Autowired
    private EventByIdDataBinder eventByIdDataBinder;

    private static final Gson GSON = new Gson();

    @ResponseBody
    @RequestMapping("/check")
    public String isTicketsAvailable(@ModelAttribute BookingRequest bookingRequest){
        log.info("Checking availability: " + bookingRequest.toString());
        int[] places = parsePlaces(bookingRequest.getPlaces());
        boolean available = bookingService.isAvailable(
                bookingRequest.getEvent(),
                bookingRequest.getShow(),
                places);
        log.info("Result of availability check: " + available);
        return GSON.toJson(available);
    }

    @ResponseBody
    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public String bookTickets(@ModelAttribute UserBookingRequest bookingRequest){
        log.info("Attempting to book tickets: " + bookingRequest.toString());
        User user = bookingRequest.getUser();
        int[] places = parsePlaces(bookingRequest.getPlaces());
        Collection<Ticket> tickets = bookingService.bookTickets(
                bookingRequest.getEvent(),
                bookingRequest.getShow(),
                user,
                places);
        log.info("Result of booking request: " + Arrays.toString(tickets.toArray()));
        // TODO change to PDF
        return GSON.toJson(tickets);
    }

    @ResponseBody
    @RequestMapping(value = "/ticket/price", method = RequestMethod.GET)
    public String getTicketPrice(@ModelAttribute UserBookingRequest bookingRequest){
        int[] places = parsePlaces(bookingRequest.getPlaces());
        double price = bookingService.getTicketsPrice(
                bookingRequest.getEvent(),
                bookingRequest.getShow(),
                bookingRequest.getUser(),
                places
        );
        return Double.toString(price);
    }

    @RequestMapping("/ticket/{ticketId}")
    public String getTicketById(@PathVariable long ticketId, ModelMap modelMap){
        Ticket ticket = bookingService.getTicketById(ticketId);
        modelMap.addAttribute("ticket", ticket);
        return "singleTicket";
    }

    private int[] parsePlaces(String placesRaw){
        String[] placesRawArr = placesRaw.split(",");
        int[] places = new int[placesRawArr.length];
        for (int i = 0; i < placesRawArr.length ; i++){
            places[i] = Integer.parseInt(placesRawArr[i]);
        }
        return places;
    }

    @InitBinder
    protected void initDataBinders(WebDataBinder dataBinder){
        dataBinder.registerCustomEditor(User.class, userDataBinder);
        dataBinder.registerCustomEditor(Show.class, showByIdDataBinder);
        dataBinder.registerCustomEditor(Event.class, eventByIdDataBinder);
    }
}
