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
     *
     * @return the configured ticket price as a BigDecimal
     * @throws NumberFormatException if 'ticket-price' does
     * not represent a BigDecimal
     * @throws NullPointerException if 'ticket-price' is unset
     */
    BigDecimal ticketPrice();

    /**
     * Get the limit for tickets purchased per clan, per pot.
     *
     * @return the maximum number of tickets one clan may
     * purchase per lottery. -1 equals unlimited
     */
    int ticketLimitPerClan();

    /**
     * Get all tickets in the pot.
     *
     * @return set of purchased tickets
     */
    Set<Ticket> getTicketsInPot();

    /**
     * End the current lotto and choose a winner immediately.
     *
     * @return an Optional describing the result. If no lottery
     * is happening and/or the call is cancelled, this will
     * return an empty Optional.
     */
    Optional<LottoResult> callLotto();

    /**
     * Start a new lottery in world world.
     * <p>
     * Ends any existing lottery.
     *
     * @param world the calling world.
     */
    void startLotto(World world);

    /**
     * Retrieve the FaustAPI provider via Bukkit RSP.
     *
     * @return instance of api
     */
    static FaustAPI getInstance() {
        final RegisteredServiceProvider<FaustAPI> rsp = Bukkit.getServicesManager().getRegistration(FaustAPI.class);
        if (rsp == null) throw new IllegalStateException("Faust is not loaded!");
        return rsp.getProvider();
    }
}
