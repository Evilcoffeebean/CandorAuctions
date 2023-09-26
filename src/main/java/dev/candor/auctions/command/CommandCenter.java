package dev.candor.auctions.command;

import com.google.common.collect.Maps;
import dev.candor.auctions.command.sub.MenuCommand;
import dev.candor.auctions.command.sub.RevokeCommand;
import dev.candor.auctions.command.sub.SellCommand;
import dev.candor.auctions.util.StringUtil;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class CommandCenter implements CommandExecutor {

    @Getter
    private static final Map<String, ICommand> commands = Maps.newHashMap();

    public CommandCenter() {
        commands.put("menu", new MenuCommand());
        commands.put("revoke", new RevokeCommand());
        commands.put("sell", new SellCommand());
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getLabel().equalsIgnoreCase("auctionhouse")) {
            if (args.length == 0) {
                final StringBuilder response = new StringBuilder();
                commands.values().forEach(command -> response.append("&6- &e").append(command.getUsage()).append("\n"));

                sender.sendMessage(StringUtil.color(response.toString()));
                return true;
            }

            final ICommand command = commands.get(args[0].toLowerCase());
            if (command == null || !commands.containsKey(args[0].toLowerCase())) {
                sender.sendMessage(StringUtil.color("&cUnknown AuctionHouse command."));
                return true;
            }

            if (command.playerOnly() && !(sender instanceof Player)) {
                sender.sendMessage(StringUtil.color("&cThis command can only be executed client-side."));
                return true;
            }

            if (args.length != command.getLength()) {
                if (command.matchExact()) {
                    sender.sendMessage(StringUtil.color("&cUsage: &e" + command.getUsage()));
                    return true;
                }

                if (args.length > command.getLength()) {
                    sender.sendMessage(StringUtil.color("&cUsage: &e" + command.getUsage()));
                    return true;
                }

                command.execute(sender, args);
                return true;
            }
            command.execute(sender, args);
            return true;
        }
        return false;
    }
}
