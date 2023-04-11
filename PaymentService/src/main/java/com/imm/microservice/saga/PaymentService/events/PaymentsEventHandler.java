package com.imm.microservice.saga.PaymentService.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.imm.microservice.saga.CommonService.events.PaymentCancelledEvent;
import com.imm.microservice.saga.CommonService.events.PaymentProcessedEvent;
import com.imm.microservice.saga.PaymentService.data.Payment;
import com.imm.microservice.saga.PaymentService.data.PaymentRepository;

import java.util.Date;

@Component
public class PaymentsEventHandler {

    private PaymentRepository paymentRepository;

    public PaymentsEventHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        Payment payment
                = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .paymentStatus("COMPLETED")
                .timeStamp(new Date())
                .build();

            paymentRepository.save(payment);
    }

    @EventHandler
    public void on(PaymentCancelledEvent event) {
        Payment payment
                = paymentRepository.findById(event.getPaymentId()).get();

        payment.setPaymentStatus(event.getPaymentStatus());

        paymentRepository.save(payment);
    }
}
