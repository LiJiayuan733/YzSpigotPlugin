package org.thzs.uitils;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.thzs.YzPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YzRecipeUtils {
    public static List<Location> RecipeLocation=new ArrayList<>();
    public static YamlConfiguration RecipeConfiguration;
    public static List<Location> loadRecipeConfig(){
        File file=new File(YzPlugin.instance.getDataFolder().getAbsoluteFile()+"/"+"RecipePosition"+".yml");
        if(!file.exists()){
            try {
                file.createNewFile();
                RecipeConfiguration=new YamlConfiguration();
                return new ArrayList<>();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            RecipeConfiguration=YamlConfiguration.loadConfiguration(file);
            ConfigurationSection section= RecipeConfiguration.getConfigurationSection("Positions");
            int size=RecipeConfiguration.getInt("Size");
            for (int i=0;i<size;i++){
                RecipeLocation.add(section.getLocation(i+""));
            }
            return RecipeLocation;
        }
    }
    public static void saveRecipeConfig(List<Location> position){
        YamlConfiguration yamlConfiguration=new YamlConfiguration();
        yamlConfiguration.set("Size",position.size());
        ConfigurationSection section=yamlConfiguration.createSection("Positions");
        for(int i=0;i<position.size();i++){
            section.set(i+"",position.get(i));
        }
        File file=new File(YzPlugin.instance.getDataFolder().getAbsoluteFile()+"/"+"RecipePosition"+".yml");
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
