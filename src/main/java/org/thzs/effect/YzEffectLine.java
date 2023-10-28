package org.thzs.effect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;


public class YzEffectLine extends YzEffect{
    public Location pos1,pos2;
    public double during;
    public int nums;
    public YzEffectLine(Location pos1,Location pos2,double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.pos2=pos2;
        this.pos1=pos1;
    }
    public int dela=0;
    @Override
    public void act(long delta) {
        super.act(delta);
        dela+=delta;
        if(dela>1000/10){
            dela=0;
            Location pos3=EffectUtils.linePosition(pos1,pos2,aliveTime/during);
            for (Player p:Bukkit.getOnlinePlayers()){
                p.spawnParticle(particle,pos3,5);
            }
        }
    }
    @Override
    public boolean isFinish() {
        return aliveTime>=during;
    }
}
