package org.dyngames.dyngames.games.tntrun;

import org.dyngames.dyngames.common.DynGamesGame;

import java.util.Arrays;
import java.util.List;

public class TntRun implements DynGamesGame {

    private final String name = "TntRun";
    private final List<String> authors = Arrays.asList("Aberdeener", "2xjtn");

    @Override
    public void load() {
        System.out.println("initiated tntrun pog!");
    }

    public String getName() {
        return this.name;
    }

    public List<String> getAuthors() {
        return this.authors;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
