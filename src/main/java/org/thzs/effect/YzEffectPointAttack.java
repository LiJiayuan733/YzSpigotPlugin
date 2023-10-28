package org.thzs.effect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class YzEffectPointAttack extends YzEffect{
    public Entity pos1;
    public double during;
    public int nums;
    public Location offset;
    public YzEffectPointAttack(Entity pos1, double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.pos1=pos1;
    }
    public YzEffectPointAttack(Entity pos1,Location offset,double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.pos1=pos1;
        this.offset=offset;
    }
    public int dela=0;
    @Override
    public void act(long delta) {
        super.act(delta);
        dela+=delta;
        if(dela>=1000/10){
            dela=0;
            Location pos2=pos1.getLocation().clone().add(offset);
            for (Player p: Bukkit.getOnlinePlayers()){
                p.spawnParticle(particle,pos2, (int) (nums/(during/(1000/10))));
            }
        }
    }
    @Override
    public boolean isFinish() {
        return aliveTime>=during;
    }
}
