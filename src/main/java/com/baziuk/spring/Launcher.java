package com.baziuk.spring;

import com.baziuk.spring.auditorium.bean.Auditorium;
import com.baziuk.spring.auditorium.config.AuditoriumServiceLayerConfig;
import com.baziuk.spring.auditorium.config.db.AuditoriumDBConfig;
import com.baziuk.spring.auditorium.dao.AuditoriumDAO;
import com.baziuk.spring.booking.config.BookingServiceLayerConfig;
import com.baziuk.spring.booking.config.db.BookingH2DBConfig;
import com.baziuk.spring.booking.dao.BookingDAO;
import com.baziuk.spring.data.H2DBConfig;
import com.baziuk.spring.discount.config.DiscountServiceLayerConfig;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.EventRating;
import com.baziuk.spring.events.bean.Show;
import com.baziuk.spring.events.config.EventServiceLayerConfig;
import com.baziuk.spring.events.config.db.EventH2DBConfig;
import com.baziuk.spring.events.dao.EventDAO;
import com.baziuk.spring.user.config.UserServiceLayerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.TreeSet;

/**
 * Created by Maks on 10/10/16.
 */
public class Launcher {

    public static void main(String[] args) {
        /*AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                H2DBConfig.class,
                AuditoriumDBConfig.class,
                EventH2DBConfig.class,
                BookingH2DBConfig.class);*/
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                H2DBConfig.class,
                AuditoriumDBConfig.class,
                EventH2DBConfig.class,
                BookingH2DBConfig.class,
                AuditoriumServiceLayerConfig.class,
                EventServiceLayerConfig.class,
                UserServiceLayerConfig.class,
                BookingServiceLayerConfig.class,
                DiscountServiceLayerConfig.class);
        DataSource ds = context.getBean(DataSource.class);

        EventDAO eventDAO = context.getBean(EventDAO.class);
        AuditoriumDAO auditoriumDAO = context.getBean(AuditoriumDAO.class);
        Auditorium auditorium = auditoriumDAO.get(1);

        System.out.println(eventDAO.getAll());

        Event event = new Event();
        event.setName("testEvent");
        event.setPrice(123123);
        event.setEventRating(EventRating.HIGH);
        Show show = new Show();
        show.setAuditorium(auditorium);
        show.setStart(LocalDateTime.now().plusDays(5));
        show.setEnd(LocalDateTime.now().plusDays(10));
        TreeSet<Show> showTreeSet = new TreeSet<>();
        showTreeSet.add(show);
        event.setSchedule(showTreeSet);

        eventDAO.create(event);

        Event createdEvent = eventDAO.getByName("testEvent");
        System.out.println(createdEvent);
        createdEvent.setPrice(1111);
        Show show1 = new Show();
        show1.setAuditorium(auditorium);
        show1.setStart(LocalDateTime.now().plusDays(5));
        show1.setEnd(LocalDateTime.now().plusDays(10));
        createdEvent.getSchedule().add(show1);
        eventDAO.update(createdEvent);
        Event updatedEvent = eventDAO.get(createdEvent.getId());
        System.out.println(updatedEvent);

        BookingDAO bookingDAO = context.getBean(BookingDAO.class);
        System.out.println(bookingDAO.getAll());
    }
}
