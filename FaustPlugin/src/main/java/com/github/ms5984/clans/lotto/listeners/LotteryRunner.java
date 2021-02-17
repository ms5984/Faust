package com.github.ms5984.clans.lotto.listeners;

import com.github.ms5984.clans.lotto.Lottery;
import com.github.ms5984.clans.lotto.api.events.LotteryEndEvent;
import com.github.ms5984.clans.lotto.api.model.LottoResult;
import com.github.ms5984.clans.lotto.api.model.Ticket;
import com.github.ms5984.clans.lotto.cycles.FaustCycle;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class LotteryRunner implements Listener {
    private final Random random = new Random();
    private Lottery lottery;

    public Optional<LottoResult> endLotto() {
        if (lottery == null) return Optional.empty();
        val tickets = new ArrayList<>(lottery.getTickets());
        final Ticket ticket = tickets.get(random.nextInt(tickets.size()));
        val potValue = tickets.stream().map(Ticket::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        val lottoResult = new LottoResult(ticket.getClan(), lottery.getLocations().get(ticket), potValue);
        val event = new LotteryEndEvent();
        event.setLottoResult(lottoResult);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) return Optional.empty();
        return Optional.ofNullable(event.getLottoResult());
    }

    public void startLotto(World world) {
        if (lottery != null) {
            endLotto();
        }
        this.lottery = new Lottery(world);
    }

    public Optional<Lottery> getLottery() {
        return Optional.ofNullable(lottery);
    }

    public static Optional<LotteryRunner> getCurrentRunner() {
        return Optional.ofNullable(FaustCycle.getLottery());
    }

}
