package com.baziuk.spring.payment.web;

import com.baziuk.spring.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Maks on 11/20/16.
 */
@Controller
@RequestMapping("/pay")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @ResponseBody
    @Transactional
    @RequestMapping("/transfer")
    public String transferFounds(@RequestParam long userId,
                                 @RequestParam double amount){
        boolean result = paymentService.addFunds(userId, amount);
        return Boolean.toString(result);
    }

}
