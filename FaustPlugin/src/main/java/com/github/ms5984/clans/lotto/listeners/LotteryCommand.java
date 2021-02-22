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

import com.youtube.hempfest.clans.util.events.command.CommandInsertEvent;
import com.youtube.hempfest.clans.util.events.command.TabInsertEvent;
import lombok.val;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Process player commands. TODO: investigate server commands
 */
public class LotteryCommand implements Listener {
    private final Plugin plugin = JavaPlugin.getProvidingPlugin(LotteryCommand.class);

    /**
     * Add suggests to Clans tab complete.
     *
     * @param e TabInsertEvent
     */
    @EventHandler
    public void onClanTab(TabInsertEvent e) {
        val commandArgs = e.getCommandArgs();
        switch (commandArgs.length) {
            case 1:
                val args0 = e.getArgs(0);
                if (!args0.contains("lot")) {
                    args0.add("lot");
                }
                if (!args0.contains("lottery")) {
                    args0.add("lottery");
                }
                break;
            case 2:
                switch (commandArgs[0].toLowerCase()) {
                    case "lot":
                    case "lottery":
                        break;
                    default:
                }
                break;
            default:
        }
    }

    /**
     * Process player commands.
     *
     * @param e CommandInsertEvent
     */
    @EventHandler
    public void onClanCommand(CommandInsertEvent e) {
        val args = e.getArgs();
        if (args.length >= 1) {
            switch (args[0].toLowerCase()) {
                case "lottery":
                case "lot":
                    break;
                default:
            }
        }
    }
}
