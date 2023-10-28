package org.thzs.uitils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerUtils {
    public static void sendGift(Player player, ItemStack ...items){
        Inventory inventory= Bukkit.createInventory(null,27,"Yz Gift View");
        inventory.addItem(items);
        player.openInventory(inventory);
    }
}
