package org.thzs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.thzs.effect.core.YzEffect;
import org.thzs.uitils.ItemUtils;
import org.thzs.uitils.PlayerUtils;

import java.io.File;
import java.io.IOException;

public class MainYzCommandHandler implements CommandExecutor {
    /**
     * @param s 别名
     * @param strings 参数
     * */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p=(Player) commandSender;
            if(!p.hasPermission("yz.editor")){
                commandSender.sendMessage("你无法使用");
                return true;
            }
            return CommandLevelTwo(p,command,s,strings);
        }else{
            commandSender.sendMessage("你必须是一个玩家");  //所要执行的内容
        }
        return false;
    }
    public boolean CommandLevelTwo(Player sender, Command command, String s, String[] strings){
        switch (strings[0]){
            case "gui":
                return CommandLevelTwo_gui(sender,command,s,strings);
            case "particle":
                return CommandLevelTwo_particle(sender,command,s,strings);
            case "help":
                break;
            default:
                sender.sendMessage("you can use /yz help get more details.");
        }
        return false;
    }
    public boolean CommandLevelTwo_gui(Player sender, Command command, String s, String[] strings){
        switch (strings[1]){
            case "send":
                PlayerUtils.sendGift(sender,new ItemUtils(sender.getItemInHand()).lore(strings[2]).use());
                break;
            case "test":
                PlayerUtils.sendGift(sender,
                        new ItemUtils(sender.getItemInHand()).lore("[Yz]PointAttack").use(),
                        new ItemUtils(sender.getItemInHand()).lore("[Yz]Circle").use(),
                        new ItemUtils(sender.getItemInHand()).lore("[Yz]LineAttack").use());
                break;
            default:
                break;
        }
        return true;
    }
    public boolean CommandLevelTwo_particle(Player sender, Command command, String s, String[] strings){
        switch (strings[1]){
            case "create":
                if(strings.length==3){
                    File file=new File(YzPlugin.instance.getDataFolder().getAbsoluteFile()+"/"+strings[2]+".yml");
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
        }
        return true;
    }
}
