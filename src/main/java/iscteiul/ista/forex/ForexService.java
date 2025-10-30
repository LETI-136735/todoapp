package iscteiul.ista.forex;

import com.gyftedstore.currency.*;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ForexService {
    private final ForexRepository repository;
    private final ExchangeRateProvider provider = new ExchangeRateProvider();

    public ForexService(ForexRepository repository) {
        this.repository = repository;
    }

    public double convert(String from, String to, double amount) {
        double rate = provider.getRate2(from, to);
        double result = amount * rate;

        Forex forex = new Forex();
        forex.setFromCurrency(from);
        forex.setToCurrency(to);
        forex.setAmount(amount);
        forex.setResult(result);
        forex.setTimestamp(LocalDateTime.now());
        repository.save(forex);

        return result;
    }
}
