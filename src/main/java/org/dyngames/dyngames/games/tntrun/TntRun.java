package org.dyngames.dyngames.games.tntrun;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
    private Location spawnLocation;
    @Getter
    private Location respawnLocation;
    @Getter
    private List<UUID> deadPlayers = Lists.newArrayList();

    @Override
    public void enable() {

        instance = this;

        Bukkit.getPluginManager().registerEvents(new Listeners(), DynGames.getInstance());

        this.spawnLocation = new Location(
                Bukkit.getWorld(String.valueOf(this.getOption("world.name"))),
                (double) this.getOption("world.spawn.x"),
                (double) this.getOption("world.spawn.y"),
                (double) this.getOption("world.spawn.z"),
                (float) this.getOption("world.spawn.pitch"),
                (float) this.getOption("world.spawn.yaw")
        );
        this.respawnLocation = new Location(
            Bukkit.getWorld(String.valueOf(this.getOption("world.name"))),
            (double) this.getOption("world.respawn.x"),
            (double) this.getOption("world.respawn.y"),
            (double) this.getOption("world.respawn.z"),
            (float) this.getOption("world.respawn.pitch"),
            (float) this.getOption("world.respawn.yaw")
        );
    }

    @Override
    public void start() {

        for (UUID uuid : DynGames.getQueuedPlayers()) {
            Player player = Bukkit.getPlayer(uuid);

            player.teleport(spawnLocation);

        }
    }

    @Override
    public Object getOption(String path) {
        return DynGames.getInstance().getConfig().get("games.tntrun." + path);
    }


    @Override
    public void disable() { }
}
