package org.thzs.recipe;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class YzRecipe {
    public static int[] ChestBlockIndexMap ={0,1,2,9,10,11,18,19,20,13};
    public ItemStack[] items=new ItemStack[9];
    public ItemStack result=null;
    public YzRecipe(Location location){ //位置
        Block block=location.getBlock();
        if(block.getType()== Material.CHEST){
            Chest chest= (Chest) block.getState();
            Inventory chestBlockInventory=chest.getBlockInventory();
            for (int j=0;j<9;j++){
                items[j]=chestBlockInventory.getItem(ChestBlockIndexMap[j]);
            }
            result=chestBlockInventory.getItem(ChestBlockIndexMap[9]);
        }
    }
}
