package org.thzs.effect.core;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class YzEffectFactoryBox {
    public YzEffectFactoryBox(){
        Effects=new ArrayList<>();
        Config=new ArrayList<>();
    }
    public List<YzEffect> Effects;
    public List<YamlConfiguration> Config;
    public void apply(YzEffectHandlerThread effect,Event event, YamlConfiguration configuration){
        //TODO: Check event can be used to the effect.
    }
    public void put(){

    }
}
