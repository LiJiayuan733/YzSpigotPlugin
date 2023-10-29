package org.thzs.effect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.thzs.effect.core.YzEffectEventInfoHandler;
import org.thzs.effect.core.YzEffectUtils;
import org.thzs.effect.core.YzEffect;


public class YzEffectLine extends YzEffect {
    public Location pos1,pos2;
    public boolean tracing1=false,tracing2=false;
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
        tracing1=false;
        tracing2=false;
    }
    public YzEffectLine(Entity pos1, Entity pos2, double during, int nums){
        super();
        this.during=during;
        this.nums=nums;
        this.epos2=pos2;
        this.epos1=pos1;
        tracing1=true;
        tracing2=false;
    }
    public int dela=0;
    @Override
    public void act(long delta) {
        super.act(delta);
        dela+=delta;
        if(dela>=YzEffect.Tick){
            dela=0;
            if(tracing1){
                pos1=epos1.getLocation();
            }
            if(tracing2){
                pos2=epos2.getLocation();
            }
            Location pos3 = YzEffectUtils.linePosition(pos1, pos2, aliveTime / during);
            for (Player p : pos3.getWorld().getPlayers()) {
                p.spawnParticle(particle, pos3, (int) (nums/(during/YzEffect.Tick)));
            }
        }
    }
    @Override
    public boolean isFinish() {
        return aliveTime>=during;
    }

    @Override
    public YzEffect loadForYaml(MemorySection config, Event event) {
        if (configKeyCheck(config, "Particle","During", "Num", "Start", "End")) {
            particle = particle(config);
            during = config.getDouble("During");
            nums = config.getInt("Num");
            if (config.isString("Start")) {
                Object ob= YzEffectEventInfoHandler.getInfo(event,config.getString("Start"));
                if(ob==null){
                    Bukkit.getLogger().info("[Yz]Particle:Line->Start Value Not Found");
                } else if(ob instanceof Location) {
                    pos1 = (Location) ob;
                }else  if(ob instanceof Entity){
                    epos1 = (Entity) ob;
                    tracing1=true;
                }
            }else if(config.isList("Center")){
                //TODO: 标准地址
            }
            if (config.isString("End")) {
                Object ob= YzEffectEventInfoHandler.getInfo(event,config.getString("End"));
                if(ob==null){
                    Bukkit.getLogger().info("[Yz]Particle:Line->End Value Not Found");
                } else if(ob instanceof Location) {
                    pos2 = (Location) ob;
                }else  if(ob instanceof Entity){
                    epos2 = (Entity) ob;
                    tracing2=true;
                }
            }else if(config.isList("Center")){
                //TODO: 标准地址
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
