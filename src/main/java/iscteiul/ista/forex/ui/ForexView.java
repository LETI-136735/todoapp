package iscteiul.ista.forex.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import iscteiul.ista.forex.ForexService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("forex")
public class ForexView extends Main {

    private static final String[] CURRENCIES = {"USD", "EUR", "GBP", "JPY", "CHF", "CAD"};

    @Autowired
    public ForexView(ForexService service) {
        ComboBox<String> from = new ComboBox<>("From", CURRENCIES);
        ComboBox<String> to = new ComboBox<>("To", CURRENCIES);
        NumberField amount = new NumberField("Amount");
        TextField result = new TextField("Result");
        result.setReadOnly(true);

        Button convert = new Button("Convert", e -> {
            if (from.isEmpty() || to.isEmpty() || amount.isEmpty()) {
                Notification.show("Please fill all fields");
                return;
            }
            try {
                double res = service.convert(from.getValue(), to.getValue(), amount.getValue());
                result.setValue(String.format("%.2f", res));
            } catch (Exception ex) {
                Notification.show("Conversion error: " + ex.getMessage());
            }
        });

        add(from, to, amount, convert, result);
    }
}
