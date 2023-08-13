package com.semillero.crakruk.service;

import com.sendgrid.helpers.mail.objects.Content;

import java.io.IOException;

public interface IEmailService {

    void sendWelcomeEmailTo(String to,String name);
    String welcomeEmailTemplate(String name);
    void sendMail(String emailTo, String emailSubject, Content body) throws IOException;

}
