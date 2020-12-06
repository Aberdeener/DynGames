package org.dyngames.dyngames.common;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.dyngames.dyngames.DynGames;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        DynGames.getInstance().getUser(event.getPlayer());
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (Config.CHAT_FORMAT_ENABLED) {
            event.setCancelled(true);
            event.setFormat(Config.CHAT_FORMAT);
        }
    }

}
