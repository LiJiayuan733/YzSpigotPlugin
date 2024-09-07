package org.thzs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class YzTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        String cmd=command.getName();
        if(cmd.equals("yz")){
            if (strings.length == 1){
                return level2(strings);
            }else if(strings.length == 2){
                return level3(strings);
            }else if(strings.length ==  3){
                return level4(strings);
            }else if(strings.length == 4){
                return level4(strings);
            }
        }
        return null;
    }
    public List<String> level2(String[] strings){
        String[] list={"gui","recipe","particle","item","help"};
        List<String> list1=new ArrayList<>();
        for (String s:list) {
            if(s.startsWith(strings[0])){
                list1.add(s);
            }
        }
        return list1;
    }
    public List<String> level3(String[] strings){
        String[] list;
        switch (strings[0]){
            case "gui":
                list=new String[]{"send"};
                break;
            case "particle":
                list=new String[]{"create","reload","help"};
                break;
            case "recipe":
                list=new String[]{"reload","p","say","help"};
                break;
            case "item":
                list=new String[]{"name","enc","attr","help"};
                break;
            default:
                list=new String[]{};
                break;
        }
        List<String> list1=new ArrayList<>();
        for (String s:list) {
            if(s.startsWith(strings[1])){
                list1.add(s);
            }
        }
        return list1;
    }
    public List<String> level4(String[] strings){
        switch (strings[0]){
            case "item":
                switch (strings[1]){
                    case "enc":
                        if (strings.length==3){
                            return enchant(strings[2]);
                        }else{
                            return null;
                        }

                    case "attr":
                        if(strings.length==3){
                            return attribute(strings[2]);
                        }else if(strings.length==4){
                            return attribute_opr(strings[3]);
                        }
                    default:
                        return null;
                }
            default:
                return null;
        }
    }
    public static final String[] ENCHANTMENT_NAMES = {
            "protection", // 保护
            "fire_protection", // 火焰保护
            "feather_falling", // 摔落保护
            "blast_protection", // 爆炸保护
            "projectile_protection", // 弹射物保护
            "respiration", // 水下呼吸
            "aqua_affinity", // 水下速掘
            "thorns", // 荆棘
            "depth_strider", // 深海探索者
            "frost_walker", // 冰霜行者
            "binding_curse", // 绑定诅咒
            "sharpness", // 锋利
            "smite", // 亡灵杀手
            "bane_of_arthropods", // 节肢杀手
            "knockback", // 击退
            "fire_aspect", // 火焰附加
            "looting", // 抢夺
            "sweeping", // 横扫之刃
            "efficiency", // 效率
            "silk_touch", // 精准采集
            "unbreaking", // 耐久
            "fortune", // 时运
            "power", // 力量
            "punch", // 冲击
            "flame", // 火矢
            "infinity", // 无限
            "luck_of_the_sea", // 海之眷顾
            "lure", // 饵钓
            "loyalty", // 忠诚
            "impaling", // 穿刺
            "riptide", // 激流
            "channeling", // 引雷
            "multishot", // 多重射击
            "quick_charge", // 快速装填
            "piercing", // 穿透
            "mending", // 经验修补
            "vanishing_curse", // 消失诅咒
            "soul_speed", // 灵魂疾行
            "swift_sneak" // 迅捷潜行
    };
    public List<String> enchant(String l){
        List<String> list1=new ArrayList<>();
        for (String s:ENCHANTMENT_NAMES) {
            if(s.startsWith(l)){
                list1.add(s);
            }
        }
        return list1;
    }
    public static final String[] ATTRIBUTE_NAMES = {
            "generic.max_health", // 最大生命值
            "generic.follow_range", // 跟随范围
            "generic.knockback_resistance", // 击退抗性
            "generic.movement_speed", // 移动速度
            "generic.flying_speed", // 飞行速度
            "generic.attack_damage", // 攻击伤害
            "generic.attack_knockback", // 攻击击退
            "generic.attack_speed", // 攻击速度
            "generic.armor", // 护甲
            "generic.armor_toughness", // 护甲韧性
            "generic.luck", // 幸运
            "horse.jump_strength", // 马跳跃力量
            "zombie.spawn_reinforcements" // 僵尸生成援军
    };
    public static final String[] ATTRIBUTE_OPR={
            "clear",
            "ADD_NUMBER",   //直接添加
            "ADD_SCALAR",   //在基础值上加
            "MULTIPLY_SCALAR_1" //乘法
    };
    public List<String> attribute_opr(String l){
        List<String> list1=new ArrayList<>();
        for (String s:ATTRIBUTE_OPR) {
            if(s.startsWith(l)){
                list1.add(s);
            }
        }
        return list1;
    }
    public List<String> attribute(String l){
        List<String> list1=new ArrayList<>();
        for (String s:ATTRIBUTE_NAMES) {
            if(s.startsWith(l)){
                list1.add(s);
            }
        }
        return list1;
    }
    public List<String> particle(String l){
        String[] particles = {
                "ASH",
                "BLOCK_CRACK",
                "BLOCK_DUST",
                "BLOCK_MARKER",
                "BUBBLE_COLUMN_UP",
                "BUBBLE_POP",
                "CAMPFIRE_COSY_SMOKE",
                "CAMPFIRE_SIGNAL_SMOKE",
                "CLOUD",
                "COMPOSTER",
                "CRIMSON_SPORE",
                "CRIT",
                "CRIT_MAGIC",
                "CURRENT_DOWN",
                "DAMAGE_INDICATOR",
                "DOLPHIN",
                "DRAGON_BREATH",
                "DRIP_LAVA",
                "DRIP_WATER",
                "DRIPPING_DRIPSTONE_LAVA",
                "DRIPPING_DRIPSTONE_WATER",
                "DRIPPING_HONEY",
                "DRIPPING_OBSIDIAN_TEAR",
                "DUST_COLOR_TRANSITION",
                "ELECTRIC_SPARK",
                "ENCHANTMENT_TABLE",
                "END_ROD",
                "EXPLOSION_HUGE",
                "EXPLOSION_LARGE",
                "EXPLOSION_NORMAL",
                "FALLING_DRIPSTONE_LAVA",
                "FALLING_DRIPSTONE_WATER",
                "FALLING_DUST",
                "FALLING_HONEY",
                "FALLING_LAVA",
                "FALLING_NECTAR",
                "FALLING_OBSIDIAN_TEAR",
                "FALLING_SPORE_BLOSSOM",
                "FALLING_WATER",
                "FIREWORKS_SPARK",
                "FLAME",
                "FLASH",
                "GLOW",
                "GLOW_SQUID_INK",
                "HEART",
                "ITEM_CRACK",
                "LANDING_HONEY",
                "LANDING_LAVA",
                "LANDING_OBSIDIAN_TEAR",
                "LAVA",
                "LEGACY_BLOCK_CRACK",
                "LEGACY_BLOCK_DUST",
                "LEGACY_FALLING_DUST",
                "MOB_APPEARANCE",
                "NAUTILUS",
                "NOTE",
                "PORTAL",
                "REDSTONE",
                "REVERSE_PORTAL",
                "SCRAPE",
                "SCULK_CHARGE",
                "SCULK_CHARGE_POP",
                "SCULK_SOUL",
                "SHRIEK",
                "SLIME",
                "SMALL_FLAME",
                "SMOKE_LARGE",
                "SMOKE_NORMAL",
                "SNEEZE",
                "SNOW_SHOVEL",
                "SNOWBALL",
                "SNOWFLAKE",
                "SONIC_BOOM",
                "SOUL",
                "SOUL_FIRE_FLAME",
                "SPELL",
                "SPELL_INSTANT",
                "SPELL_MOB",
                "SPELL_MOB_AMBIENT",
                "SPELL_WITCH",
                "SPIT",
                "SPORE_BLOSSOM_AIR",
                "SQUID_INK",
                "SUSPENDED",
                "SUSPENDED_DEPTH",
                "SWEEP_ATTACK",
                "TOTEM",
                "TOWN_AURA",
                "VIBRATION",
                "VILLAGER_ANGRY",
                "VILLAGER_HAPPY",
                "WARPED_SPORE",
                "WATER_BUBBLE",
                "WATER_DROP",
                "WATER_SPLASH",
                "WATER_WAKE",
                "WAX_OFF",
                "WAX_ON",
                "WHITE_ASH"
        };
        List<String> list1=new ArrayList<>();
        for (String s:particles) {
            if(s.startsWith(l)){
                list1.add(s);
            }
        }
        return list1;
    }
}
