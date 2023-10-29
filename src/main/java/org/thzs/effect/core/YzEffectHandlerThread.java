package org.thzs.effect.core;

import java.util.*;

//TODO 多线程同步
public class YzEffectHandlerThread{
    public static YzEffectHandlerThread instance;
    public List<Thread> pool;
    public YzEffectHandlerThread(int threadNum){
        pool=new ArrayList<>();
        yzEffectMaps=new ArrayList<>();
        nums=new ArrayList<>();
        for(int i=0;i<threadNum;i++){
            yzEffectMaps.add(new HashMap<>());
            nums.add(0);
            Thread t=new Thread(new YzEffectHandler(i),"粒子效果"+i);
            t.setDaemon(true);
            t.start();
        }
        YzEffectHandlerThread.instance=this;
    }
    public final List<Map<UUID,YzEffect>> yzEffectMaps;
    public List<Integer> nums;
    public synchronized YzEffect hasEffect(int channel){
        int num=nums.get(channel);
        Map<UUID,YzEffect> yzEffectMap=yzEffectMaps.get(channel);
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
    public void stop(){
        for(Thread t:pool){
            t.stop();
        }
    }
    public YzEffect add(YzEffect effect){
        UUID u= UUID.randomUUID();
        int minChannel=0;
        int minNum=nums.get(0);
        for (int i=0;i<nums.size();i++){
            int num=nums.get(i);
            if(num==0){
                minChannel=i;
                minNum=0;
                break;
            }else if(minNum>num){
                minNum=num;
                minChannel=i;
            }
        }
        effect.setUuidAndChannel(u,minChannel);
        yzEffectMaps.get(minChannel).put(u,effect);
        nums.set(minChannel,minNum+1);
        return effect;
    }
    public void del(YzEffect effect){
        int channel=effect.getChannel();
        int num=nums.get(channel);
        nums.set(channel,num-1);
        Map<UUID,YzEffect> yzEffectMap=yzEffectMaps.get(channel);
        if (yzEffectMap.containsKey(effect.uuid)) {
            yzEffectMap.remove(effect.uuid);
        }
    }
}
