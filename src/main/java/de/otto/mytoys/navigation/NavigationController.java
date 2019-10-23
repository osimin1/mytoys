package de.otto.mytoys.navigation;

import de.otto.mytoys.security.ApiKeyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@AllArgsConstructor
public class NavigationController {

    private ApiKeyService apiKeyService;
    private NavigationService navigationService;

    @GetMapping("/api/navigation")
    public ResponseEntity<NavigationEntries> getNavigationEntries(@RequestHeader(value = "x-api-key") String apiKey) {
        if (apiKeyService.isProvidedKeyInCorrect(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<NavigationEntry> navigationEntries = navigationService.getAllEntries();
        return ResponseEntity.ok().body(new NavigationEntries(navigationEntries));
    }
}
