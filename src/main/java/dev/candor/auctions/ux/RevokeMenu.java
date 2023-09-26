package dev.candor.auctions.ux;

import dev.candor.auctions.Core;
import dev.candor.auctions.engine.AuctionParticipant;
import dev.candor.auctions.util.MenuUtil;
import org.bukkit.entity.Player;

public class RevokeMenu extends MenuUtil {

    private final Player player;

    public RevokeMenu(final Player player) {
        super("&e" + player.getName() + "&6's Items", 54);
        this.player = player;
    }

    public void buildAndOpen() {
        final AuctionParticipant participant = Core.getCore().getParticipantManager().getParticipant(player.getUniqueId());
        for (int i = 0; i < participant.getItems().size(); i++)
            setItem(i, participant.getItems().get(i).getItem());
        build(player);
    }
}
