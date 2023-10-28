package org.thzs;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.thzs.effect.YzEffectHandlerThread;
import org.thzs.event.YzAttackEffectListener;

import java.util.Objects;

public class YzPlugin extends JavaPlugin {
    public YzEffectHandlerThread effect;

    @Override
    public void onEnable() {
        super.onEnable();
        effect=new YzEffectHandlerThread(1);
        System.out.println("YzPlugin Enable");
        Objects.requireNonNull(Bukkit.getPluginCommand("yz")).setExecutor(new MainYzCommandHandler());
        Objects.requireNonNull(Bukkit.getPluginCommand("yz")).setTabCompleter(new YzTabCompleter());
        Bukkit.getPluginManager().registerEvents(new YzAttackEffectListener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        System.out.println("YzPlugin Disable");
    }
}
