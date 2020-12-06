package org.dyngames.dyngames;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.dyngames.dyngames.api.User;
import org.dyngames.dyngames.common.Config;
import org.dyngames.dyngames.common.DynGamesGame;
import org.dyngames.dyngames.common.PlayerListener;
import org.dyngames.dyngames.games.tntrun.TntRun;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class DynGames extends JavaPlugin {

    @Getter
    private static DynGames instance;
    private final HashMap<Player, User> loadedUsers = new HashMap<>();
    private final Set<DynGamesGame> loadedGames = new HashSet<>();

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        this.loadGames();
    }

    @Override
    public void onDisable() {
        //
    }

    private void loadGames() {
        for (String game : Config.GAMES) {

            DynGamesGame gameInstance = null;
            boolean loaded = false;

            if (game.equalsIgnoreCase("tntrun")) {
                gameInstance = new TntRun();
                loaded = true;
            }

            if (loaded) {
                gameInstance.enable();
                this.loadedGames.add(gameInstance);
                Bukkit.getLogger().info("Loaded game: " + gameInstance.getName() + ", by: " + gameInstance.getAuthors());
            } else {
                Bukkit.getLogger().warning("Failed to load game: " + game + ", are you sure it exists?");
            }
        }
    }

    public User getUser(Player player) {
        if (this.loadedUsers.containsKey(player)) {
            return this.loadedUsers.get(player);
        } else {
            User user = new User(player);
            this.loadedUsers.put(player, user);
            return user;
        }
    }
}
