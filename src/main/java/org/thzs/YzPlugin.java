package org.thzs;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.thzs.effect.core.YzEffectHandlerThread;
import org.thzs.event.YzEffectListener;

import java.io.File;
import java.util.Objects;

public class YzPlugin extends JavaPlugin {
    public YzEffectHandlerThread effect;
    public static YzPlugin instance;
    @Override
    public void onEnable() {
        super.onEnable();
        File file=getDataFolder();
        if(!file.exists()){
            file.mkdirs();
        }
        getLogger().info(file.getAbsolutePath());
        effect=new YzEffectHandlerThread(2);
        System.out.println("YzPlugin Enable");
        Objects.requireNonNull(Bukkit.getPluginCommand("yz")).setExecutor(new YzCommandHandler());
        Objects.requireNonNull(Bukkit.getPluginCommand("yz")).setTabCompleter(new YzTabCompleter());
        Bukkit.getPluginManager().registerEvents(new YzEffectListener(), this);
        YzPlugin.instance=this;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        System.out.println("YzPlugin Disable");
    }
}
