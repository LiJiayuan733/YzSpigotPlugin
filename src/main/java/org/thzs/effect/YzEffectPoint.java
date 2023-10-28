package org.thzs.effect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.thzs.effect.core.YzEffect;

public class YzEffectPoint extends YzEffect {
    public boolean Tracing=false;
    public Location pos1,offset;
    public Entity epos1;
    public double during;
    public int nums;
    public YzEffectPoint(){
        super();
    }
    public YzEffectPoint(Location pos1, double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.pos1=pos1;
        this.Tracing=false;
    }
    public YzEffectPoint(Location pos1,Location offset,double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.pos1=pos1;
        this.offset=offset;
        this.Tracing=false;
    }
    public YzEffectPoint(Entity pos1, double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.epos1=pos1;
        this.Tracing=true;
    }
    public YzEffectPoint(Entity pos1,Location offset,double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.epos1=pos1;
        this.offset=offset;
        this.Tracing=true;
    }
    public int dela=0;
    @Override
    public void act(long delta) {
        super.act(delta);
        dela+=delta;
        if(dela>=YzEffect.Tick){
            dela=0;
            if(Tracing){
                pos1=epos1.getLocation().clone().add(offset);
                for (Player p: Bukkit.getOnlinePlayers()){
                    p.spawnParticle(particle,pos1, (int) (nums/(during/(1000/10))));
                }
            }else{
                for (Player p: Bukkit.getOnlinePlayers()){
                    p.spawnParticle(particle,pos1.clone().add(offset), (int) (nums/(during/YzEffect.Tick)));
                }
            }
        }
    }
    @Override
    public boolean isFinish() {
        return aliveTime>=during;
    }

    @Override
    public void loadForYaml(YamlConfiguration config, Event event) {

    }
    @Override
    public boolean isSupportEvent(Class<? extends Event> clazz) {
        if (clazz==(EntityDamageByEntityEvent.class)) {
            return true;
        }
        return false;
    }
}
