package dev.candor.auctions.command.sub;

import dev.candor.auctions.Core;
import dev.candor.auctions.command.ICommand;
import dev.candor.auctions.engine.AuctionItem;
import dev.candor.auctions.engine.AuctionParticipant;
import dev.candor.auctions.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SellCommand implements ICommand {

    @Override
    public String getCommand() {
        return "sell";
    }

    @Override
    public String getUsage() {
        return "/ah sell <price>";
    }

    @Override
    public int getLength() {
        return 2;
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
        final Player player = (Player) sender;
        final AuctionParticipant participant = Core.getCore().getParticipantManager().getParticipant(player.getUniqueId());

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage(StringUtil.color("&cYou need to be holding an item to auction it."));
            return;
        }

        final ItemStack hand = player.getInventory().getItemInMainHand();

        try {
            double price = Double.parseDouble(args[1]);
            AuctionItem item = new AuctionItem(player, hand, price);
            participant.addItem(item);
            player.getInventory().remove(item.getItem());

            Bukkit.getOnlinePlayers().forEach(online -> {
                String display = item.getItem().hasItemMeta() ? item.getItem().getItemMeta().getDisplayName() : item.getItem().getType().toString().replace("_", " ");
                online.sendMessage(StringUtil.color("&e" + player.getName() + " &6is selling " + display + " &6for &e$" + price));
            });
        } catch (NumberFormatException e) {
            player.sendMessage(StringUtil.color("&cInvalid price amount specified."));
        }
    }
}
