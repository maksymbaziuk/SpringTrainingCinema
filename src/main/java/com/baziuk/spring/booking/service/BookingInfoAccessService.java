package com.baziuk.spring.booking.service;

import com.baziuk.spring.user.web.bean.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Created by Maks on 11/19/16.
 */
@Service
public class BookingInfoAccessService {

    public boolean checkIfTicketAccessable(Authentication authentication, long id){
        Object principal = authentication.getPrincipal();
        if (principal == null || "anonymousUser".equals(principal))
            return false;
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        boolean result = false;
        if (currentUser != null){
            result = currentUser.getUser().getBoughtTickets().stream()
                    .filter(ticket -> ticket.getId() == id)
                    .findFirst()
                    .isPresent();
        }
        return result;
    }

}
