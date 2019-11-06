package de.otto.mytoys.navigation.service;

import de.otto.mytoys.navigation.Link;
import de.otto.mytoys.navigation.NavigationEntry;
import de.otto.mytoys.navigation.repository.NaviagationRepo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static de.otto.mytoys.navigation.LinkComperator.*;


@Service
public class NavigationService {

    private static final String URL = "url";
    private static final String URL_ASC = "url:asc";
    private static final String URL_DESC = "url:desc";
    private static final String LABEL = "label";
    private static final String LABEL_ASC = "label:asc";
    private static final String LABEL_DESC = "label:desc";

    private NaviagationRepo naviagationRepo = new NaviagationRepo();

    public List<NavigationEntry> getAllEntries() {
        final NavigationEntry navigationEntry = naviagationRepo.createSections();


        return Collections.singletonList(navigationEntry);
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

    public List<Link> sortLinks(List<Link> links, Sort sort) {
        sortUrl(links, sort);
        sortLabel(links, sort);

        return links;
    }

    private void sortLabel(List<Link> links, Sort sort) {
        if (sort.getOrderFor(LABEL) != null || sort.getOrderFor(LABEL_ASC) != null) {
            links.sort((getComparator(LABEL_SORT)));
        }
        if (sort.getOrderFor(LABEL_DESC) != null) {
            links.sort(decending(getComparator(LABEL_SORT)));
        }
    }

    private void sortUrl(List<Link> links, Sort sort) {
        if (sort.getOrderFor(URL) != null || sort.getOrderFor(URL_ASC) != null) {
            links.sort((getComparator(URL_SORT)));
        }
        if (sort.getOrderFor(URL_DESC) != null) {
            links.sort(decending(getComparator(URL_SORT)));
        }
    }
}
