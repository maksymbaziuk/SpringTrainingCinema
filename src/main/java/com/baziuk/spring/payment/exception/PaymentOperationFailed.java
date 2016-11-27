package com.baziuk.spring.payment.exception;

/**
 * Created by Maks on 11/20/16.
 */
public class PaymentOperationFailed extends RuntimeException {

    public PaymentOperationFailed() {
    }

    public PaymentOperationFailed(String message) {
        super(message);
    }

    public PaymentOperationFailed(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentOperationFailed(Throwable cause) {
        super(cause);
    }

    public PaymentOperationFailed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
