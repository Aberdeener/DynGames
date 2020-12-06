package org.dyngames.dyngames.common;

import java.util.List;

public interface DynGamesGame {

    void load();

    String getName();

    List<String> getAuthors();

    void enable();

    void disable();

}
