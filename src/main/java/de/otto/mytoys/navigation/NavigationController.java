package de.otto.mytoys.navigation;

import de.otto.mytoys.security.ApiKeyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Data
@AllArgsConstructor
public class NavigationController {

    private ApiKeyService apiKeyService;
    private NavigationService navigationService;

    @GetMapping("/api/navigation")
    public ResponseEntity<NavigationResult> getNavigationEntries(@RequestHeader(value = "x-api-key") String apiKey) {
        if (apiKeyService.isProvidedKeyInCorrect(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<NavigationEntry> navigationEntries = navigationService.getAllEntries();
        return ResponseEntity.ok().body(new NavigationResult(navigationEntries));
    }

    @GetMapping("/links")
    public ResponseEntity<List<Link>> getLinksEntries(@RequestHeader(value = "x-api-key") String apiKey,
                                                      @RequestParam("parent") Optional<String> parent) {
        if (apiKeyService.isProvidedKeyInCorrect(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Link> links;
        if (parent.isPresent()) {
            links = navigationService.getLinksWithParent(parent.get());
        } else {
            links = navigationService.getLinks();
        }
        return ResponseEntity.ok().body(links);
    }
}
