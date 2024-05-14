package com.frnomad.pluginbasic.listener;

import com.frnomad.pluginbasic.PluginBasic;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class ListenerTasker {

    private final PluginBasic plugin;

    public ListenerTasker(PluginBasic plugin) {
        this.plugin = plugin;
    }

    public boolean register(Listener... listeners) {
        for(Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
        return true;
    }
}
