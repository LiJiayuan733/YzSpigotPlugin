package org.thzs.recipe;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class YzRecipeConfig {
    public List<Location> RecipeLocation=new ArrayList<>();
    public List<YzRecipe> Recipe=new ArrayList<>();
    public YzRecipeConfig(List<Location> locations){
        locations=removeSame(locations);

        for(int i=0;i<locations.size();i++){
            if(locations.get(i).getBlock().getType() == Material.CHEST) {
                addLocation(locations.get(i));
            }else{
                locations.remove(i);
            }
        }
        this.RecipeLocation=locations;
    }
    public void addLocation(Location location){
        if(location.getBlock().getType() == Material.CHEST){
            boolean has = false;
            for (Location value : RecipeLocation) {
                if (location.getX() == value.getX() && location.getY() == value.getY()
                        && location.getZ() == value.getZ()) {
                    has = true;
                }
            }
            if(!has){
                RecipeLocation.add(location);
                addRecipe(location);
            }
        }
    }
    public void addRecipe(Location location){
        Recipe.add(new YzRecipe(location,Recipe.size()));
    }
    public void reload(){
        for(YzRecipe i:Recipe){
            i.deinit();
        }

        //去重
        RecipeLocation=removeSame(RecipeLocation);

        Recipe=new ArrayList<>();
        for(Location i:RecipeLocation){
            if(i.getBlock().getType() != Material.CHEST){
                RecipeLocation.remove(i);
            }else{
                addRecipe(i);
            }
        }
    }
    public void deinit(){
        for(YzRecipe i:Recipe){
            i.deinit();
        }
    }
    public List<Location> removeSame(List<Location> source){
        List<Location> locations1=new ArrayList<>();
        for (int i=0;i<source.size();i++){
            Location location=source.get(i);
            boolean has=false;
            for (Location value : locations1) {
                if (location.getX() == value.getX() && location.getY() == value.getY()
                        && location.getZ() == value.getZ()) {
                    has = true;
                }
            }
            if (!has){
                locations1.add(location);
            }
        }
        return  locations1;
    }
}