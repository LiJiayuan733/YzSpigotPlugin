package org.thzs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class YzTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        String cmd=command.getName();
        if(cmd.equals("yz")){
            if (strings.length == 1){
                return level2(strings[0]);
            }else if(strings.length == 2){
                return level3(strings[1]);
            }
        }
        return null;
    }
    public List<String> level2(String l){
        String[] list={"gui","particle","help"};
        List<String> list1=new ArrayList<>();
        for (String s:list) {
            if(s.startsWith(l)){
                list1.add(s);
            }
        }
        return list1;
    }
    public List<String> level3(String l){
        String[] list={"send","test","create"};
        List<String> list1=new ArrayList<>();
        for (String s:list) {
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
