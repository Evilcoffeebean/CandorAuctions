package dev.candor.auctions.event;

import dev.candor.auctions.Core;
import dev.candor.auctions.engine.AuctionParticipant;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinQuitEvents implements Listener {

    @EventHandler (ignoreCancelled = true, priority = EventPriority.LOW)
    public void onJoin(final PlayerJoinEvent event) {
        final UUID id = event.getPlayer().getUniqueId();
        final String name = event.getPlayer().getName();

        if (!Core.getCore().getParticipantManager().isSaved(id))
            Core.getCore().getParticipantManager().saveUser(id, new AuctionParticipant(id, name));
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.LOW)
    public void onQuit(final PlayerQuitEvent event) {
        final UUID id = event.getPlayer().getUniqueId();
        final AuctionParticipant participant = Core.getCore().getParticipantManager().getParticipant(id);

        if (Core.getCore().getParticipantManager().isSaved(id)) {
            participant.getItems().forEach(item -> event.getPlayer().getInventory().addItem(item.getItem()));
            participant.clear();
            Core.getCore().getParticipantManager().removeUser(id);
        }
    }
}
