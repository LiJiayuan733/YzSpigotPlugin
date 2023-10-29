package org.thzs.effect.core;

import org.bukkit.configuration.MemorySection;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class YzEffectFactoryBox {
    public YzEffectFactoryBox(){
        Effects=new ArrayList<>();
        Configs=new ArrayList<>();
    }
    public List<YzEffect> Effects;
    public List<MemorySection> Configs;
    public void apply(YzEffectHandlerThread effect,Event event){
        List<Integer> canUse=new ArrayList<>();
        for(int i=0;i<Effects.size();i++){
            if(Effects.get(i).isSupportEvent(event.getClass())){
                canUse.add(i);
            }
        }
        for(int i=0;i<canUse.size();i++){
            //Effects.get(canUse.get(i)).loadForYaml(Configs.get(i),event);
            effect.add(Effects.get(canUse.get(i)).loadForYaml(Configs.get(i),event));
        }
    }
    public void put(YzEffect effect,MemorySection config){
        Effects.add(effect);
        Configs.add(config);
    }
}
