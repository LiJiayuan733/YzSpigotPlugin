package org.thzs.effect;

import org.bukkit.Effect;
import org.bukkit.Particle;

import java.util.UUID;

public class YzEffect{
    public UUID uuid;
    public Particle particle=Particle.HEART;
    public YzEffect(){
        this.lastTime=System.currentTimeMillis();
        this.aliveTime=0;
    }
    public void setUuid(UUID uuid){
        this.uuid=uuid;
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
}
