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
package com.github.ms5984.clans.lotto.api.events;

import com.youtube.hempfest.clans.construct.Clan;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * Called when a ticket is purchased.
 */
public final class TicketPreBuyEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final Clan clan;
    private final Player buyer;
    private BigDecimal price;
    private boolean cancelled;

    /**
     * A ticket is being purchased by a clan member.
     * @param clan the buying clan
     * @param buyer the buying player
     * @param price initial cost of the ticket
     */
    public TicketPreBuyEvent(Clan clan, Player buyer, BigDecimal price) {
        this.clan = clan;
        this.buyer = buyer;
        this.price = price;
    }

    /**
     * Get the player that is buying a ticket.
     * <p>Typically, this is the clan leader.</p>
     * @return player
     */
    public Player getBuyer() {
        return buyer;
    }

    /**
     * Get the clan that is buying this ticket.
     * @return clan
     */
    public Clan getBuyingClan() {
        return clan;
    }

    /**
     * Set the price of the ticket.
     * @param price a new price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Get the price of the ticket.
     * @return ticket price
     */
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
