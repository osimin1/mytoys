package de.otto.mytoys.security;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ApiKeyService {

    private static final String API_KEY = "hz7JPdKK069Ui1TRxxd1k8BQcocSVDkj219DVzzD";

    public boolean isProvidedKeyInCorrect(String apiKey) {
        boolean result = true;
        if (apiKey.equals(API_KEY)) {
            result = false;
        }
        return result;
    }
}