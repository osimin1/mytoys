package de.otto.mytoys.navigation;

import jdk.internal.joptsimple.internal.Strings;
import lombok.Data;

@Data
public class Link {
    String label = Strings.EMPTY;
    String url = Strings.EMPTY;
}
