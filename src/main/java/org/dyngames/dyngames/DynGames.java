package org.dyngames.dyngames;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.dyngames.dyngames.api.User;
import org.dyngames.dyngames.common.Config;
import org.dyngames.dyngames.common.DynGamesGame;
import org.dyngames.dyngames.common.PlayerListener;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class DynGames extends JavaPlugin {

    @Getter
    private static DynGames instance;
    private final HashMap<Player, User> loadedUsers = new HashMap<>();
    private final Set<DynGamesGame> loadedGames = new HashSet<>();
    private PaperCommandManager manager;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        this.manager = new PaperCommandManager(this);

        this.manager.registerCommand(new DynGamesCommand());

        this.loadGames();
    }

    @Override
    public void onDisable() {
        //
    }

    private void loadGames() {
        for (String game : Config.GAMES) {
            String clazz = getGameClassName(game);
            Class<?> gameClass;
            try {

                gameClass = Class.forName(clazz);

                DynGamesGame gameInstance = (DynGamesGame) gameClass.getDeclaredConstructor().newInstance();
                gameInstance.load();
                this.loadedGames.add(gameInstance);
                Bukkit.getLogger().info("Loaded game " + game + "...");

            } catch (ClassNotFoundException e) {
                Bukkit.getLogger().warning("Failed to load game: " + game + ", are you sure it exists? Attempted to find class: " + clazz);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
                Bukkit.getLogger().warning("Failed to load game: " + game + ".");
            }
        }
    }

    private String getGameClassName(String game) {
        switch (game.toLowerCase()) {
            case "tntrun":
                return "org.dyngames.dyngames.games.tntrun.TntRun";
            default:
                return null;
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
