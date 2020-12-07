package org.dyngames.dyngames.common;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.dyngames.dyngames.DynGames;

public class Config {

    /**
     * All games
     */
    public static final ConfigurationSection GAMES = DynGames.getInstance().getConfig().getConfigurationSection("games");

    /**
     * A boolean whether DynGames should touch chat formatting at all
     */
    public static final boolean CHAT_FORMAT_ENABLED = (boolean) get("chat.format-enabled", false);

    /**
     * A string with some placeholders on how chat should be formatted. No effect if CHAT_FORMAT_ENABLED is false
     */
    public static final String CHAT_FORMAT = (String) get("chat.format", "%level%&7 -&r%username%&7: %message%");

    /**
     * A boolean whether chat should be global (server wide) or just in the player's world
     */
    public static final boolean GLOBAL_CHAT = (boolean) get("chat.global-chat", true);

    /**
     * A {@link org.bukkit.Location} to respawn players when they join the queue, and when a game ends
     */
    public static final Location QUEUE_LOCATION = new Location(
        Bukkit.getWorld((String) get("queue.location.world")),
        (double) get("queue.location.x"),
        (double) get("queue.location.y"),
        (double) get("queue.location.z"),
        (float) ((double) get("queue.location.pitch")),
        (float) ((double) get("queue.location.yaw"))
    );

    private static Object get(String path) {
        return get(path, null);
    }

    private static Object get(String path, Object fallback) {
        Object value = DynGames.getInstance().getConfig().get(path);

        return value == null ? fallback : value;
    }
}