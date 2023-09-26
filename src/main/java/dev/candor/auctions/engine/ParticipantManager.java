package dev.candor.auctions.engine;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public final class ParticipantManager {

    private static final Map<UUID, AuctionParticipant> users = Maps.newConcurrentMap();

    public boolean isSaved(final UUID id) {
        return users.containsKey(id);
    }

    public void saveUser(final UUID id, final AuctionParticipant participant) {
        users.put(id, participant);
    }

    public void removeUser(final UUID id) {
        users.remove(id);
    }

    public Collection<AuctionParticipant> getParticipants() {
        return users.values();
    }

    public AuctionParticipant getParticipant(final UUID id) {
        return users.get(id);
    }

    public void clearUsers() {
        users.clear();
    }
}
