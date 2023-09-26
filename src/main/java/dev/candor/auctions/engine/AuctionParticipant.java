package dev.candor.auctions.engine;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class AuctionParticipant {

    private final UUID uuid;
    private final String name;
    private final List<AuctionItem> items;

    public AuctionParticipant(final UUID id, final String displayName) {
        this.uuid = id;
        this.name = displayName;
        this.items = Lists.newArrayList();
    }

    public void addItem(final AuctionItem item) {
        items.add(item);
    }

    public void remove(final AuctionItem item) {
        items.remove(item);
    }

    public boolean exists(final AuctionItem item) {
        return items.contains(item);
    }

    public void clear() {
        items.clear();
    }
}
