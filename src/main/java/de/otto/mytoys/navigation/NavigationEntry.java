package de.otto.mytoys.navigation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class NavigationEntry {
    private String type;
    private String label;
    private String url;

    private List<NavigationEntry> children;

    public Optional<String> getLabel() {
        return Optional.ofNullable(label);
    }

    public Optional<String> getUrl() {
        return Optional.ofNullable(url);
    }

    public Optional<List<NavigationEntry>> getChildren() {
        return Optional.ofNullable(children);
    }

    public Optional<String> getType() {
        return Optional.ofNullable(type);
    }
}
