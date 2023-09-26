package dev.candor.auctions.command;

import org.bukkit.command.CommandSender;

public interface ICommand {

    String getCommand();
    String getUsage();
    int getLength();
    boolean matchExact();
    boolean playerOnly();
    void execute(CommandSender sender, String[] args);
}
