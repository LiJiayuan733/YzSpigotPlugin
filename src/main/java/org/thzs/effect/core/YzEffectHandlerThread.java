package org.thzs.effect.core;

import java.util.*;

//TODO 多线程同步
public class YzEffectHandlerThread{
    public static YzEffectHandlerThread instance;
    public List<Thread> pool;
    public YzEffectHandlerThread(int threadNum){
        pool=new ArrayList<>();
        yzEffectMap=new HashMap<>();
        for(int i=0;i<threadNum;i++){
            Thread t=new Thread(new YzEffectHandler(),"粒子效果"+i);
            t.setDaemon(true);
            t.start();
        }
        YzEffectHandlerThread.instance=this;
    }
    public final Map<UUID,YzEffect> yzEffectMap;
    public int num=0;
    public synchronized YzEffect hasEffect(){
        synchronized (yzEffectMap) {
            if (num <= 0) {
                return null;
            } else {
                Object[] uuids = yzEffectMap.keySet().toArray();
                if(uuids.length<=0){
                    return null;
                }
                return yzEffectMap.get(yzEffectMap.keySet().toArray()[new Random().nextInt(num)]);
            }
        }
    }
    public void stop(){
        for(Thread t:pool){
            t.stop();
        }
    }
    public UUID add(YzEffect effect){
        UUID u= UUID.randomUUID();
        effect.setUuid(u);
        yzEffectMap.put(u,effect);
        num++;
        return u;
    }
    public void del(YzEffect effect){
        num--;
        if (yzEffectMap.containsKey(effect.uuid)) {
            yzEffectMap.remove(effect.uuid);
        }
    }
}
