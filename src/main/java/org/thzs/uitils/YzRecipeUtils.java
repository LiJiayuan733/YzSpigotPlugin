package org.thzs.uitils;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.thzs.YzPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public static String parseSay(String s, InventoryClickEvent event){
        String item_name;
        String player_name;

        player_name=event.getWhoClicked().getName();

        if(event.getCurrentItem()==null){
            item_name="什么都没有呢OVO";
        }else{
            ItemMeta meta= event.getCurrentItem().getItemMeta();
            if(meta==null){
                item_name=event.getCurrentItem().getType().name();
            }else{
                String name=meta.getDisplayName();
                if(name.equals("")){
                    item_name=event.getCurrentItem().getType().name();
                }else{
                    item_name=name;
                }
            }
        }

        s=s.replace("{player}",player_name);
        s=s.replace("{item_name}",item_name);
        return s;
    }
}
