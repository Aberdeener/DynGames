package org.dyngames.dyngames.common;

import org.bukkit.ChatColor;

public class Messages {

    public static String PLAYER_ONLY = ChatColor.RED + "Only players can use this command.";

    public static String NO_PERMISSION = ChatColor.RED + "You do not have permission to use this command.";

    public static String JOINED_QUEUE = ChatColor.GREEN + "You have joined the queue. There are now " + ChatColor.YELLOW + "%d" + ChatColor.GREEN + " players in queue.";

    public static String NOT_ENOUGH_PLAYERS = ChatColor.RED + "Not enough players are in the queue to start a new game. Minimum players: %d, queued players: %d.";
}
