package org.thzs.effect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.thzs.effect.core.YzEffectEventInfoHandler;
import org.thzs.effect.core.YzEffectFactory;
import org.thzs.effect.core.YzEffectUtils;
import org.thzs.effect.core.YzEffect;

import java.util.Objects;

public class YzEffectCircle extends YzEffect {
    public Location pos1;
    public Entity target;
    public double during;
    public double r;
    public int nums;
    public boolean tracing=false;
    public YzEffectCircle() {
        super();
    }

    public YzEffect loadForYaml(MemorySection config, Event event) {
        if (configKeyCheck(config, "Particle","During", "Num", "Center", "R")) {
            particle =particle(config);
            during = config.getDouble("During");
            nums = config.getInt("Num");
            r = config.getDouble("R");
            if (config.isString("Center")) {
                Object ob= YzEffectEventInfoHandler.getInfo(event,config.getString("Center"));
                if(ob==null){
                    throw new RuntimeException("[Yz]Particle:Circle->Center Value Not Found");
                }else if(ob instanceof Location) {
                    pos1 = (Location) ob;
                }else  if(ob instanceof Entity){
                    target = (Entity) ob;
                    tracing=true;
                }
            }else if(config.isList("Center")){
                //TODO: 标准地址
            }
        }
        return this;
    }

    @Override
    public boolean isSupportEvent(Class<? extends Event> clazz) {
        if (clazz == (EntityDamageByEntityEvent.class)) {
            return true;
        }
        return false;
    }

    public YzEffectCircle(Location pos1, double r, double during, int nums) {
        super();
        this.pos1 = pos1;
        this.r = r;
        this.during = during;
        this.nums = nums;
    }

    public long dela = 0;

    @Override
    public void act(long delta) {
        super.act(delta);
        dela += delta;
        if (dela >= YzEffect.Tick) {
            dela = 0;
            if(tracing) {
                pos1=target.getLocation();
            }
            Location pos3 = YzEffectUtils.cirPosition(pos1, r, aliveTime / during);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spawnParticle(particle, pos3, (int) (nums / (during / YzEffect.Tick)));
            }
        }
    }

    @Override
    public boolean isFinish() {
        return aliveTime >= during;
    }
}
