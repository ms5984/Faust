package com.github.ms5984.clans.lotto.listeners;

import com.youtube.hempfest.clans.util.events.command.CommandInsertEvent;
import com.youtube.hempfest.clans.util.events.command.TabInsertEvent;
import lombok.val;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class LotteryCommand implements Listener {
    private final Plugin plugin = JavaPlugin.getProvidingPlugin(LotteryCommand.class);
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
