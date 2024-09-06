package org.thzs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.thzs.effect.core.YzEffectFactory;
import org.thzs.recipe.YzRecipe;
import org.thzs.uitils.ItemUtils;
import org.thzs.uitils.PlayerUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class YzCommandHandler implements CommandExecutor {
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
            case "recipe":
                return CommandLevelTwo_recipe(sender,command,s,strings);
            case "help":
                sender.sendMessage("gui -- 修改物品信息");
                sender.sendMessage("particle -- 粒子相关");
                sender.sendMessage("recipe -- 合成配方相关");
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
            case "help":
                sender.sendMessage("send [text] -- 添加Lore到手上物品");
            default:
                sender.sendMessage("you can use /yz gui help get more details.");
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
            case "reload":
                YzEffectFactory.configTemp=new HashMap<>();
                sender.sendMessage("加载成功");
                break;
            case "help":
                sender.sendMessage("create [粒子文件名称] -- 创建粒子文件");
                sender.sendMessage("reload -- 重新加载粒子文件");
                break;
        }
        return true;
    }
    public boolean CommandLevelTwo_recipe(Player sender, Command command, String s, String[] strings){
        switch (strings[1]){
            case "reload":
                YzPlugin.instance.recipe.reload();
                YzPlugin.instance.getLogger().info("成功加载"+YzPlugin.instance.recipe.Recipe.size()+"个配方。");
                break;
            case "help":
                sender.sendMessage("reload -- 重新加载合成配方");
            default:
                sender.sendMessage("you can use /yz recipe help get more details.");
                break;
        }
        return true;
    }
}
