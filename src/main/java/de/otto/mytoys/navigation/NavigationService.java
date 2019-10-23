package de.otto.mytoys.navigation;

import org.springframework.hateoas.Links;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
public class NavigationService {


    public List<NavigationEntry> getAllEntries() {
        final NavigationEntry navigationEntry = createSections();


        return Collections.singletonList(navigationEntry);
    }

    private NavigationEntry createSections() {
        return NavigationEntry.builder()
                .type("section")
                .label("Sortiment")
                .children(createSectionNodes())
                .build();
    }

    private List<NavigationEntry> createSectionNodes() {
        final NavigationEntry alterNode = NavigationEntry.builder()
                .type("node")
                .label("Alter")
                .children(createAlterNode())
                .build();

        return Arrays.asList(alterNode);
    }

    private List<NavigationEntry> createKinderGartenLinks() {
        final NavigationEntry twoToThreeLink = NavigationEntry.builder()
                .type("link")
                .label("2-3 Jahre")
                .url(java.util.Optional.of("http://www.mytoys.de/24-47-months/"))
                .build();

        final NavigationEntry fourToFiveLink = NavigationEntry.builder()
                .type("link")
                .label("4-5 Jahre")
                .url(java.util.Optional.of("http://www.mytoys.de/48-71-months/"))
                .build();

        return Arrays.asList(twoToThreeLink, fourToFiveLink);
    }

    private List<NavigationEntry> createAlterNode() {
        final NavigationEntry babyAndKleinKindNode = NavigationEntry.builder()
                .type("node")
                .label("Baby & Kleinkind")
                .children(createBabyAndKleinKindLinks())
                .build();

        final NavigationEntry kinderGartenNode = NavigationEntry.builder()
                .type("node")
                .label("Kindergarten")
                .children(createKinderGartenLinks())
                .build();

        return Arrays.asList(babyAndKleinKindNode, kinderGartenNode);
    }

    private List<NavigationEntry> createBabyAndKleinKindLinks() {
        final NavigationEntry zeroToSixMonths = NavigationEntry.builder()
                .type("link")
                .label("0-6 Monate")
                .url(java.util.Optional.of("http://www.mytoys.de/0-6-months/"))
                .build();

        final NavigationEntry sevenToTwelveMonths = NavigationEntry.builder()
                .type("link")
                .label("7-12 Monate")
                .url(java.util.Optional.of("http://www.mytoys.de/7-12-months/"))
                .build();

        final NavigationEntry thirteenToTwenty4Months = NavigationEntry.builder()
                .type("link")
                .label("13-24 Monate")
                .url(java.util.Optional.of("http://www.mytoys.de/13-24-months/"))
                .build();

        return Arrays.asList(zeroToSixMonths, sevenToTwelveMonths, thirteenToTwenty4Months);
    }

    public List<Link> getLinks() {
        List<Link> links = new ArrayList<>();
        final List<NavigationEntry> allEntries = getAllEntries();
        allEntries.forEach(navigationEntry -> {
                    Link link = new Link();
                    link.setLabel(navigationEntry.getLabel());
                    navigationEntry.getUrl().ifPresentOrElse(url -> {
                        link.setUrl(url);
                        links.add(link);
                        }, () ->
                            navigationEntry.getChildren().forEach(children -> iterateEntires(link, children)));
                }

        );
        return links;
    }

    private void iterateEntires(Link link, NavigationEntry navigationEntry) {
        link.setLabel(String.join(" - ", link.getLabel(), navigationEntry.getLabel()));
        navigationEntry.getUrl().ifPresentOrElse(url -> link.setUrl(url), () ->
                navigationEntry.getChildren().forEach(children -> iterateEntires(link, children)));
    }
}
