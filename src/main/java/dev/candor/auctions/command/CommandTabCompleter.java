package dev.candor.auctions.command;

import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class CommandTabCompleter implements TabCompleter {

    private final List<String> completions = Lists.newArrayList();

    public CommandTabCompleter() {
        CommandCenter.getCommands().values().forEach(command -> completions.add(command.getCommand()));
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        }
        return completions;
    }
}
