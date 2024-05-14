package com.frnomad.pluginbasic.store;

import com.frnomad.pluginbasic.PluginBasic;

import java.util.ArrayList;
import java.util.List;

public class StoreTasker<T extends StoreConfig> {

    private final PluginBasic plugin;
    
    private final List<T> configs = new ArrayList<>();

    public StoreTasker(PluginBasic plugin) {
        this.plugin = plugin;
    }

    public boolean register(T... configs) {
        for(T config : configs) {
            config.setTasker(this);
            this.configs.add(config);
        }
        return true;
    }

    public void loadConfigs() {
        plugin.saveDefaultConfig();
        for(T config : configs) {
            config.loadFile();
        }
    }

    public void saveConfigs() {
        plugin.saveDefaultConfig();
        for(T config : configs) {
            config.saveFile();
        }
    }

    public PluginBasic getPlugin() {
        return this.plugin;
    }

}
