package org.dyngames.dyngames.games.tntrun;

import org.bukkit.Bukkit;
import org.dyngames.dyngames.common.DynGamesGame;

import java.util.Arrays;
import java.util.List;

public class TntRun implements DynGamesGame {

    public String getName() {
        return "TntRun";
    }

    public List<String> getAuthors() {
        return Arrays.asList("Aberdeener", "2xjtn");
    }

    @Override
    public void enable() {
        Bukkit.getLogger().info("initiated tntrun pog!");
    }

    @Override
    public void disable() {

    }
}
