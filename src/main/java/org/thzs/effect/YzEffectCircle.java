package org.thzs.effect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class YzEffectCircle extends YzEffect{
    public Location pos1;
    public double during;
    public double r;
    public YzEffectCircle(Location pos1,double r,double during){
        super();
        this.pos1=pos1;
        this.r=r;
        this.during=during;
    }
    public long dela=0;
    @Override
    public void act(long delta) {
        super.act(delta);
        dela+=delta;
        if(dela>1000/20){
            dela=0;
            Location pos3=EffectUtils.cirPosition(pos1,r,aliveTime/during);
            for (Player p: Bukkit.getOnlinePlayers()){
                p.spawnParticle(particle,pos3,5);
            }
        }
    }
    @Override
    public boolean isFinish() {
        return aliveTime>=during;
    }
}
