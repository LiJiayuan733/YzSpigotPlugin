package org.thzs.effect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.thzs.effect.core.YzEffect;
import org.thzs.effect.core.YzEffectEventInfoHandler;

import java.util.List;
import java.util.Objects;


public class YzEffectPoint extends YzEffect {
    public boolean tracing=false;
    public Location pos1,offset;
    public Entity epos1;
    public double during;
    public int nums;
    public YzEffectPoint(){
        super();
    }
    public YzEffectPoint(Location pos1,Location offset,double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.pos1=pos1;
        this.offset=offset;
        this.tracing=false;
    }
    public YzEffectPoint(Entity pos1,Location offset,double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.epos1=pos1;
        this.offset=offset;
        this.tracing=true;
    }
    public int dela=0;
    @Override
    public void act(long delta) {
        super.act(delta);
        dela+=delta;
        if(dela>=YzEffect.Tick){
            dela=0;
            if(tracing){
                pos1=epos1.getLocation().clone().add(offset);
            }
            for (Player p: Bukkit.getOnlinePlayers()){
                p.spawnParticle(particle,pos1.clone().add(offset), (int) (nums/(during/YzEffect.Tick)));
            }
        }
    }
    @Override
    public boolean isFinish() {
        return aliveTime>=during;
    }

    @Override
    public YzEffect loadForYaml(MemorySection config, Event event) {
        if (configKeyCheck(config, "Particle","During", "Num", "Position", "Offset")) {
            particle = particle(config);
            during = config.getDouble("During");
            nums = config.getInt("Num");
            if (config.isString("Position")) {
                Object ob= YzEffectEventInfoHandler.getInfo(event,config.getString("Position"));
                if(ob==null){
                    throw new RuntimeException("[Yz]Particle:Point->Position Value Not Found");
                } else if(ob instanceof Location) {
                    pos1 = (Location) ob;
                }else  if(ob instanceof Entity){
                    epos1 = (Entity) ob;
                    tracing=true;
                }
            }
            if(config.isList("Offset")){
                offset=location(Objects.requireNonNull(config.getList("Offset")));
                offset.setWorld(epos1.getWorld());
            }
        }
        return this;
    }
    @Override
    public boolean isSupportEvent(Class<? extends Event> clazz) {
        if (clazz==(EntityDamageByEntityEvent.class)) {
            return true;
        }
        return false;
    }
}
