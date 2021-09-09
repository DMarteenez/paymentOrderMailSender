package ru.dmarteenez.mailsender.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrderModel {

    @NotNull
    private String remitterName;

    @NotNull
    private String beneficiaryName;
    @NotNull
    private String beneficiaryEmail;

    @NotNull
    private double paymentAmount;
    @NotNull
    private String paymentCurrency;
    @NotNull
    private Date valueDate;
    private String paymentDetails;
}
