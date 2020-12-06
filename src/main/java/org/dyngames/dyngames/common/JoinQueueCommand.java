package org.dyngames.dyngames.common;

import lombok.Data;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dyngames.dyngames.DynGames;
import org.dyngames.dyngames.api.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class JoinQueueCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.PLAYER_ONLY);
            return true;
        }

        if (sender.hasPermission(Permissions.JOIN_QUEUE_COMMAND)) {
            User user = DynGames.getInstance().getUser((Player) sender);
            DynGames.getQueuedPlayers().add(user.getPlayer().getUniqueId());

            user.sendMessage(Messages.JOINED_QUEUE, DynGames.getQueuedPlayers().size());
        } else {
            sender.sendMessage(Messages.NO_PERMISSION);
        }

        return false;
    }
}