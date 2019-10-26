package de.otto.mytoys.navigation;

import jdk.internal.joptsimple.internal.Strings;
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
                .url("http://www.mytoys.de/24-47-months/")
                .build();

        final NavigationEntry fourToFiveLink = NavigationEntry.builder()
                .type("link")
                .label("4-5 Jahre")
                .url("http://www.mytoys.de/48-71-months/")
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
                .url("http://www.mytoys.de/0-6-months/")
                .build();

        final NavigationEntry sevenToTwelveMonths = NavigationEntry.builder()
                .type("link")
                .label("7-12 Monate")
                .url("http://www.mytoys.de/7-12-months/")
                .build();

        final NavigationEntry thirteenToTwenty4Months = NavigationEntry.builder()
                .type("link")
                .label("13-24 Monate")
                .url("http://www.mytoys.de/13-24-months/")
                .build();

        return Arrays.asList(zeroToSixMonths, sevenToTwelveMonths, thirteenToTwenty4Months);
    }

    public List<Link> getLinks() {
        List<Link> linkList = new ArrayList<>();
        final List<NavigationEntry> allEntries = getAllEntries();
        allEntries.forEach(navigationEntry ->
                createLinks(navigationEntry, linkList)
        );
        return linkList;
    }

    private void createLinks(NavigationEntry navigationEntry, List<Link> linkList) {
        final String parentLabel = navigationEntry.getLabel().orElse(Strings.EMPTY);
        createLinkOrCheckChildren(navigationEntry, linkList, parentLabel);
    }

    private void createLinkOrCheckChildren(NavigationEntry navigationEntry, List<Link> linkList, String parentLabel) {
        navigationEntry.getUrl().ifPresentOrElse(url ->
                        linkList.add(Link.builder().label(parentLabel).url(url).build())
                , () ->
                        navigationEntry.getChildren().ifPresent(children ->
                                children.forEach(child -> createLinksFromChildren(parentLabel, linkList, child))
                        ));
    }

    private void createLinksFromChildren(String parentLabel, List<Link> linkList, NavigationEntry navigationEntry) {
        String resultLabel;
        if (parentLabel.isEmpty()) {
            resultLabel = navigationEntry.getLabel().orElse(Strings.EMPTY);
        } else {
            resultLabel = String.join(" - ", parentLabel, navigationEntry.getLabel().orElse(Strings.EMPTY));
        }
        createLinkOrCheckChildren(navigationEntry, linkList, resultLabel);
    }

    public List<Link> getLinksWithParent(String parent) {
        List<Link> linkList = new ArrayList<>();
        final List<NavigationEntry> allEntries = getAllEntries();
        allEntries.forEach(navigationEntry -> checkChildrenWithParent(parent, linkList, navigationEntry));
        return linkList;
    }

    private void checkChildrenWithParent(String parent, List<Link> linkList, NavigationEntry navigationEntry) {
        navigationEntry.getLabel().ifPresent(label -> {
            if (label.equals(parent)) {
                createLinkOrCheckChildren(navigationEntry, linkList, Strings.EMPTY);
            } else {
                navigationEntry.getChildren().ifPresent(children ->
                        children.forEach(child -> checkChildrenWithParent(parent, linkList, child)));
            }
        });
    }
}
