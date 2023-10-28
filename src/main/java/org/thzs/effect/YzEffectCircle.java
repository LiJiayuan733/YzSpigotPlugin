package org.thzs.effect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.thzs.effect.core.EffectUtils;
import org.thzs.effect.core.YzEffect;

public class YzEffectCircle extends YzEffect {
    public Location pos1;
    public double during;
    public double r;
    public int nums;
    public YzEffectCircle(){
        super();
    }
    public void loadForYaml(YamlConfiguration config,Event event){

    }

    @Override
    public boolean isSupportEvent(Class<? extends Event> clazz) {
        if (clazz==(EntityDamageByEntityEvent.class)) {
            return true;
        }
        return false;
    }

    public YzEffectCircle(Location pos1,double r,double during,int nums){
        super();
        this.pos1=pos1;
        this.r=r;
        this.during=during;
        this.nums=nums;
    }
    public long dela=0;
    @Override
    public void act(long delta) {
        super.act(delta);
        dela+=delta;
        if(dela>=YzEffect.Tick){
            dela=0;
            Location pos3= EffectUtils.cirPosition(pos1,r,aliveTime/during);
            for (Player p: Bukkit.getOnlinePlayers()){
                p.spawnParticle(particle,pos3,(int) (nums/(during/YzEffect.Tick)));
            }
        }
    }
    @Override
    public boolean isFinish() {
        return aliveTime>=during;
    }
}
