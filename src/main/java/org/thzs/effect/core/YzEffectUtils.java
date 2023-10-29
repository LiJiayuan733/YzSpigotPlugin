package org.thzs.effect.core;

import org.bukkit.Location;

public class YzEffectUtils {
    public static Location linePosition(Location pos1,Location pos2,double rate){
        return new Location(pos1.getWorld(),pos1.getX()+(pos2.getX()-pos1.getX())*rate,pos1.getY()+(pos2.getY()-pos1.getY())*rate,pos1.getZ()+(pos2.getZ()-pos1.getZ())*rate);
    }
    public static Location cirPosition(Location center,double r,double rate){
        double x,z;
        x=r*Math.cos(Math.toRadians(360*rate));
        z=r*Math.sin(Math.toRadians(360*rate));
        return new Location(center.getWorld(),center.getX()+x,center.getY(),center.getZ()+z);
    }
}
