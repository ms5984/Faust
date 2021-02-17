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
