package com.github.ms5984.clans.lotto.api.events;

import com.github.ms5984.clans.lotto.api.model.Ticket;
import com.youtube.hempfest.clans.construct.Clan;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a ticket is purchased.
 */
public final class TicketBuyEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final Clan clan;
    private final Player buyer;
    private final Ticket ticket;

    /**
     * A ticket is purchased by a clan member.
     * @param clan the buying clan
     * @param buyer the buying player
     * @param ticket the ticket purchased
     */
    public TicketBuyEvent(Clan clan, Player buyer, Ticket ticket) {
        this.clan = clan;
        this.buyer = buyer;
        this.ticket = ticket;
    }

    /**
     * Get the player that bought this ticket.
     * <p>Typically, this is the clan leader.</p>
     * @return player
     */
    public Player getBuyer() {
        return buyer;
    }

    /**
     * Get the clan that bought this ticket.
     * @return clan
     */
    public Clan getBuyingClan() {
        return clan;
    }

    /**
     * Get the Ticket object purchased.
     * @return a new ticket
     */
    public Ticket getTicket() {
        return ticket;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
