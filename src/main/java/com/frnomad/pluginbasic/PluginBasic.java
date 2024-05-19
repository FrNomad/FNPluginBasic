package com.frnomad.pluginbasic;

import com.frnomad.pluginbasic.commands.CommandTasker;
import com.frnomad.pluginbasic.listener.ListenerTasker;
import com.frnomad.pluginbasic.store.StoreTasker;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.frnomad.pluginbasic.utils.InCodeUtil.format;

public abstract class PluginBasic extends JavaPlugin {

    public final Logger logger = this.getLogger();
    protected PluginDescriptionFile pdFile = this.getDescription();
    private final List<String> dependencies = new ArrayList<>();

    protected ListenerTasker listener = new ListenerTasker(this);
    protected CommandTasker command = new CommandTasker(this);
    protected StoreTasker store = new StoreTasker(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        setup();
        for(String dep : dependencies) {
            if(!Bukkit.getPluginManager().isPluginEnabled(dep)) {
                log(Level.SEVERE, "This plugin depends on some of other plugins. Please download plugins named " + dep + ".");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
        }
        if(enableTask()) {
            log(Level.INFO, pdFile.getName() + " version " + pdFile.getVersion() + " successfully enabled.");
        }
        else {
            log(Level.WARNING, "There's something wrong while enabling " + pdFile.getName() + " version " + pdFile.getVersion() + ".");
        }
        store.loadConfigs();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        store.saveConfigs();
        if(disableTask()) {
            log(Level.INFO, pdFile.getName() + " version " + pdFile.getVersion() + " successfully disabled.");
        }
        else {
            log(Level.WARNING, "There's something wrong while disabling " + pdFile.getName() + " version " + pdFile.getVersion() + ".");
        }
    }


    public abstract boolean enableTask();

    public abstract boolean disableTask();

    public abstract void setup();

    public void log(Level lv, String str) {
        logger.log(lv, str);
    }

    protected void setDependencies(String... dependencies) {
        this.dependencies.addAll(Arrays.asList(dependencies));
    }

    public static void sendMessage(CommandSender sender, String str) {
        sender.sendMessage(format(str));
    }

    public static void noticeMessage(World world, String str) {
        for(Player p : world.getPlayers()) {
            sendMessage(p, str);
        }
    }

    public static void noticeMessage(String str) {
        for(World w : Bukkit.getWorlds()) {
            noticeMessage(w, str);
        }
    }
}
