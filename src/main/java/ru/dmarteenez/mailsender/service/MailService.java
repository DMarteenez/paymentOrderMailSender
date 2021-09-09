package ru.dmarteenez.mailsender.service;

import ru.dmarteenez.mailsender.exceptions.PaymentException;
import ru.dmarteenez.mailsender.service.model.PaymentOrderModel;

import javax.mail.internet.AddressException;

public interface MailService {

    void sendReceivedPaymentReport(PaymentOrderModel paymentInfo) throws PaymentException, AddressException;
}
