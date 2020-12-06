package org.dyngames.dyngames.common;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dyngames.dyngames.DynGames;
import org.dyngames.dyngames.api.User;

public class StartGameCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if(!(sender instanceof Player)) {
            sender.sendMessage(Messages.PLAYER_ONLY);
            return true;
        }

        if (sender.hasPermission(Permissions.FORCE_START_GAME_COMMAND)) {
            User user = DynGames.getInstance().getUser((Player) sender);
        } else {
            sender.sendMessage(Messages.NO_PERMISSION);
        }
        
        return true;
    }
}
