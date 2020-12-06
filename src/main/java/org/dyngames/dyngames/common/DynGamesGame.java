package org.dyngames.dyngames.common;

import java.util.List;

public interface DynGamesGame {

    String getName();

    List<String> getAuthors();

    void enable();

    void start();

    void disable();

    Object getOption(String path);

}
