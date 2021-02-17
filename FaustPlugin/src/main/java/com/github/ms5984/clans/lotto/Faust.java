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
