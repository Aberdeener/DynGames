package org.dyngames.dyngames.common;

import java.util.List;

public interface DynGamesGame {

    String getName();

    List<String> getAuthors();

    DynGamesGame getInstance();

    void enable();

    void start();

    void disable();

    int getMinPlayers();

    Object getOption(String path);

}
