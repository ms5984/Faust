/*
 *  Copyright 2021 ms5984 (Matt) <https://github.com/ms5984>
 *
 *  This file is part of FaustAPI.
 *
 *  FaustAPI is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  FaustAPI is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.github.ms5984.clans.lotto.api.model;

import com.github.sanctum.labyrinth.library.HUID;
import com.youtube.hempfest.clans.construct.Clan;
import com.youtube.hempfest.clans.construct.api.ClansAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents a ticket purchased.
 */
public final class Ticket implements Serializable {
    private static final long serialVersionUID = 688082801681935188L;
    private transient Clan clan;
    private final String clanId;
    private transient Player player;
    private final UUID playerUid;
    private final HUID ticketNumber;
    private final BigDecimal price;

    /**
     * A ticket is purchased by a player in a clan for a price.
     *
     * @param clan clan of player
     * @param player buying player
     * @param price price paid
     */
    public Ticket(Clan clan, Player player, BigDecimal price) {
        this.clan = clan;
        this.clanId = clan.getClanID();
        this.player = player;
        this.playerUid = player.getUniqueId();
        this.ticketNumber = HUID.randomID();
        this.price = price;
    }

    /**
     * Get the clan that purchased this ticket.
     *
     * @return the clan
     */
    public Clan getClan() {
        return (clan != null) ? clan : (clan = ClansAPI.getInstance().getClan(clanId));
    }

    /**
     * Get the Player that purchased this ticket.
     * <p>
     * This method will attempt to resolve playerUid
     * into a Player object. This won't be possible if the
     * player is not online after a plugin restart, in
     * which case this method will return null.
     *
     * @return player reference if available or null
     */
    @Nullable
    public Player getPlayer() {
        return (player != null) ? player : (player = Bukkit.getPlayer(playerUid));
    }

    /**
     * Get a fallback OfflinePlayer reference from this ticket.
     * <p>
     * If {@link #getPlayer} is null, you can use this method to easily
     * retrieve a backup OfflinePlayer. If #getPlayer is not null
     * this method may return that Player object.
     *
     * @return player reference as OfflinePlayer
     */
    public OfflinePlayer getPlayerOffline() {
        return (player != null) ? player : Bukkit.getOfflinePlayer(playerUid);
    }

    /**
     * Get the UniqueId of the player that purchased this ticket.
     *
     * @return UUID of player's UniqueId
     */
    public UUID getPlayerUid() {
        return playerUid;
    }

    /**
     * Get the embedded ticket number.
     *
     * @return HUID ticket number
     */
    public HUID getTicketNumber() {
        return ticketNumber;
    }

    /**
     * Get the price paid for this ticket.
     *
     * @return price paid for the ticket
     */
    public BigDecimal getPrice() {
        return price;
    }
}
