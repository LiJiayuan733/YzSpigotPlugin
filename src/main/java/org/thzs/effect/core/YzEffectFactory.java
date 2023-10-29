package org.thzs.effect.core;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.thzs.YzPlugin;
import org.thzs.effect.YzEffectCircle;
import org.thzs.effect.YzEffectLine;
import org.thzs.effect.YzEffectPoint;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class YzEffectFactory {
    public static HashMap<String,YamlConfiguration> configTemp=new HashMap<>();
    public static YamlConfiguration getConfig(String name){
        File[] files= YzPlugin.instance.getDataFolder().listFiles();
        File select=null;
        for (File file:files){
            if(file.getName().equals(name+".yml")){
                select=file;
            }
        }
        if(select==null){
            return null;
        }else{
            YamlConfiguration config;
            if(configTemp.containsKey(name)){
                config=configTemp.get(name);
            }else {
                config=YamlConfiguration.loadConfiguration(select);
            }
            return config;
        }
    }
    public static boolean EventCheck(String name,Class<? extends Event> clazz){
        YamlConfiguration config=getConfig(name);
        if(config==null||!config.contains("Event")){
            return false;
        }
        String event=config.getString("Event");
        Class<? extends Event> eventClass=convertEvent(event);
        return eventClass == clazz;
    }
    public static YzEffectFactoryBox create(String name){
        YamlConfiguration config=getConfig(name);
        if(config==null){
            return null;
        }
        return createBox(config);
    }
    public static YzEffectFactoryBox createBox(YamlConfiguration config){
        //TODO: 出错就是config提取的错误
        YzEffectFactoryBox box=new YzEffectFactoryBox();
        MemorySection mem= (MemorySection) config.get("Struct");
        assert mem != null;
        for(String s:mem.getKeys(false)){
            Object p;
            p=mem.get(s);
            if (p instanceof MemorySection){
                String type=((MemorySection) p).getString("Type");
                YzEffect effect=getEffect(type);
                if(effect!=null){
                    box.put(effect, (MemorySection) p);
                }
            }
        }
        return box;
    }
    public static YzEffect getEffect(String s){
        switch (s){
            case "Line":
                return new YzEffectLine();
            case "Circle":
                return new YzEffectCircle();
            case "Point":
                return new YzEffectPoint();
            default:
                return null;
        }
    }
    public static Particle convertParticle(String ParticleName){
        switch (ParticleName){
            case "ASH":
                return Particle.ASH;
            case "BLOCK_CRACK":
                return Particle.BLOCK_CRACK;
            case "BLOCK_DUST":
                return Particle.BLOCK_DUST;
            case "BLOCK_MARKER":
                return Particle.BLOCK_MARKER;
            case "BUBBLE_COLUMN_UP":
                return Particle.BUBBLE_COLUMN_UP;
            case "BUBBLE_POP":
                return Particle.BUBBLE_POP;
            case "CAMPFIRE_COSY_SMOKE":
                return Particle.CAMPFIRE_COSY_SMOKE;
            case "CAMPFIRE_SIGNAL_SMOKE":
                return Particle.CAMPFIRE_SIGNAL_SMOKE;
            case "CLOUD":
                return Particle.CLOUD;
            case "COMPOSTER":
                return Particle.COMPOSTER;
            case "CRIMSON_SPORE":
                return Particle.CRIMSON_SPORE;
            case "CRIT":
                return Particle.CRIT;
            case "CRIT_MAGIC":
                return Particle.CRIT_MAGIC;
            case "CURRENT_DOWN":
                return Particle.CURRENT_DOWN;
            case "DAMAGE_INDICATOR":
                return Particle.DAMAGE_INDICATOR;
            case "DOLPHIN":
                return Particle.DOLPHIN;
            case "DRAGON_BREATH":
                return Particle.DRAGON_BREATH;
            case "DRIP_LAVA":
                return Particle.DRIP_LAVA;
            case "DRIP_WATER":
                return Particle.DRIP_WATER;
            case "DRIPPING_DRIPSTONE_LAVA":
                return Particle.DRIPPING_DRIPSTONE_LAVA;
            case "DRIPPING_DRIPSTONE_WATER":
                return Particle.DRIPPING_DRIPSTONE_WATER;
            case "DRIPPING_HONEY":
                return Particle.DRIPPING_HONEY;
            case "DRIPPING_OBSIDIAN_TEAR":
                return Particle.DRIPPING_OBSIDIAN_TEAR;
            case "DUST_COLOR_TRANSITION":
                return Particle.DUST_COLOR_TRANSITION;
            case "ELECTRIC_SPARK":
                return Particle.ELECTRIC_SPARK;
            case "ENCHANTMENT_TABLE":
                return Particle.ENCHANTMENT_TABLE;
            case "END_ROD":
                return Particle.END_ROD;
            case "EXPLOSION_HUGE":
                return Particle.EXPLOSION_HUGE;
            case "EXPLOSION_LARGE":
                return Particle.EXPLOSION_LARGE;
            case "EXPLOSION_NORMAL":
                return Particle.EXPLOSION_NORMAL;
            case "FALLING_DRIPSTONE_LAVA":
                return Particle.FALLING_DRIPSTONE_LAVA;
            case "FALLING_DRIPSTONE_WATER":
                return Particle.FALLING_DRIPSTONE_WATER;
            case "FALLING_DUST":
                return Particle.FALLING_DUST;
            case "FALLING_HONEY":
                return Particle.FALLING_HONEY;
            case "FALLING_LAVA":
                return Particle.FALLING_LAVA;
            case "FALLING_NECTAR":
                return Particle.FALLING_NECTAR;
            case "FALLING_OBSIDIAN_TEAR":
                return Particle.FALLING_OBSIDIAN_TEAR;
            case "FALLING_SPORE_BLOSSOM":
                return Particle.FALLING_SPORE_BLOSSOM;
            case "FALLING_WATER":
                return Particle.FALLING_WATER;
            case "FIREWORKS_SPARK":
                return Particle.FIREWORKS_SPARK;
            case "FLAME":
                return Particle.FLAME;
            case "FLASH":
                return Particle.FLASH;
            case "GLOW":
                return Particle.GLOW;
            case "GLOW_SQUID_INK":
                return Particle.GLOW_SQUID_INK;
            case "HEART":
                return Particle.HEART;
            case "ITEM_CRACK":
                return Particle.ITEM_CRACK;
            case "LANDING_HONEY":
                return Particle.LANDING_HONEY;
            case "LANDING_LAVA":
                return Particle.LANDING_LAVA;
            case "LANDING_OBSIDIAN_TEAR":
                return Particle.LANDING_OBSIDIAN_TEAR;
            case "LAVA":
                return Particle.LAVA;
            case "LEGACY_BLOCK_CRACK":
                return Particle.LEGACY_BLOCK_CRACK;
            case "LEGACY_BLOCK_DUST":
                return Particle.LEGACY_BLOCK_DUST;
            case "LEGACY_FALLING_DUST":
                return Particle.LEGACY_FALLING_DUST;
            case "MOB_APPEARANCE":
                return Particle.MOB_APPEARANCE;
            case "NAUTILUS":
                return Particle.NAUTILUS;
            case "NOTE":
                return Particle.NOTE;
            case "PORTAL":
                return Particle.PORTAL;
            case "REDSTONE":
                return Particle.REDSTONE;
            case "REVERSE_PORTAL":
                return Particle.REVERSE_PORTAL;
            case "SCRAPE":
                return Particle.SCRAPE;
            case "SCULK_CHARGE":
                return Particle.SCULK_CHARGE;
            case "SCULK_CHARGE_POP":
                return Particle.SCULK_CHARGE_POP;
            case "SCULK_SOUL":
                return Particle.SCULK_SOUL;
            case "SHRIEK":
                return Particle.SHRIEK;
            case "SLIME":
                return Particle.SLIME;
            case "SMALL_FLAME":
                return Particle.SMALL_FLAME;
            case "SMOKE_LARGE":
                return Particle.SMOKE_LARGE;
            case "SMOKE_NORMAL":
                return Particle.SMOKE_NORMAL;
            case "SNEEZE":
                return Particle.SNEEZE;
            case "SNOW_SHOVEL":
                return Particle.SNOW_SHOVEL;
            case "SNOWBALL":
                return Particle.SNOWBALL;
            case "SNOWFLAKE":
                return Particle.SNOWFLAKE;
            case "SONIC_BOOM":
                return Particle.SONIC_BOOM;
            case "SOUL":
                return Particle.SOUL;
            case "SOUL_FIRE_FLAME":
                return Particle.SOUL_FIRE_FLAME;
            case "SPELL":
                return Particle.SPELL;
            case "SPELL_INSTANT":
                return Particle.SPELL_INSTANT;
            case "SPELL_MOB":
                return Particle.SPELL_MOB;
            case "SPELL_MOB_AMBIENT":
                return Particle.SPELL_MOB_AMBIENT;
            case "SPELL_WITCH":
                return Particle.SPELL_WITCH;
            case "SPIT":
                return Particle.SPIT;
            case "SPORE_BLOSSOM_AIR":
                return Particle.SPORE_BLOSSOM_AIR;
            case "SQUID_INK":
                return Particle.SQUID_INK;
            case "SUSPENDED":
                return Particle.SUSPENDED;
            case "SUSPENDED_DEPTH":
                return Particle.SUSPENDED_DEPTH;
            case "SWEEP_ATTACK":
                return Particle.SWEEP_ATTACK;
            case "TOTEM":
                return Particle.TOTEM;
            case "TOWN_AURA":
                return Particle.TOWN_AURA;
            case "VIBRATION":
                return Particle.VIBRATION;
            case "VILLAGER_ANGRY":
                return Particle.VILLAGER_ANGRY;
            case "VILLAGER_HAPPY":
                return Particle.VILLAGER_HAPPY;
            case "WARPED_SPORE":
                return Particle.WARPED_SPORE;
            case "WATER_BUBBLE":
                return Particle.WATER_BUBBLE;
            case "WATER_DROP":
                return Particle.WATER_DROP;
            case "WATER_SPLASH":
                return Particle.WATER_SPLASH;
            case "WATER_WAKE":
                return Particle.WATER_WAKE;
            case "WAX_OFF":
                return Particle.WAX_OFF;
            case "WAX_ON":
                return Particle.WAX_ON;
            case "WHITE_ASH":
                return Particle.WHITE_ASH;
            default:
                return null;
        }
    }
    public static Class<? extends Event> convertEvent(String EventName){
        switch (EventName){
            case "EntityDamageByEntityEvent":
                return EntityDamageByEntityEvent.class;
            case "PlayerDropItemEvent":
                return PlayerDropItemEvent.class;
            case "PlayerEvent":
                return PlayerEvent.class;
            case "PlayerExpChangeEvent":
                return PlayerExpChangeEvent.class;
            case "PlayerItemBreakEvent":
                return PlayerItemBreakEvent.class;
            case "PlayerItemHeldEvent":
                return PlayerItemHeldEvent.class;
            case "PlayerJoinEvent":
                return PlayerJoinEvent.class;
            case "PlayerMoveEvent":
                return PlayerMoveEvent.class;
            case "PlayerPickupItemEvent":
                return PlayerPickupItemEvent.class;
            case "PlayerQuitEvent":
                return PlayerQuitEvent.class;
            case "PlayerRespawnEvent":
                return PlayerRespawnEvent.class;
            case "PlayerTeleportEvent":
                return PlayerTeleportEvent.class;
            case "PlayerPickupArrowEvent":
                return PlayerPickupArrowEvent.class;
            case "BlockBreakEvent":
                return BlockBreakEvent.class;
            case "BlockBurnEvent":
                return BlockBurnEvent.class;
            case "BlockCanBuildEvent":
                return BlockCanBuildEvent.class;
            case "BlockDamageEvent":
                return BlockDamageEvent.class;
            case "BlockDispenseEvent":
                return BlockDispenseEvent.class;
            case "BlockExpEvent":
                return BlockExpEvent.class;
            case "BlockFadeEvent":
                return BlockFadeEvent.class;
            case "BlockFertilizeEvent":
                return BlockFertilizeEvent.class;
            case "BlockFormEvent":
                return BlockFormEvent.class;
            case "BlockFromToEvent":
                return BlockFromToEvent.class;
            case "BlockIgniteEvent":
                return BlockIgniteEvent.class;
            case "BlockMultiPlaceEvent":
                return BlockMultiPlaceEvent.class;
            case "BlockPlaceEvent":
                return BlockPlaceEvent.class;
            case "BlockRedstoneEvent":
                return BlockRedstoneEvent.class;
            case "BlockSpreadEvent":
                return BlockSpreadEvent.class;
            default:
                return null;
        }
    }
}
