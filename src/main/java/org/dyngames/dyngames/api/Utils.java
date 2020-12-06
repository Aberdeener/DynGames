package org.dyngames.dyngames.api;

import java.util.List;

public class Utils {

    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(item -> sb.append(item).append(", "));
        return sb.substring(sb.toString().length() - 1).trim();
    }
}
