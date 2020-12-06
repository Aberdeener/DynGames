package org.dyngames.dyngames.common;

import org.dyngames.dyngames.DynGames;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class Config {

    public static final List<String> GAMES = (List<String>) get("games", Collections.emptyList());

    public static final boolean CHAT_FORMAT_ENABLED = (boolean) get("chat.format-enabled", false);

    public static final String CHAT_FORMAT = (String) get("chat.format", "%level%&7 -&r%username%&7: %message%");

    public static final boolean GLOBAL_CHAT = (boolean) get("chat.global-chat", true);

    private static Object get(String path, Object fallback) {
        Object value = DynGames.getInstance().getConfig().get(path);

        return value == null ? fallback : value;
    }
}