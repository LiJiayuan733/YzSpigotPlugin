package org.thzs.recipe;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class YzRecipeConfig {
    public List<Location> RecipeLocation=new ArrayList<>();
    public List<YzRecipe> Recipe=new ArrayList<>();
    public YzRecipeConfig(List<Location> locations){
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
            RecipeLocation.add(location);
            addRecipe(location);
        }
    }
    public void addRecipe(Location location){
        Recipe.add(new YzRecipe(location));
    }
    public void reload(){
        Recipe=new ArrayList<>();
        for(Location i:RecipeLocation){
            if(i.getBlock().getType() != Material.CHEST){
                RecipeLocation.remove(i);
            }else{
                addRecipe(i);
            }
        }
    }
}