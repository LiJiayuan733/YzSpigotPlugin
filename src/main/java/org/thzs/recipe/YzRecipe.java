package org.thzs.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.thzs.YzPlugin;
import org.thzs.uitils.ItemUtils;

import java.util.Arrays;

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
    public String[] Say={null,null,null};
    public NamespacedKey key;
    public char[] map={'A', 'B', 'C','D','E','F','G','H','I'};

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
                    String P=findLore(config[setIndex],"[YzP]");
                    String S=findLore(config[setIndex],"[YzS]");
                    if(P!=null){
                        resultP[setIndex]=Double.parseDouble(P);
                        resultPRange[setIndex]=setIndex==0?resultP[setIndex]:resultP[setIndex-1]+resultP[setIndex];
                    }
                    if(S!=null){
                        Say[setIndex]=S;
                    }
                    setIndex++;
                }
            }
            resultSize=setIndex;
        }

        key=new NamespacedKey(YzPlugin.instance, "YZ"+index);
        if(resultExample!=null){
            ShapedRecipe recipe = new ShapedRecipe(key, resultExample);
            Material[] itemsk=new Material[9];
            String[] sk={"","",""};
            int index1=0;

            for(int i=0;i<9;i++){
                if(items[i]==null){
                    continue;
                }
                boolean flag=false;
                for(int j=0;j<index1;j++){
                    if(itemsk[j]==items[i].getType()){
                        flag=true;
                    }
                }
                if(!flag){
                    itemsk[index1]=items[i].getType();
                    index1++;
                }
            }
            for (int i = 0; i < 9; i++){
                if(items[i]==null){
                    sk[i/3]+=' ';
                    continue;
                }
                for(int j=0;j<index1;j++){
                    if(itemsk[j]==items[i].getType()){
                        sk[i/3]+=map[j];
                        break;
                    }
                }
            }
            recipe.shape(sk[0],sk[1],sk[2]);
            System.out.println(Arrays.toString(sk)+Arrays.toString(itemsk));
            for(int j=0;j<index1;j++){
                recipe.setIngredient(map[j],itemsk[j]);
            }
//            recipe.setIngredient('J',Material.AIR);

            Bukkit.addRecipe(recipe);
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
    public void deinit(){
        Bukkit.removeRecipe(key);
    }
}
