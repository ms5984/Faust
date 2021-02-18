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
package com.github.ms5984.clans.lotto.listeners;

import com.github.ms5984.clans.lotto.Lottery;
import com.github.ms5984.clans.lotto.api.events.LotteryPreEndEvent;
import com.github.ms5984.clans.lotto.api.events.LotteryPreBeginEvent;
import com.github.ms5984.clans.lotto.api.model.LottoResult;
import com.github.ms5984.clans.lotto.api.model.Ticket;
import com.github.ms5984.clans.lotto.cycles.FaustCycle;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * Coordinates lottery events and maintains lottery state.
 */
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class LotteryRunner implements Listener {
    final Random random = new Random();
    transient Lottery lottery;

    /**
     * Check for running lottery so we do not replace it if one is present.
     * <p>If a lottery is currently going on, cancel the event.</p>
     * @param e LotteryPreBeginEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLottoPreBegin(LotteryPreBeginEvent e) {
        if (lottery != null) {
            e.setCancelled(true);
        }
    }

    /**
     * Start a new lottery.
     * @param e LotteryPreBeginEvent
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onLottoPreBeginFinal(LotteryPreBeginEvent e) {
        this.lottery = new Lottery(e.getWorld());
    }

    /**
     * Process lottery results.
     * @param e LotteryPreEndEvent
     */
    @EventHandler(ignoreCancelled = true)
    public void onLottoPreEndProcess(LotteryPreEndEvent e) {
        if (lottery == null) {
            e.setCancelled(true);
            return;
        }
        val tickets = new ArrayList<>(lottery.getTickets());
        val ticket = tickets.get(random.nextInt(tickets.size()));
        val potValue = tickets.stream().map(Ticket::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        val lottoResult = new LottoResult(ticket.getClan(), lottery.getLocations().get(ticket), potValue);
        e.setLottoResult(lottoResult);
    }

    /**
     * Clean up the lottery variable after a successful lotto.
     * @param e a LotteryPreEndEvent
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onLottoEndSuccess(LotteryPreEndEvent e) {
        lottery = null;
    }

    /**
     * End a lottery.
     * <p>This will return empty on event cancel and/or unset results.</p>
     * @return an Optional that describes the lottery's results
     */
    public Optional<LottoResult> endLotto() {
        val event = new LotteryPreEndEvent();
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return Optional.empty();
        return Optional.ofNullable(event.getLottoResult());
    }

    /**
     * Start a lottery in a world.
     * @param world world to hold lottery in
     */
    public void startLotto(World world) {
        if (lottery != null) {
            endLotto();
        }
        Bukkit.getPluginManager().callEvent(new LotteryPreBeginEvent(world));
    }

    /**
     * Get the lottery currently running.
     * @return an Optional that describes the running lottery if one is present
     */
    public Optional<Lottery> getLottery() {
        return Optional.ofNullable(lottery);
    }

    /**
     * Get the current runner.
     * @return an Optional that describes the current runner if one is present
     */
    public static Optional<LotteryRunner> getCurrentRunner() {
        return Optional.ofNullable(FaustCycle.getRunner());
    }

}
