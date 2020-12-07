package org.dyngames.dyngames.api;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dyngames.dyngames.DynGames;

public class User {

    @Getter
    private final Player player;
    @Getter
    private final String username;
    @Getter
    private int level;

    public User(Player player) {
        this.player = player;
        this.username = player.getName();
        Bukkit.getScheduler().runTaskAsynchronously(DynGames.getInstance(), () -> {
            // TODO: Get db stats here
            this.level = 0;
        });
    }

    public void update() {
        Bukkit.getScheduler().runTaskAsynchronously(DynGames.getInstance(), () -> {
           // Save stats
        });
    }

    public void sendMessage(String message, Object... replacements) {
        this.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(message.replaceAll("§", "&"), replacements)));
    }
}
