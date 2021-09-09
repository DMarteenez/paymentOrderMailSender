package ru.dmarteenez.mailsender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.dmarteenez.mailsender.exceptions.PaymentException;
import ru.dmarteenez.mailsender.service.model.PaymentOrderModel;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private String generateReport(PaymentOrderModel paymentInfo) {

        String text = "";
        text += "---Received payment report---\n\n";
        text += "Remitter name: " + paymentInfo.getRemitterName() + "\n";
        text += "Beneficiary name: " + paymentInfo.getBeneficiaryName() + "\n\n";
        text += "Payment amount: " + paymentInfo.getPaymentAmount() + " (" + paymentInfo.getPaymentCurrency() + ")\n\n";
        text += "Value date: " + paymentInfo.getValueDate() + "\n";
        text += "Payment details: " + paymentInfo.getPaymentDetails();

        return text;
    }

    private void validatePaymentInfo (PaymentOrderModel paymentInfo) throws PaymentException, AddressException {

        if (paymentInfo.getPaymentAmount() <= 0) {
            throw new PaymentException("Payment amount below or equals 0.");
        }
        if (paymentInfo.getValueDate().after(new Date())) {
            throw new PaymentException("Value date is after now.");
        }

        InternetAddress emailAddress = new InternetAddress(paymentInfo.getBeneficiaryEmail());
        emailAddress.validate();

    }

    public void sendReceivedPaymentReport (PaymentOrderModel paymentInfo) throws PaymentException, AddressException {

        validatePaymentInfo(paymentInfo);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(""); //Enter same e-mail address as in application.properties (may also word without it but not 100%)
        message.setTo(paymentInfo.getBeneficiaryEmail());
        message.setSubject("Payment received");
        message.setText(generateReport(paymentInfo));

        javaMailSender.send(message);
    }
}
