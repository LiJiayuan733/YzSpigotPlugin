package org.thzs.effect.core;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class YzEffectEventInfoHandler {
    public static Object getInfo(Event event, String infoName){
        if(event instanceof EntityDamageByEntityEvent){
            return EntityDamageByEntityEvent((EntityDamageByEntityEvent) event,infoName);
        }
        return null;
    }
    public static Object EntityDamageByEntityEvent(EntityDamageByEntityEvent event,String infoName){
        switch (infoName){
            case "Damager":
                return EntityCheck(event.getDamager());
            case "$Damager":
                return EntityCheck(event.getDamager()).getLocation();
            case "Entity":
                return event.getEntity();
            case "$Entity":
                return event.getEntity().getLocation();
            default:
                return null;
        }
    }
    public static Entity EntityCheck(Entity entity){
        if(entity instanceof Arrow){
            return (Entity) ((Arrow)entity).getShooter();
        }else{
            return entity;
        }
    }
}
