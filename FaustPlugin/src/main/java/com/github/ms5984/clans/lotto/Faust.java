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

import com.github.ms5984.clans.lotto.api.FaustAPI;
import com.github.ms5984.clans.lotto.api.model.LottoResult;
import com.github.ms5984.clans.lotto.api.model.Ticket;
import com.github.ms5984.clans.lotto.listeners.LotteryRunner;
import com.youtube.hempfest.clans.construct.api.ClansAPI;
import org.bukkit.World;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class Faust extends JavaPlugin implements FaustAPI {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getServicesManager().register(FaustAPI.class, this, this, ServicePriority.Normal);
        ClansAPI.getInstance().searchNewAddons(this, "com.github.ms5984.clans.lotto.cycles");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getServicesManager().unregister(this);
    }

    @Override
    public BigDecimal ticketPrice() {
        return new BigDecimal(Objects.requireNonNull(getConfig().getString("ticket-price")));
    }

    @Override
    public int ticketLimitPerClan() {
        return getConfig().getInt("limit-per-clan", -1);
    }

    @Override
    public Set<Ticket> getTicketsInPot() {
        return LotteryRunner.getCurrentRunner()
                .flatMap(LotteryRunner::getLottery)
                .map(Lottery::getTickets)
                .orElseGet(Collections::emptySet);
    }

    @Override
    public Optional<LottoResult> callLotto() {
        return LotteryRunner.getCurrentRunner().flatMap(LotteryRunner::endLotto);
    }

    @Override
    public void startLotto(World world) {
        LotteryRunner.getCurrentRunner()
                .ifPresent(lotteryRunner -> lotteryRunner.startLotto(world));
    }
}
