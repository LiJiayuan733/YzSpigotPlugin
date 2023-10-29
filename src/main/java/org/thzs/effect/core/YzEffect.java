package org.thzs.effect.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.MemorySection;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class YzEffect{
    public static float Tick= (float) 1000 /10;
    public UUID uuid;
    public int Channel;
    public Particle particle=Particle.HEART;
    public YzEffect(){
        this.lastTime=System.currentTimeMillis();
        this.aliveTime=0;
    }
    public void setUuidAndChannel(UUID uuid,int channel){
        this.uuid=uuid;
        this.Channel=channel;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getChannel() {
        return Channel;
    }

    public void setParticle(Particle particle){
        this.particle=particle;
    }
    /**
     * 确认是否结束,如果结束就删除自己
     * */
    public void check(){
        if(isFinish()){
            YzEffectHandlerThread.instance.del(this);
        }
    }
    public long lastTime;
    public long aliveTime;
    public void update(long  time){
        act(time-lastTime);
        lastTime=time;
    }
    /**
     * 更新时间和动作 ms
     * user
     * */
    public void act(long delta){
        aliveTime+=delta;
    }
    public boolean isFinish(){
        return aliveTime>=1000;
    }
    public abstract YzEffect loadForYaml(MemorySection config, Event event);
    protected static boolean configKeyCheck(MemorySection config,String ...keys){
        for(String s:keys){
            if(!config.contains(s)){
                return false;
            }
        }
        return true;
    }
    protected static Particle particle(MemorySection config){
        return YzEffectFactory.convertParticle(Objects.requireNonNull(config.getString("Particle")));
    }
    protected static Location location(List<?> data){
        Object o=data.get(0);
        if(o instanceof Float || o instanceof Double) {
            return new Location(null, (double) data.get(0), (double) data.get(1), (double) data.get(2));
        }else if(o instanceof Integer){
            return new Location(null, (int) data.get(0), (int) data.get(1), (int) data.get(2));
        }else {
            Bukkit.getLogger().info("[Yz]getPosition Failed:"+data.toString());
            return new Location(null,0,0,0);
        }
    }
    public abstract boolean isSupportEvent(Class<? extends Event> clazz);
}
