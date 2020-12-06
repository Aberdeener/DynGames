package org.dyngames.dyngames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dyngames.dyngames.DynGames;
import org.dyngames.dyngames.api.User;
import org.dyngames.dyngames.common.Messages;
import org.dyngames.dyngames.common.Permissions;

public class JoinQueueCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.PLAYER_ONLY);
            return true;
        }

        if (sender.hasPermission(Permissions.JOIN_QUEUE_COMMAND)) {
            User user = DynGames.getInstance().getUser((Player) sender);
            DynGames.getInstance().getQueuedPlayers().add(user.getPlayer().getUniqueId());

            user.sendMessage(Messages.JOINED_QUEUE, DynGames.getInstance().getQueuedPlayers().size());
        } else {
            sender.sendMessage(Messages.NO_PERMISSION);
        }

        return false;
    }
}