package com.github.ms5984.clans.lotto.api.events;

import com.github.ms5984.clans.lotto.api.model.LottoResult;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a lottery is requested to end.
 */
public final class LotteryEndEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private LottoResult lottoResult;
    private boolean cancelled;

    /**
     * Set the results of the draw.
     * @param lottoResult new results
     */
    public void setLottoResult(@NotNull LottoResult lottoResult) {
        this.lottoResult = lottoResult;
    }

    /**
     * Get the results of the draw.
     * @return lottery results
     */
    public LottoResult getLottoResult() {
        return lottoResult;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
