package de.otto.mytoys.navigation;

import java.util.Comparator;

public enum LinkComperator implements Comparator<Link> {
    LABEL_SORT {
        public int compare(Link l1, Link l2) {
            return (l1.getLabel()).compareTo(l2.getLabel());
        }
    },
    URL_SORT {
        public int compare(Link l1, Link l2) {
            return l1.getUrl().compareTo(l2.getUrl());
        }
    };

    public static Comparator<Link> decending(final Comparator<Link> other) {
        return (l1, l2) -> -1 * other.compare(l1, l2);
    }

    public static Comparator<Link> getComparator(final LinkComperator... multipleOptions) {
        return (l1, l2) -> {
            for (LinkComperator option : multipleOptions) {
                int result = option.compare(l1, l2);
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        };
    }
}

