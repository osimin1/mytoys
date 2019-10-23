package de.otto.mytoys.navigation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Data
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NavigationEntry {
    private String type;
    private String label;
    @Builder.Default
    private Optional<String> url = Optional.empty();

    private List<NavigationEntry> children;
}
