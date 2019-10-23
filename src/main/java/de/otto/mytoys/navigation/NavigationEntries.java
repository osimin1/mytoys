package de.otto.mytoys.navigation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NavigationEntries {
    List<NavigationEntry> navigationEntries;
}
