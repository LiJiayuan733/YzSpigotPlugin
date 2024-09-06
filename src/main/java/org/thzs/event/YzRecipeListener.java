package org.thzs.event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.thzs.YzPlugin;
import org.thzs.recipe.YzRecipe;

import java.util.List;
import java.util.Objects;

public class YzRecipeListener implements Listener {
    public ItemStack random(YzRecipe result,Inventory inventory){
        if(result.resultPRange[result.resultSize-1]!=0&&result.resultSize>1){
            double d=YzPlugin.instance.random.nextFloat((float) 0, (float) result.resultPRange[result.resultSize-1]);
            System.out.println(result.resultPRange[result.resultSize-1]+","+d);
            if(result.resultSize==2){
                if(result.resultPRange[0]<d){
                    return result.result[1];
                }else{
                    return result.result[0];
                }
            }else{
                if(result.resultPRange[1]<d){
                    return result.result[2];
                }else if(result.resultPRange[0]<d){
                    return result.result[1];
                }else{
                    return result.result[0];
                }
            }
        }else{
            return result.result[0];
        }
    }
    //完成最后的检测
    static class RecipeSetRunnable implements Runnable{
        int uncheck;
        int uncheckIndex;
        ItemStack uncheckItem;
        CraftingInventory inventory;
        YzRecipe result;
        public RecipeSetRunnable(int uncheckIndex,ItemStack uncheckItem,int uncheck,CraftingInventory inventory,YzRecipe result){
            this.uncheckItem=uncheckItem;
            this.uncheck=uncheck;
            this.uncheckIndex=uncheckIndex;
            this.inventory=inventory;
            this.result=result;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (uncheck == 1 && inventory.getMatrix()[uncheckIndex] != null &&
                    inventory.getMatrix()[uncheckIndex].getType() == uncheckItem.getType()) {
                ((CraftingInventory) inventory).setResult(result.resultExample);
            }else if(uncheck==0){
                ((CraftingInventory) inventory).setResult(result.resultExample);
            }
        }
    }
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event){

    }
    public void RecipeCheck(Inventory inventory, HumanEntity player, InventoryClickEvent event){
        for(YzRecipe recipe:YzPlugin.instance.recipe.Recipe){
            int uncheck=0;
            ItemStack uncheckItem=null;
            int uncheckIndex=0;
            for (int j=0;j<9;j++){
                ItemStack type1=recipe.items[j];
                ItemStack type2=((CraftingInventory) inventory).getMatrix()[j];
                if ((type1 == null && type2 !=null)||(type1 != null && type2 ==null)){
                    if(type2==null&&uncheck<2){
                        uncheck++;
                        uncheckItem=type1;
                        uncheckIndex=j;
                    }else {
                        return;
                    }
                }else if(type1 != null && type1.getType() != type2.getType()){
                    return;
                }
            }
            new Thread(new RecipeSetRunnable(uncheckIndex, uncheckItem, uncheck, (CraftingInventory) inventory,recipe)).start();
        }
//        for (Location i: YzRecipeUtils.RecipeLocation){
//            Block block=i.getBlock();
//            if(block.getType()== Material.CHEST){
//                Chest chest= (Chest) block.getState();
//                Inventory chestBlockInventory=chest.getBlockInventory();
//                int uncheck=0;
//                ItemStack uncheckItem=null;
//                int uncheckIndex=0;
//                for (int j=0;j<9;j++){
//                    ItemStack type1=chestBlockInventory.getItem(ChestBlockIndexMap[j]);
//                    ItemStack type2=((CraftingInventory) inventory).getMatrix()[j];
//                    if ((type1 == null && type2 !=null)||(type1 != null && type2 ==null)){
//                        if(type2==null&&uncheck<2){
//                            uncheck++;
//                            uncheckItem=type1;
//                            uncheckIndex=j;
//                        }else {
//                            return;
//                        }
//                    }else if(type1 != null && type1.getType() != type2.getType()){
//                        return;
//                    }
//                }
//                new Thread(new RecipeSetRunnable(uncheckIndex, uncheckItem, uncheck, (CraftingInventory) inventory, chestBlockInventory.getItem(ChestBlockIndexMap[9]).clone())).start();
//            }
//        }
    }

    //检测配方是否匹配
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        InventoryAction action=event.getAction();
        Inventory inventory=event.getClickedInventory();
        if(inventory==null){
            return;
        }
        if(inventory.getType()== InventoryType.WORKBENCH){
            switch (action){
                case PLACE_ONE:
                case PLACE_ALL:
                case PLACE_SOME:
                case SWAP_WITH_CURSOR:
                    ItemStack itemStack=event.getCursor();
                    RecipeCheck(inventory,event.getView().getPlayer(),event);
                    break;
                case PICKUP_ONE:
                case PICKUP_HALF:
                case PICKUP_SOME:
                case PICKUP_ALL:
                    ItemStack item=event.getCurrentItem();
                    ItemMeta meta=item.getItemMeta();
                    if(meta!=null){
                        List<String> str=meta.getLore();
                        if(str!=null){
                            for(String s:str){
                                if(s.startsWith("[EXAMPLE")){
                                    int index=Integer.parseInt(s.replace("[EXAMPLE","").replace("]",""));
                                    event.setCurrentItem(random(YzPlugin.instance.recipe.Recipe.get(index),inventory));
                                }
                            }
                        }
                    }
                    break;
                default:
            }
        }
    }

    //配方箱放置检测
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player p=event.getPlayer();
        if(p.hasPermission("yz.editor")){
            Block block=event.getBlockPlaced();
            if(block.getType()== Material.CHEST){
                Location location=event.getBlockReplacedState().getLocation();
                if(Objects.requireNonNull(location.getWorld()).getBlockAt(location.add(0,-1,0)).getType()==Material.CRAFTING_TABLE){
                    YzPlugin.instance.recipe.addLocation(location.add(0,1,0));
                    event.getPlayer().sendMessage("配方箱位置已记录");
                }
            }
        }
    }
}
