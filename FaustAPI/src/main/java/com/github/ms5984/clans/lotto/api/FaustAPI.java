package com.github.ms5984.clans.lotto.api;

import com.github.ms5984.clans.lotto.api.model.LottoResult;
import com.github.ms5984.clans.lotto.api.model.Ticket;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

public interface FaustAPI {
    /**
     * Get the ticket price set in the config.
     * @return the configured ticket price as a BigDecimal
     * @throws NumberFormatException if 'ticket-price' does
     * not represent a BigDecimal
     * @throws NullPointerException if 'ticket-price' is unset
     */
    BigDecimal ticketPrice();

    /**
     * Get the limit for tickets purchased per clan, per pot.
     * @return the maximum number of tickets one clan may
     * purchase per lottery. -1 equals unlimited
     */
    int ticketLimitPerClan();

    /**
     * Get all tickets in the pot.
     * @return set of purchased tickets
     */
    Set<Ticket> getTicketsInPot();

    /**
     * End the current lotto and choose a winner immediately.
     * @return an Optional describing the result. If no lottery
     * is happening and/or the call is cancelled, this will
     * return an empty Optional.
     */
    Optional<LottoResult> callLotto();

    /**
     * Start a new lottery in world world.
     * <p>Ends any existing lottery.</p>
     * @param world the calling world.
     */
    void startLotto(World world);

    static FaustAPI getInstance() {
        final RegisteredServiceProvider<FaustAPI> rsp = Bukkit.getServicesManager().getRegistration(FaustAPI.class);
        if (rsp == null) throw new IllegalStateException("Faust is not loaded!");
        return rsp.getProvider();
    }
}
