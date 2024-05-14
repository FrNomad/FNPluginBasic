package com.frnomad.pluginbasic.commands;

import com.frnomad.pluginbasic.commands.exceptions.ExceptionReason;
import com.frnomad.pluginbasic.commands.exceptions.NoPermissionException;
import com.frnomad.pluginbasic.commands.exceptions.WrongFormatException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class Command {

    private CommandTasker tasker;

    //private Field tasker;

    private String prefix;

    private String usage;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String str) {
        this.prefix = str;
    }

    public void setUsage(String str) {
        this.usage = str;
    }

    public String getUsage() {
        return this.usage;
    }

    public abstract boolean runCommand(CommandSender s, String[] args) throws NoPermissionException, WrongFormatException;

    protected boolean debug(String activeArg, Permissions permission, ArgType type, CommandSender s, String[] args) throws NoPermissionException, WrongFormatException {
        if(args.length > 0 && args[tasker.getArgPointer(1)].equalsIgnoreCase(activeArg)) {
            return debugBasic(permission, type, s, args);
        }
        return false;
    }

    protected boolean debug(List<String> activeList, Permissions permission, ArgType type, CommandSender s, String[] args) throws NoPermissionException, WrongFormatException {
        if(args.length > 0 && activeList.contains(args[tasker.getArgPointer(1)])) {
            return debugBasic(permission, type, s, args);
        }
        return false;
    }

    protected boolean debug(Permissions permission, ArgType type, CommandSender s, String[] args) throws NoPermissionException, WrongFormatException {
        if(args.length > 0) {
            return debugBasic(permission, type, s, args);
        }
        return false;
    }

    protected String getCurrentArg(String[] args) {
        return getCurrentArg(args, 0);
    }

    protected String getCurrentArg(String[] args, int i) {
        return args[tasker.getArgPointer(i)];
    }

    private boolean debugBasic(Permissions permission, ArgType type, CommandSender s, String[] args) throws NoPermissionException, WrongFormatException {
        tasker.pointerBump();
        if(s instanceof Player) {
            Player p = (Player) s;
            if(permission == Permissions.CONSOLE_ONLY) {
                throw new NoPermissionException(ExceptionReason.CONSOLE);
            }
            else if(permission == Permissions.OP_PLAYER_ONLY || permission == Permissions.OPERATOR_ONLY) {
                if(!p.isOp()) {
                    throw new NoPermissionException(ExceptionReason.OPERATOR);
                }
                else {
                    if(type.isValidForm(type, getCurrentArg(args))) {
                        return true;
                    }
                    else {
                        throw new WrongFormatException(type);
                    }
                }
            }
            else {
                if(type.isValidForm(type, getCurrentArg(args))) {
                    return true;
                }
                else {
                    throw new WrongFormatException(type);
                }
            }
        }
        else {
            if(permission == Permissions.OP_PLAYER_ONLY || permission == Permissions.PLAYER_ONLY) {
                throw new NoPermissionException(ExceptionReason.PLAYER);
            }
            else {
                if(type.isValidForm(type, getCurrentArg(args))) {
                    return true;
                }
                else {
                    throw new WrongFormatException(type);
                }
            }
        }
    }

    public void setTasker(CommandTasker tasker) {
        this.tasker = tasker;
    }

}
