package de.otto.mytoys.navigation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
class NavigationResult {
    List<NavigationEntry> navigationEntries;
}
