package org.dyngames.dyngames.api;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.dyngames.dyngames.DynGames;

public class User {

    @Getter
    private Player player;
    @Getter
    private String username;
    @Getter
    private int level;

    public User(Player player) {
        String username = player.getName();
        Bukkit.getScheduler().runTaskAsynchronously(DynGames.getInstance(), () -> {
            this.player = player;
            this.username = username;
            this.level = 0; // TODO: Get and store info in db of some sort
        });
    }

    public void sendMessage(String message, Object... replacements) {
        this.getPlayer().sendMessage(String.format(message, replacements));
    }
}
