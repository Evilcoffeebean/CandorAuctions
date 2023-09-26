package dev.candor.auctions.event;

import dev.candor.auctions.Core;
import dev.candor.auctions.engine.AuctionItem;
import dev.candor.auctions.engine.AuctionParticipant;
import dev.candor.auctions.util.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RevokeMenuListener implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onClick(final InventoryClickEvent event) {
        if (event.getClickedInventory() == null)
            return;
        if (!(event.getWhoClicked() instanceof Player player))
            return;
        if (event.getCurrentItem() == null)
            return;

        if (event.getView().getTitle().contains("'s Items")) {
            event.setCancelled(true);
            final AuctionParticipant participant = Core.getCore().getParticipantManager().getParticipant(player.getUniqueId());

            for (AuctionItem item : participant.getItems()) {
                if (event.getCurrentItem().isSimilar(item.getItem())) {
                    participant.remove(item);
                    player.getInventory().addItem(item.getItem());
                    player.closeInventory();
                    player.sendMessage(StringUtil.color("&6Item removed from auction."));
                    return;
                }
            }
        }
    }
}
