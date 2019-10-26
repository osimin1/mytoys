package de.otto.mytoys.navigation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class NavigationEntry {
    private String type;
    private String label;
    @Builder.Default
    private String url;
    private List<NavigationEntry> children;

    public Optional<String> getLabel() {
        return Optional.ofNullable(label);
    }

    public Optional<String> getUrl() {
        return Optional.ofNullable(url);
    }

    public List<NavigationEntry> getChildren() {
        return children;
    }

    public Optional<String> getType() {
        return Optional.ofNullable(type);
    }
}
