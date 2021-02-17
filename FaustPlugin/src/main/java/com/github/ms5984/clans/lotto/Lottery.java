package com.github.ms5984.clans.lotto;

import com.github.ms5984.clans.lotto.api.model.Ticket;
import com.youtube.hempfest.clans.construct.api.ClansAPI;
import lombok.Getter;
import lombok.val;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a running lottery.
 */
public final class Lottery implements Serializable {
    private static final long serialVersionUID = -2264763922448603191L;
    @Getter
    protected final Set<Ticket> tickets = new HashSet<>();
    @Getter
    protected final Map<Ticket, Location> locations = new HashMap<>();
    private final World world;

    /**
     * Start a lottery in a given world.
     * @param world world to use
     */
    public Lottery(World world) {
        this.world = world;
/*        tickets.add(firstTicket);
        val player = firstTicket.getPlayer();
        if (player == null) {
            val uid = firstTicket.getPlayerUid();
            if (ClansAPI.getData().playerClan.containsKey(uid))
                throw new IllegalArgumentException("The player is not online or a member of a clan!");
            val baseLoc = ClansAPI.getInstance().getClan(uid).getBase();
            this.world = baseLoc.getWorld();
        } else {
            this.world = player.getWorld();
        }*/
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        locations.computeIfAbsent(ticket, t -> {
            if (t.getPlayer() != null) {
                val location = t.getPlayer().getLocation();
                if (world == location.getWorld())
                    return location;
            }
            val playerUid = t.getPlayerUid();
            if (ClansAPI.getData().playerClan.containsKey(playerUid)) {
                return ClansAPI.getInstance().getClan(playerUid).getBase();
            }
            return null;
        });
    }

    public void updateLocations() {
        tickets.forEach(ticket -> locations.computeIfPresent(ticket, (key, oldVal) -> {
            val player = ticket.getPlayer();
            if (player == null) return oldVal;
            val playerLocation = player.getLocation();
            if (playerLocation.getWorld() != world) return oldVal;
            val newLoc = player.getLocation();
            return oldVal.add(newLoc).multiply(0.5);
        }));
    }
}