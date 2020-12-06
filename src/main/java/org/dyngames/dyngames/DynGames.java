package org.dyngames.dyngames;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.dyngames.dyngames.api.User;
import org.dyngames.dyngames.api.Utils;
import org.dyngames.dyngames.commands.JoinQueueCommand;
import org.dyngames.dyngames.common.Config;
import org.dyngames.dyngames.common.DynGamesGame;
import org.dyngames.dyngames.common.Messages;
import org.dyngames.dyngames.common.PlayerListener;
import org.dyngames.dyngames.games.tntrun.TntRun;

import java.util.*;

public final class DynGames extends JavaPlugin {

    @Getter
    private static DynGames instance;
    private final List<DynGamesGame> loadedGames = Lists.newArrayList();
    private final Map<Player, User> loadedUsers = new HashMap<>();
    @Getter
    private static final Set<UUID> queuedPlayers = new HashSet<>();
    @Getter
    @Setter
    private DynGamesGame currentGame = null;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getCommand("joinqueue").setExecutor(new JoinQueueCommand());

        getConfig().options().copyDefaults(true);
        saveConfig();

        this.loadGames();

        this.startQueueTask();
    }

    @Override
    public void onDisable() {
        for (DynGamesGame gameInstance : this.loadedGames) {
            gameInstance.disable();
        }
    }

    private void loadGames() {
        for (String game : Config.GAMES.getKeys(false)) {
            if (this.getConfig().getBoolean("games." + game + ".enabled")) {
                DynGamesGame gameInstance = null;
                boolean loaded = false;

                if (game.equalsIgnoreCase("tntrun")) {
                    gameInstance = new TntRun();
                    loaded = true;
                }

                if (loaded) {
                    gameInstance.enable();
                    this.loadedGames.add(gameInstance);
                    Bukkit.getLogger().info("Loaded game: " + gameInstance.getName() + ", by: " + Utils.listToString(gameInstance.getAuthors()));
                } else {
                    Bukkit.getLogger().warning("Failed to load game: " + game + ", are you sure it exists?");
                }
            }
        }
    }

    private void startQueueTask() {
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            if (this.currentGame == null) {

                DynGamesGame nextGame = this.loadedGames.get(new Random().nextInt(loadedGames.size()));
                if (queuedPlayers.size() >= (int) nextGame.getOption("min_players")) {
                    this.setCurrentGame(nextGame);
                    nextGame.start();
                } else {
                    Bukkit.broadcastMessage(Messages.NOT_ENOUGH_PLAYERS);
                }
            }
        }, 600L, 600L);

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

    public void removeUser(Player player) {
        this.loadedUsers.remove(player);
        getQueuedPlayers().remove(player.getUniqueId());
    }
}
