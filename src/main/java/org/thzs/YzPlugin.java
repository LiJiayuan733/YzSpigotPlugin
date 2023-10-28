package org.thzs;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.thzs.effect.core.YzEffectHandlerThread;
import org.thzs.event.YzAttackEffectListener;

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
        effect=new YzEffectHandlerThread(1);
        System.out.println("YzPlugin Enable");
        Objects.requireNonNull(Bukkit.getPluginCommand("yz")).setExecutor(new MainYzCommandHandler());
        Objects.requireNonNull(Bukkit.getPluginCommand("yz")).setTabCompleter(new YzTabCompleter());
        Bukkit.getPluginManager().registerEvents(new YzAttackEffectListener(), this);
        YzPlugin.instance=this;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        System.out.println("YzPlugin Disable");
    }
}
