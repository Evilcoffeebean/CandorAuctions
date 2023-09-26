package dev.candor.auctions.event;

import dev.candor.auctions.Core;
import dev.candor.auctions.engine.AuctionItem;
import dev.candor.auctions.engine.AuctionParticipant;
import dev.candor.auctions.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MainMenuListener implements Listener {

    private AuctionParticipant findOwner(final ItemStack clicked) {
        for (AuctionParticipant participant : Core.getCore().getParticipantManager().getParticipants()) {
            for (AuctionItem item : participant.getItems()) {
                if (item.getItem().isSimilar(clicked)) {
                    return Core.getCore().getParticipantManager().getParticipant(item.getOwner().getUniqueId());
                }
            }
        }
        return null;
    }

    private AuctionItem findItem(final AuctionParticipant participant, final ItemStack clicked) {
        for (AuctionItem item : participant.getItems()) {
            if (item.getItem().isSimilar(clicked))
                return item;
        }
        return null;
    }

    private double getPrice(final AuctionParticipant participant, final ItemStack clicked) {
        AuctionItem item = findItem(participant, clicked);
        return item != null ? item.getPrice() : 0;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onClick(final InventoryClickEvent event) {
        if (event.getClickedInventory() == null)
            return;
        if (!(event.getWhoClicked() instanceof Player player))
            return;
        if (event.getCurrentItem() == null)
            return;

        if (event.getView().getTitle().contains("AUCTIONS")) {
            event.setCancelled(true);
            final AuctionParticipant target = findOwner(event.getCurrentItem());

            if (target == null)
                return;

            final AuctionItem auctionItem = findItem(target, event.getCurrentItem());
            double balance = Core.getCore().getEconomy().getBalance(player);
            double price = getPrice(target, event.getCurrentItem());

            if (auctionItem == null)
                return;

            if (balance < price) {
                player.sendMessage(StringUtil.color("&cInsufficient funds."));
                player.closeInventory();
                return;
            }

            player.closeInventory();
            Core.getCore().getEconomy().withdrawPlayer(player, price);
            Core.getCore().getEconomy().depositPlayer(Bukkit.getPlayer(target.getUuid()), price);

            player.getInventory().addItem(auctionItem.getItem());
            target.remove(auctionItem);

            Bukkit.getOnlinePlayers().forEach(online -> {
                String display = event.getCurrentItem().getType().toString().replace("_", " ");
                online.sendMessage(StringUtil.color("&e" + player.getName() + " &6has bought &e" + display + " &6from &e" + target.getName() + " &6for &e$" + price));
            });
        }
    }
}
