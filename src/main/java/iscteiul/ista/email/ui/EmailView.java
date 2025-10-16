package iscteiul.ista.email.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import iscteiul.ista.email.Email;
import iscteiul.ista.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;


@PageTitle("Email")
@Menu(order = 1, icon = "vaadin:clipboard-check", title = "Email")
@Route("email")

public class EmailView extends Main {
    @Autowired
    private EmailService emailService;

    public EmailView() {
        TextField from = new TextField("De");
        TextField to = new TextField("Para");
        TextField subject = new TextField("Asunto");
        TextArea message = new TextArea("Mensaje");

        Button send = new Button("Enviar", event -> {
            Email email = new Email();
            email.setFromAddress(from.getValue());
            email.setToAddress(to.getValue());
            email.setSubject(subject.getValue());
            email.setMessage(message.getValue());
            emailService.saveEmail(email);
            try {
                emailService.sendEmail(email);
                Notification.show("Correo enviado");
            } catch (Exception e) {
                Notification.show("Error al enviar: " + e.getMessage());
            }
        });

        add(from, to, subject, message, send);
    }
}
