package org.thzs.recipe;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.thzs.uitils.ItemUtils;

import java.util.List;
import java.util.Objects;

public class YzRecipe {
    public static int[] ChestBlockIndexMap ={0,1,2, 9,10,11, 18,19,20,
                                            6,7,8,
                                            15,16,17};
    public ItemStack[] items=new ItemStack[9];
    public ItemStack[] result=new ItemStack[3];
    public ItemStack resultExample;
    public ItemStack[] config=new ItemStack[3];
    public double[] resultP=new double[3];
    public int resultSize;
    public double[] resultPRange= {0,0,0};
    public YzRecipe(Location location,int index){ //位置
        Block block=location.getBlock();
        if(block.getType()== Material.CHEST){
            Chest chest= (Chest) block.getState();
            Inventory chestBlockInventory=chest.getBlockInventory();
            for (int j=0;j<9;j++){
                items[j]=chestBlockInventory.getItem(ChestBlockIndexMap[j]);
            }
            int setIndex=0;
            for(int i=0;i<3;i++){
                result[setIndex]=chestBlockInventory.getItem(ChestBlockIndexMap[9+i]);
                if(result[setIndex]!=null){
                    if(setIndex==0){
                        resultExample=new ItemUtils(result[setIndex]).lore("[EXAMPLE"+index+"]").use();
                    }
                    config[setIndex]=chestBlockInventory.getItem(ChestBlockIndexMap[12+i]);
                    if(config[setIndex]==null){
                        setIndex++;
                        continue;
                    }
                    String s=findLore(config[setIndex],"[YzP]");
                    if(s!=null){
                        resultP[setIndex]=Double.parseDouble(s);
                        resultPRange[setIndex]=setIndex==0?resultP[setIndex]:resultP[setIndex-1]+resultP[setIndex];
                    }
                    setIndex++;
                }
            }
            resultSize=setIndex;
        }
    }
    public String findLore(ItemStack item,String head){
        if(item.getItemMeta()==null){
            return null;
        }
        if(item.getItemMeta().getLore()==null){
            return null;
        }
        for(String s: item.getItemMeta().getLore()){
            if(s.startsWith(head)){
                return s.replace(head,"");
            }
        }
        return null;
    }
}
