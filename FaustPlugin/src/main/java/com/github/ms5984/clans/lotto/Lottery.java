/*
 *  Copyright 2021 ms5984 (Matt) <https://github.com/ms5984>
 *
 *  This file is part of FaustAPI.
 *
 *  FaustAPI is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  FaustAPI is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.github.ms5984.clans.lotto;

import com.github.ms5984.clans.lotto.api.model.Ticket;
import com.youtube.hempfest.clans.construct.api.ClansAPI;
import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;
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
    private transient World world;
    private final UUID worldUuid;

    /**
     * Start a lottery in a given world.
     * @param world world to use
     */
    public Lottery(World world) {
        this.world = world;
        this.worldUuid = world.getUID();
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

    private void initializeWorld() {
        if (world == null) world = Bukkit.getWorld(worldUuid);
    }

    /**
     * Add a ticket to the pot.
     * @param ticket ticket to add
     */
    public void addTicket(Ticket ticket) {
        initializeWorld();
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

    /**
     * Update tickets' location data.
     */
    public void updateLocations() {
        initializeWorld();
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
