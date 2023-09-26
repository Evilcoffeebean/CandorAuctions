package dev.candor.auctions.command.sub;

import dev.candor.auctions.command.ICommand;
import dev.candor.auctions.ux.RevokeMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RevokeCommand implements ICommand {

    @Override
    public String getCommand() {
        return "revoke";
    }

    @Override
    public String getUsage() {
        return "/ah revoke";
    }

    @Override
    public int getLength() {
        return 1;
    }

    @Override
    public boolean matchExact() {
        return true;
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        new RevokeMenu((Player) sender).buildAndOpen();
    }
}
