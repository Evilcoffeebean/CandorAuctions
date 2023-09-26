package dev.candor.auctions.ux;

import dev.candor.auctions.Core;
import dev.candor.auctions.engine.AuctionParticipant;
import dev.candor.auctions.util.MenuUtil;
import org.bukkit.entity.Player;

public class AuctionMenu extends MenuUtil {

    public AuctionMenu() {
        super("&5&lAUCTIONS", 54);
    }

    public void buildAndOpen(final Player player) {
        for (AuctionParticipant participant : Core.getCore().getParticipantManager().getParticipants()) {
            for (int i = 0; i < participant.getItems().size(); i++)
                setItem(i, participant.getItems().get(i).getItem());
        }
        build(player);
    }
}
