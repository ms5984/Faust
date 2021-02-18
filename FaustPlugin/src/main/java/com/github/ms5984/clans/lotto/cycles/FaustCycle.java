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
package com.github.ms5984.clans.lotto.cycles;

import com.github.ms5984.clans.lotto.listeners.LotteryCommand;
import com.github.ms5984.clans.lotto.listeners.LotteryRunner;
import com.github.sanctum.labyrinth.library.HUID;
import com.youtube.hempfest.link.EventCycle;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;

/**
 * Describes addon details and maintains state.
 */
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

    /**
     * Get an instance of the current lottery runner.
     * @return runner, null if instance == null or runner == null
     */
    @Nullable
    public static LotteryRunner getRunner() {
        if (instance == null) return null;
        return instance.lotteryRunner;
    }
}
