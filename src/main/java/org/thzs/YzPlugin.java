package org.thzs;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.thzs.effect.core.YzEffectHandlerThread;
import org.thzs.recipe.YzRecipeConfig;
import org.thzs.event.YzEffectListener;
import org.thzs.event.YzRecipeListener;
import org.thzs.uitils.YzRecipeUtils;

import java.io.File;
import java.util.Objects;
import java.util.Random;

public class YzPlugin extends JavaPlugin {
    public Random random;
    public YzEffectHandlerThread effect;
    public YzRecipeConfig recipe;
    public static YzPlugin instance;
    @Override
    public void onEnable() {
        super.onEnable();
        random=new Random();
        File file=getDataFolder();
        if(!file.exists()){
            file.mkdirs();
        }

        YzPlugin.instance=this;

        effect=new YzEffectHandlerThread(2);
        recipe=new YzRecipeConfig(YzRecipeUtils.loadRecipeConfig());

        getLogger().info("Yz Recipe Count: "+recipe.Recipe.size());
        Objects.requireNonNull(Bukkit.getPluginCommand("yz")).setExecutor(new YzCommandHandler());
        Objects.requireNonNull(Bukkit.getPluginCommand("yz")).setTabCompleter(new YzTabCompleter());

        Bukkit.getPluginManager().registerEvents(new YzEffectListener(), this);
        Bukkit.getPluginManager().registerEvents(new YzRecipeListener(), this);
        getLogger().info("YzPlugin Enable");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        YzRecipeUtils.saveRecipeConfig(recipe.RecipeLocation);
        getLogger().info("YzPlugin Disable");
    }
}
