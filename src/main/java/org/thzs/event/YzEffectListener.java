package org.thzs.event;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.*;
import org.thzs.effect.*;
import org.thzs.effect.core.YzEffectFactory;
import org.thzs.effect.core.YzEffectHandlerThread;

public class YzEffectListener implements Listener {

    //粒子特效监听
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player||event.getDamager() instanceof Arrow) {
            Player p;
            if(event.getDamager() instanceof Player){
                p= (Player) event.getDamager();
            }else{
                if(((Arrow)event.getDamager()).getShooter() instanceof Player){
                    p=(Player) ((Arrow)event.getDamager()).getShooter();
                }else {
                    return;
                }
            }
            EntityEquipment equipment= p.getEquipment();
            if(equipment==null){
                return;
            }
            ItemStack item = equipment.getItemInMainHand();
            if(item.getItemMeta()==null){
                return;
            }
            if (item.getItemMeta().getLore() != null) {
                for (String s : item.getItemMeta().getLore()) {
                    if (s.startsWith("[Yz]")) {
                        String type = s.replace("[Yz]", "");
                        switch (type) {
                            case "PointAttack":
                                YzEffectHandlerThread.instance.add(new YzEffectPoint(event.getEntity(), new Location(p.getWorld(), 0, 3, 0), 2000, 60));
                                continue;
                            case "Circle":
                                YzEffectHandlerThread.instance.add(new YzEffectCircle(p.getLocation(), 10, 1000,30));
                                continue;
                            case "LineAttack":
                                YzEffectHandlerThread.instance.add(new YzEffectLine(p, event.getEntity(), 1000, 30));
                                continue;
                            default:
                                if(YzEffectFactory.EventCheck(type,event.getClass())) {
                                    YzEffectFactory.create(type).apply(YzEffectHandlerThread.instance, event);
                                }
                        }
                    }
                }
            }
        }
    }
}
