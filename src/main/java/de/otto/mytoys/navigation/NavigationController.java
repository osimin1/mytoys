package de.otto.mytoys.navigation;

import de.otto.mytoys.navigation.service.NavigationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@AllArgsConstructor
public class NavigationController {

    private NavigationService navigationService;

    @GetMapping("/api/navigation")
    public ResponseEntity<NavigationResult> getNavigationEntries() {


        List<NavigationEntry> navigationEntries = navigationService.getAllEntries();
        return ResponseEntity.ok().body(new NavigationResult(navigationEntries));
    }

    @GetMapping("/links")
    public ResponseEntity<List<Link>> getLinksEntries(@RequestParam(name = "parent", required = false) String parent,
                                                      Pageable pageable) {
        List<Link> links;
        links = getLinks(parent, pageable);
        return ResponseEntity.ok().body(links);
    }

    private List<Link> getLinks(String parent, Pageable pageable) {
        List<Link> links;
        links = handleFilter(parent);
        links = handlePageable(pageable, links);
        return links;
    }

    private List<Link> handlePageable(Pageable pageable, List<Link> links) {
        if (pageable.isPaged()) {
            if (pageable.getSort().isSorted()) {
                links = navigationService.sortLinks(links, pageable.getSort());
            }
        }
        return links;
    }

    private List<Link> handleFilter(String parent) {
        List<Link> links;
        if (parent != null) {
            links = navigationService.getLinksWithParent(parent);
        } else {
            links = navigationService.getLinks();
        }
        return links;
    }
}
