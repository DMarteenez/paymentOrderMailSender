package ru.dmarteenez.mailsender.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.dmarteenez.mailsender.service.model.PaymentOrderModel;

import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class MailControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("Test send good payment report")
    @Test
    public void sendGood() throws Exception
    {
        String json = (new ObjectMapper()).writeValueAsString(
                new PaymentOrderModel(
                        "buyer",
                        "seller",
                        "some.test.email@gmail.com", //!!! Enter your test gmail e-mail here to test service!!!
                        100,
                        "euro",
                        new SimpleDateFormat("yyyy-MM-dd").parse("2020-10-10"),
                        "some details"));

        mvc.perform( MockMvcRequestBuilders
                        .post("/api/mail")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("Test send wrong amount payment report")
    @Test
    public void sendWrongAmount() throws Exception
    {
        String json = (new ObjectMapper()).writeValueAsString(
                new PaymentOrderModel(
                        "buyer",
                        "seller",
                        "some.test.email@gmail.com", //!!! Enter your test gmail e-mail here to test service!!!
                        -100,
                        "euro",
                        new SimpleDateFormat("yyyy-MM-dd").parse("2020-10-10"),
                        "some details"));

        mvc.perform( MockMvcRequestBuilders
                        .post("/api/mail")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Test send wrong date payment report")
    @Test
    public void sendWrongDate() throws Exception
    {
        String json = (new ObjectMapper()).writeValueAsString(
                new PaymentOrderModel(
                        "buyer",
                        "seller",
                        "some.test.email@gmail.com", //!!! Enter your test gmail e-mail here to test service!!!
                        100,
                        "euro",
                        new SimpleDateFormat("yyyy-MM-dd").parse("2025-10-10"),
                        "some details"));

        mvc.perform( MockMvcRequestBuilders
                        .post("/api/mail")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Test send wrong e-mail address payment report")
    @Test
    public void sendWrongEmail() throws Exception
    {
        String json = (new ObjectMapper()).writeValueAsString(
                new PaymentOrderModel(
                        "buyer",
                        "seller",
                        "seller_seller",
                        100,
                        "euro",
                        new SimpleDateFormat("yyyy-MM-dd").parse("2020-10-10"),
                        "some details"));

        mvc.perform( MockMvcRequestBuilders
                        .post("/api/mail")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
