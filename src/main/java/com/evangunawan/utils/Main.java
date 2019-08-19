package com.evangunawan.utils;

import com.evangunawan.utils.Commands.RandomTeleport;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Utils Plugin Enabled.");
        this.getCommand("tprandom").setExecutor(new RandomTeleport());
    }
}
