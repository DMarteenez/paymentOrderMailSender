package ru.dmarteenez.mailsender.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dmarteenez.mailsender.exceptions.PaymentException;
import ru.dmarteenez.mailsender.service.MailService;
import ru.dmarteenez.mailsender.service.model.PaymentOrderModel;

import javax.mail.internet.AddressException;

@RequiredArgsConstructor
@RestController
public class MailController {

    private final MailService mailService;

    @PostMapping("/api/mail")
    public ResponseEntity<String> sendMail(@RequestBody PaymentOrderModel paymentInfo) throws PaymentException, AddressException {

        mailService.sendReceivedPaymentReport(paymentInfo);
        return ResponseEntity.ok("Report was sent to " + paymentInfo.getBeneficiaryEmail());
    }

    @ExceptionHandler ({PaymentException.class, AddressException.class})
    public ResponseEntity<?> handleException(Exception exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
