package com.github.ms5984.clans.lotto.cycles;

import com.github.ms5984.clans.lotto.listeners.LotteryCommand;
import com.github.ms5984.clans.lotto.listeners.LotteryRunner;
import com.github.sanctum.labyrinth.library.HUID;
import com.youtube.hempfest.link.EventCycle;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashSet;

public final class FaustCycle extends EventCycle {

    private static FaustCycle instance;
    private final Plugin plugin = JavaPlugin.getProvidingPlugin(FaustCycle.class);
    private final Collection<Listener> listeners = new HashSet<>();
    private final HUID addonId = super.getAddonId();
    private final LotteryRunner lotteryRunner;

    public FaustCycle() {
        instance = this;
        this.lotteryRunner = new LotteryRunner();
    }

    @Override
    public boolean persist() {
        return plugin.getConfig().getBoolean("enabled");
    }

    @Override
    public HUID getAddonId() {
        return addonId;
    }

    @Override
    public EventCycle getInstance() {
        return instance;
    }

    @Override
    public String getAddonName() {
        return "FaustLotto";
    }

    @Override
    public String getAddonDescription() {
        return "A different type of lottery...";
    }

    @Override
    public Collection<Listener> getAdditions() {
        return listeners;
    }

    @Override
    public void setAdditions() {
        listeners.add(new LotteryCommand());
        listeners.add(lotteryRunner);
    }

    @Override
    public void remove() {
        super.remove();
        instance = null;
    }

    public static LotteryRunner getLottery() {
        if (instance == null) return null;
        return instance.lotteryRunner;
    }
}
