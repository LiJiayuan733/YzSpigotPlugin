package org.thzs.effect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.thzs.effect.core.EffectUtils;
import org.thzs.effect.core.YzEffect;


public class YzEffectLine extends YzEffect {
    public Location pos1,pos2;
    public boolean tracing=false;
    public double during;
    public int nums;
    public Entity epos1,epos2;
    public YzEffectLine(){
        super();
    }
    public YzEffectLine(Location pos1,Location pos2,double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.pos2=pos2;
        this.pos1=pos1;
        tracing=false;
    }
    public YzEffectLine(Entity pos1, Entity pos2, double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.epos2=pos2;
        this.epos1=pos1;
        tracing=true;
    }
    public int dela=0;
    @Override
    public void act(long delta) {
        super.act(delta);
        dela+=delta;
        if(dela>=YzEffect.Tick){
            dela=0;
            if(tracing){
                pos1=epos1.getLocation();pos2=epos2.getLocation();
                Location pos3= EffectUtils.linePosition(pos1,pos2,aliveTime/during);
                for (Player p: Bukkit.getOnlinePlayers()){
                    p.spawnParticle(particle,pos3,(int) (nums/(during/YzEffect.Tick)));
                }
            }else {
                Location pos3 = EffectUtils.linePosition(pos1, pos2, aliveTime / during);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.spawnParticle(particle, pos3, (int) (nums/(during/YzEffect.Tick)));
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
