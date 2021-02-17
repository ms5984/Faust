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
package com.github.ms5984.clans.lotto.api.model;

import com.youtube.hempfest.clans.construct.Clan;
import org.bukkit.Location;

import java.math.BigDecimal;

/**
 * Represents an ended lottery.
 */
public final class LottoResult {
    private final Clan winner;
    private final Location location;
    private final BigDecimal potValue;

    /**
     * A lottery is ended with winner, location, and value
     * @param winner winning clan
     * @param location fuzzed location
     * @param potValue value of lottery prize
     */
    public LottoResult(Clan winner, Location location, BigDecimal potValue) {
        this.winner = winner;
        this.location = location;
        this.potValue = potValue;
    }

    /**
     * Get the winning clan.
     * @return the winning clan
     */
    public Clan getWinner() {
        return winner;
    }

    /**
     * Get a fuzzed location.
     * @return fuzzed location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Get the value of the pot won.
     * @return prize value
     */
    public BigDecimal getPotValue() {
        return potValue;
    }
}
