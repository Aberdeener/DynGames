package org.dyngames.dyngames.games.tntrun;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.dyngames.dyngames.DynGames;
import org.dyngames.dyngames.api.User;
import org.dyngames.dyngames.common.Config;

import java.util.UUID;


public class Listeners implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (TntRun.getInstance().getTotalPlayers().contains(player.getUniqueId())) {
            if (TntRun.getInstance().isGameStarted()) {
                if (!TntRun.getInstance().getDeadPlayers().contains(player.getUniqueId())) {

                    Location location = event.getPlayer().getLocation();

                    Location blockUnder = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() - 1, location.getBlockZ());

                    if (location.getWorld().getBlockAt(blockUnder).getType() == Material.TNT) {

                        Block block = blockUnder.getBlock().getLocation().getBlock();

                        Bukkit.getScheduler().scheduleSyncDelayedTask(DynGames.getInstance(), () -> {
                            block.setType(Material.AIR);
                        }, 6);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (TntRun.getInstance().getTotalPlayers().contains(player.getUniqueId())) {
            if (TntRun.getInstance().isGameStarted()) {
                TntRun.getInstance().getDeadPlayers().add(player.getUniqueId());

                int totalPlayers = TntRun.getInstance().getTotalPlayers().size();
                int remainingPlayers = (totalPlayers - TntRun.getInstance().getDeadPlayers().size());
                if (remainingPlayers <= 0) {
                    for (UUID uuid : TntRun.getInstance().getTotalPlayers()) {
                        Player p = Bukkit.getPlayer(uuid);
                        if (p != null) {
                            User user = DynGames.getInstance().getUser(p);
                            user.sendMessage(Messages.GAME_OVER, player.getDisplayName());
                            p.setGameMode(GameMode.ADVENTURE);
                            p.teleport(Config.QUEUE_LOCATION);
                        }
                    }
                    player.sendMessage(Messages.YOU_WON);
                    DynGames.getInstance().setCurrentGame(null);
                } else {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.teleport(TntRun.getInstance().getRespawnLocation());
                    for (Player p : player.getWorld().getPlayers()) {
                        User user = DynGames.getInstance().getUser(p);
                        user.sendMessage(Messages.PLAYERS_REMAINING, player.getDisplayName(), remainingPlayers);
                    }
                }
            } else {
                player.setGameMode(GameMode.ADVENTURE);
                player.teleport(TntRun.getInstance().getSpawnLocation());
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (TntRun.getInstance().getTotalPlayers().contains(player.getUniqueId())) {
                if (TntRun.getInstance().isGameStarted()) {
                    if (event.getEntity().getWorld().getName().equals(TntRun.getInstance().getOption("world.name"))) {
                        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
