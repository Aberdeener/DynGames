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


public class Listeners implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!TntRun.getInstance().getDeadPlayers().contains(player.getUniqueId())) {

            Location location = event.getPlayer().getLocation();

            Location blockUnder = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() - 1, location.getBlockZ());

            if (location.getWorld().getBlockAt(blockUnder).getType() == Material.TNT) {

                Block block = blockUnder.getBlock().getLocation().getBlock();

                Bukkit.getScheduler().scheduleSyncDelayedTask(DynGames.getInstance(), () -> {
                    block.setType(Material.AIR);
                }, 10);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        TntRun.getInstance().getDeadPlayers().add(event.getEntity().getUniqueId());
        event.getEntity().setGameMode(GameMode.SPECTATOR);
        event.getEntity().teleport(TntRun.getInstance().getRespawnLocation());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity().getWorld().getName() == DynGames.getInstance().getCurrentGame().getOption("world.name")) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
        }
    }
}
