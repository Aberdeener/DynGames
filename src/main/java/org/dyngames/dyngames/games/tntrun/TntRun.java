package org.dyngames.dyngames.games.tntrun;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.dyngames.dyngames.DynGames;
import org.dyngames.dyngames.api.User;
import org.dyngames.dyngames.common.DynGamesGame;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TntRun implements DynGamesGame {

    public String getName() {
        return "TNT Run";
    }

    public List<String> getAuthors() {
        return Arrays.asList("Aberdeener", "2xjtn");
    }

    @Getter
    private static TntRun instance;
    @Getter
    private Location spawnLocation;
    @Getter
    private Location respawnLocation;
    @Getter
    private final List<UUID> deadPlayers = Lists.newArrayList();
    @Getter
    private boolean gameStarted = false;
    @Getter
    private final List<UUID> totalPlayers = Lists.newArrayList();
    @Getter
    private int minPlayers;

    @Override
    public void enable() {

        instance = this;

        Bukkit.getPluginManager().registerEvents(new Listeners(), DynGames.getInstance());

        World gameWorld = Bukkit.getWorld(String.valueOf(this.getOption("world.name")));

        this.spawnLocation = new Location(
            gameWorld,
            (double) this.getOption("world.spawn.x"),
            (double) this.getOption("world.spawn.y"),
            (double) this.getOption("world.spawn.z"),
            (float) ((double) this.getOption("world.spawn.pitch")),
            (float) ((double) this.getOption("world.spawn.yaw"))
        );
        this.respawnLocation = new Location(
            gameWorld,
            (double) this.getOption("world.respawn.x"),
            (double) this.getOption("world.respawn.y"),
            (double) this.getOption("world.respawn.z"),
            (float) ((double) this.getOption("world.respawn.pitch")),
            (float) ((double) this.getOption("world.respawn.yaw"))
        );

        this.minPlayers = (int) this.getOption("min_players");
    }

    @Override
    public void start() {

        for (UUID uuid : DynGames.getInstance().getQueuedPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                this.totalPlayers.add(player.getUniqueId());
                player.setGameMode(GameMode.ADVENTURE);
                player.teleport(spawnLocation);
                player.sendMessage(Messages.GAME_STARTING_TEN_SECONDS);
            }
        }

        Bukkit.getScheduler().runTaskLater(DynGames.getInstance(), () -> {
            for (UUID uuid : this.totalPlayers) {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    player.teleport(respawnLocation);
                    player.sendMessage(Messages.GAME_STARTED);
                }
            }
            this.gameStarted = true;
        }, 200L);
    }

    @Override
    public Object getOption(String path) {
        return DynGames.getInstance().getConfig().get("games.tntrun." + path);
    }

    @Override
    public void disable() { }
}
