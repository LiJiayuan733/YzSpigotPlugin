package org.thzs;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
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
            case "item":
                return CommandLevelTwo_item(sender,command,s,strings);
            case "help":
                sender.sendMessage("gui -- 修改物品信息");
                sender.sendMessage("particle -- 粒子相关");
                sender.sendMessage("recipe -- 合成配方相关");
                sender.sendMessage("item -- 物品修改相关");
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
            case "p":
                if(strings.length>=3){
                    sender.setItemInHand(new ItemUtils(sender.getItemInHand()).startWithLore("[YzP]","[YzP]"+strings[2]).use());
                }
                break;
            case "say":
                if(strings.length>=3){
                    sender.setItemInHand(new ItemUtils(sender.getItemInHand()).startWithLore("[YzS]","[YzS]"+strings[2].replace("&","§")).use());
                }
                break;
            case "help":
                sender.sendMessage("reload -- 重新加载合成配方");
                sender.sendMessage("p [数字] -- 设置物品获得概率");
                sender.sendMessage("say [文字]");
                break;
            default:
                sender.sendMessage("you can use /yz recipe help get more details.");
                break;
        }
        return true;
    }
    public boolean CommandLevelTwo_item(Player sender, Command command, String s, String[] strings){
        switch (strings[1]){
            case "name":
                if(strings.length>=3){
                    sender.setItemInHand(new ItemUtils(sender.getItemInHand()).name(strings[2].replace("&","§")).use());
                }
                break;
            case "enc":
                if(strings.length>=4){
                    Enchantment enchantment=Enchantment.getByName(strings[2]);
                    int level=Integer.parseInt(strings[3]);
                    if(enchantment==null){
                        sender.sendMessage("没有找到附魔");
                        break;
                    }
                    if(level<0||level>0xFFFF){
                        sender.sendMessage("等级过大，或过小");
                        break;
                    }
                    sender.setItemInHand(new ItemUtils(sender.getItemInHand()).enc(enchantment,level).use());
                }else if(strings.length==3){
                    sender.sendMessage("you can use /yz item help get more details.");
                }
                break;
            case "attr":
                if(strings.length==4||strings[3].equals("clear")){
                    Attribute attr=Attribute.valueOf(strings[2]);
                    if (attr==null){
                        sender.sendMessage("没有找到属性");
                        break;
                    }
                    if(strings[3].equals("clear")){
                        sender.setItemInHand(new ItemUtils(sender.getItemInHand()).attrClear(attr).use());
                        break;
                    }
                }else if(strings.length>=5){
                    Attribute attr=Attribute.valueOf(strings[2]);
                    if (attr==null){
                        sender.sendMessage("没有找到属性");
                        break;
                    }
                    if(strings[3].equals("clear")){
                        sender.setItemInHand(new ItemUtils(sender.getItemInHand()).attrClear(attr).use());
                        break;
                    }
                    double level=Double.parseDouble(strings[4]);
                    if(level<0||level>0xFFFF){
                        sender.sendMessage("等级过大，或过小");
                        break;
                    }
                    AttributeModifier.Operation operation=AttributeModifier.Operation.valueOf(strings[3]);
                    if (operation==null){
                        sender.sendMessage("没有找到属性操作");
                        break;
                    }
                    AttributeModifier ad=new AttributeModifier(strings[2], level,operation);
                    if (ad==null){
                        sender.sendMessage("属性操作失败");
                        break;
                    }
                    sender.setItemInHand(new ItemUtils(sender.getItemInHand()).attr(attr,ad).use());
                }else{
                    sender.sendMessage("you can use /yz item help get more details.");
                }
                break;
            case "count":
                if(strings.length>=3){
                    int count=Integer.parseInt(strings[2]);
                    sender.getItemInHand().setAmount(count);
                }
                break;
            case "help":
                sender.sendMessage("name [名字] -- 修改名字");
                sender.sendMessage("enc [附魔] [等级] -- 修改附魔");
                sender.sendMessage("attr [属性] [属性操作] [等级] -- 修改属性");
                sender.sendMessage("count [数量] -- 修改数量");
                break;
            default:
                sender.sendMessage("you can use /yz item help get more details.");
                break;
        }
        return true;
    }
}
