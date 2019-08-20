package com.evangunawan.utils;

import com.evangunawan.utils.Commands.RandomTeleport;
import com.evangunawan.utils.Webutils.PlayerCounterSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();

        new PlayerCounterSender(config);
        this.getCommand("tprandom").setExecutor(new RandomTeleport());

        getLogger().info("Utils Plugin Enabled.");
    }
}
