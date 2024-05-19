package com.frnomad.pluginbasic.commands;

import com.frnomad.pluginbasic.PluginBasic;
import com.frnomad.pluginbasic.commands.exceptions.NoPermissionException;
import com.frnomad.pluginbasic.commands.exceptions.WrongFormatException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

import static com.frnomad.pluginbasic.utils.InCodeUtil.replaceArguments;

public class CommandTasker implements CommandExecutor {

    private int argPointer;

    private final PluginBasic plugin;

    private List<Command> commandList = new ArrayList<>();

    public CommandTasker(PluginBasic plugin) {
        this.plugin = plugin;
        this.argPointer = -1;
    }

    public <T extends Command> boolean register(TabCompleter tab, T... commands) {
        for(Command command : commands) {
            PluginCommand plugin_cmd = plugin.getCommand(command.getPrefix());
            if(plugin_cmd != null) {
                plugin_cmd.setExecutor(this);
                if(tab != null) plugin_cmd.setTabCompleter(tab);
                command.setTasker(this);
                commandList.add(command);
            }
        }
        return true;
    }


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        for(Command cmd : commandList) {
            this.argPointer = -1;
            try {
                if(label.equalsIgnoreCase(cmd.getPrefix())) {
                    boolean result = cmd.runCommand(sender, args);
                    if(!result) {
                        if(args.length > 0) {
                            plugin.sendMessage(sender, replaceArguments(plugin.getConfig().getString("ErrorMessage.UnknownCommandError"), cmd.getUsage().split(" ")[argPointer + 1], args[argPointer]));
                        }
                        else {
                            plugin.sendMessage(sender, replaceArguments(plugin.getConfig().getString("ErrorMessage.EmptyArgumentError"), cmd.getUsage().split(" ")[argPointer + 1]));
                        }
                    }
                    return result;
                }
            } catch(NoPermissionException e) {
                plugin.sendMessage(sender, replaceArguments(plugin.getConfig().getString("ErrorMessage.PermissionError"), e.getReason().toString()));
                return false;
            } catch(WrongFormatException e) {
                plugin.sendMessage(sender, replaceArguments(plugin.getConfig().getString("ErrorMessage.WrongTypeError"), cmd.getUsage().split(" ")[argPointer + 1], e.getReason().toString()));
                return false;
            }
        }
        return false;
    }

    public int getArgPointer() {
        return this.argPointer;
    }

    public int getArgPointer(int dist) {
        return this.argPointer + dist;
    }

    public void pointerBump() {
        this.argPointer += 1;
    }
}
