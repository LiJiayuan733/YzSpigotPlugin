package org.thzs.effect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class YzEffectLineAttack extends YzEffect{
    public Entity epos1,epos2;
    public double during;
    public int nums;
    public YzEffectLineAttack(Entity pos1,Entity pos2,double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.epos2=pos2;
        this.epos1=pos1;
    }
    public int dela=0;
    @Override
    public void act(long delta) {
        super.act(delta);
        dela+=delta;
        if(dela>1000/10){
            dela=0;
            Location pos1=epos1.getLocation();Location pos2=epos2.getLocation();
            Location pos3=EffectUtils.linePosition(pos1,pos2,aliveTime/during);
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
