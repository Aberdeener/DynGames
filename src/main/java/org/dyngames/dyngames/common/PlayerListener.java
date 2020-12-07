package org.dyngames.dyngames.common;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.dyngames.dyngames.DynGames;
import org.dyngames.dyngames.api.User;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        DynGames.getInstance().getUser(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        DynGames.getInstance().removeUser(event.getPlayer());
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (Config.CHAT_FORMAT_ENABLED) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            User user = DynGames.getInstance().getUser(player);
            String message = Config.CHAT_FORMAT;
            message = ChatColor.translateAlternateColorCodes('&', message
                    .replace("%level%", String.valueOf(user.getLevel()))
                    .replace("%username%", player.getDisplayName())
                    .replace("%message%", event.getMessage()));
            if (Config.GLOBAL_CHAT) {
                Bukkit.broadcastMessage(message);
            } else {
                Bukkit.getConsoleSender().sendMessage(message);
                for (Player p : player.getWorld().getPlayers()) {
                    p.sendMessage(message);
                }
            }
        }
    }
}
