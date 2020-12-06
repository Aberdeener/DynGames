package org.dyngames.dyngames;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;
import org.dyngames.dyngames.api.User;
import org.dyngames.dyngames.common.Messages;

@CommandAlias("dyngames")
@CommandPermission("dyngames.admin")
public class DynGamesCommand extends BaseCommand {

    @Default
    public static void dynGamesCommand(Player player, String[] args) {
        User user = DynGames.getInstance().getUser(player);
        user.getPlayer().sendMessage(Messages.DYNGAMES_ROOT_COMMAND_HELP);
    }

}
