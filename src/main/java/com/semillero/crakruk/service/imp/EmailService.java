package com.semillero.crakruk.service.imp;

import com.semillero.crakruk.service.IEmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class EmailService implements IEmailService {

    @Value("${EMAIL}")
    private String email;

    @Value("${API_KEY}")
    private String api_Key;
    @Value("${TEMPLATE_ID}")
    private String template_id;

    public void sendWelcomeEmailTo(String emailTo,String name) {

        String emailSubject = "Bienvenido a Chakruk";
        Content body = new Content("text/html",welcomeEmailTemplate(name));
        try {
            sendMail(emailTo, emailSubject, body);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void sendMail(String emailTo, String emailSubject, Content body) throws IOException {

        String emailSender = email;
        String apiKey = api_Key;

        Email from = new Email(emailSender);
        Email to = new Email(emailTo);
        String subject = emailSubject;
        Content content = body;


        Mail mail = new Mail(from, subject, to, content);
        SendGrid sgKey = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sgKey.api(request);
            if (response != null) {
                log.info("Mail enviado a {}", emailTo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String welcomeEmailTemplate(String name) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Template para Gmail</title>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0; background-image: url('https://ichef.bbci.co.uk/news/800/cpsprodpb/F800/production/_118088436_for_press_release.jpg'); background-repeat: no-repeat; background-position: center top; background-size: cover\">\n" +
                "\n" +
                "    <table role=\"presentation\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; max-width: 600px;\" >\n" +
                "        <tr>\n" +
                "            <td align=\"center\"  style=\"padding: 40px 0;\">\n" +
                "                <!--[if mso]>\n" +
                "                <v:rect xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\" style=\"width: 100%; height: 100%;\">\n" +
                "                <v:fill type=\"tile\" src=\"https://ichef.bbci.co.uk/news/800/cpsprodpb/F800/production/_118088436_for_press_release.jpg\" color=\"#f7f7f7\" />\n" +
                "                <v:textbox style=\"mso-fit-shape-to-text: true;\" inset=\"0,0,0,0\">\n" +
                "                <![endif]-->\n" +
                "                <div>\n" +
                "                    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin: 0 auto; width: 100%; max-width: 600px; background-color: transparent; padding: 20px;\">\n" +
                "                        <tr>\n" +
                "                            <td align=\"center\" style=\"padding: 0 0 20px;\">\n" +
                "                                <h1 style=\"color: #fff; font-size: 24px; margin: 0;\">¡Bienvenido a chakruk!</h1>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td align=\"center\" style=\"padding: 0;\">\n" +
                "                                <p style=\"color: #fff; font-size: 16px; line-height: 24px; margin: 0;\">Gracias por suscribirte a nuestra comunidad. Estamos emocionados de compartir contigo las últimas noticias y actualizaciones.</p>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td align=\"center\" style=\"padding: 20px 0;\">\n" +
                "                                <a href=\"https://chakruk.quinto.site/\" target=\"_blank\" style=\"display: inline-block; background-color: #007bff; color: #ffffff; text-decoration: none; font-size: 18px; padding: 10px 20px; border-radius: 5px;\">Visitar nuestro sitio web</a>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </div>\n" +
                "                <!--[if mso]>\n" +
                "                </v:textbox>\n" +
                "                </v:rect>\n" +
                "                <![endif]-->\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }


}
