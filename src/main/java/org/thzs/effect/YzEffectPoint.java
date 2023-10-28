package org.thzs.effect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class YzEffectPoint extends YzEffect{
    public Location pos1;
    public double during;
    public int nums;
    public YzEffectPoint(Location pos1, double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.pos1=pos1;
    }
    public YzEffectPoint(Location pos1,Location offset,double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.pos1=pos1.add(offset);
    }
    public int dela=0;
    @Override
    public void act(long delta) {
        super.act(delta);
        dela+=delta;
        if(dela>=1000/10){
            dela=0;
            for (Player p: Bukkit.getOnlinePlayers()){
                p.spawnParticle(particle,pos1, (int) (nums/(during/(1000/10))));
            }
        }
    }
    @Override
    public boolean isFinish() {
        return aliveTime>=during;
    }
}
