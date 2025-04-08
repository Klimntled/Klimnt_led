package com.example.paperz;

import org.bukkit.plugin.java.JavaPlugin;

public class PaperZ extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("PaperZ is enabled!");
        getServer().getPluginManager().registerEvents(new NoteListener(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("PaperZ is disabled!");
    }
}