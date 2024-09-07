package org.thzs.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.thzs.YzPlugin;
import org.thzs.recipe.YzRecipe;
import org.thzs.uitils.YzRecipeUtils;

import java.util.List;
import java.util.Objects;

import static org.bukkit.event.inventory.InventoryType.SlotType.RESULT;

public class YzRecipeListener implements Listener {
    public int random(YzRecipe result,Inventory inventory){
        if(result.resultPRange[result.resultSize-1]!=0&&result.resultSize>1){
            double d=YzPlugin.instance.random.nextFloat((float) 0, (float) result.resultPRange[result.resultSize-1]);
            System.out.println(result.resultPRange[result.resultSize-1]+","+d);
            if(result.resultSize==2){
                if(result.resultPRange[0]<d){
                    return 1;
                }else{
                    return 0;
                }
            }else{
                if(result.resultPRange[1]<d){
                    return 2;
                }else if(result.resultPRange[0]<d){
                    return 1;
                }else{
                    return 0;
                }
            }
        }else{
            return 0;
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
//        for(YzRecipe recipe:YzPlugin.instance.recipe.Recipe){
//            int uncheck=0;
//            ItemStack uncheckItem=null;
//            int uncheckIndex=0;
//            for (int j=0;j<9;j++){
//                ItemStack type1=recipe.items[j];
//                ItemStack type2=((CraftingInventory) inventory).getMatrix()[j];
//                if ((type1 == null && type2 !=null)||(type1 != null && type2 ==null)){
//                    if(type2==null&&uncheck<2){
//                        uncheck++;
//                        uncheckItem=type1;
//                        uncheckIndex=j;
//                    }else {
//                        return;
//                    }
//                }else if(type1 != null && type1.getType() != type2.getType()){
//                    return;
//                }
//            }
//            new Thread(new RecipeSetRunnable(uncheckIndex, uncheckItem, uncheck, (CraftingInventory) inventory,recipe)).start();
//        }

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
    public boolean checkItem(ItemStack item, ItemStack item2){
        if(item==null&& item2!=null||item!=null&& item2==null){
            return false;
        }else if(item==null&&item2==null){
            return true;
        }else{
            ItemMeta meta1=item.getItemMeta();
            ItemMeta meta2=item2.getItemMeta();
            if (meta1==null&& meta2!=null||meta1!=null&& meta2==null){
                return false;
            }else if (meta1==null&& meta2==null){
                return true;
            }else{
                //检测名字是否相同
                String Name1=meta1.getDisplayName();
                String Name2=meta2.getDisplayName();
                if(Name1==null&& Name2!=null||Name1!=null&& Name2==null){
                    return false;
                }else if(Name1==null&& Name2==null){
                    return true;
                }else if(!Name1.equals(Name2)){
                    return false;
                }else{
                    //检测附魔是否相同
                    if(meta1.hasEnchants()&& meta2.hasEnchants()){
                        if(meta1.getEnchants().equals(meta2.getEnchants())){
                            //检测属性是否相同
                            if(!meta1.getItemFlags().equals(meta2.getItemFlags())){
                                return false;
                            }else{
                                //END
                                return true;
                            }
                        }
                    }else if(meta1.hasEnchants()==meta2.hasEnchants()){
                        return true;
                    }
                    return false;
                }
            }
        }
    }
    public void checkRecipe(InventoryClickEvent event, YzRecipe recipe){
        Inventory inventory=event.getClickedInventory();
        if(event.getSlotType()!=RESULT){
            int choice=random(recipe,event.getClickedInventory());
            event.setCurrentItem(recipe.result[choice]);
            return;
        }
        for (int i=0;i<recipe.items.length;i++){
            if(!checkItem(((CraftingInventory) inventory).getMatrix()[i],recipe.items[i])){
                event.setCancelled(true);
                return;
            }
        }
        int choice=random(recipe,event.getClickedInventory());
        event.setCurrentItem(recipe.result[choice]);
        //公告
        if(recipe.Say[choice]!=null){
            Bukkit.broadcastMessage(YzRecipeUtils.parseSay(recipe.Say[choice],event));
        }
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
//                case PLACE_ONE:
//                case PLACE_ALL:
//                case PLACE_SOME:
//                case SWAP_WITH_CURSOR:
//                    ItemStack itemStack=event.getCursor();
//                    RecipeCheck(inventory,event.getView().getPlayer(),event);
//                    break;
                case DROP_ONE_SLOT:
                case DROP_ALL_SLOT:
                case DROP_ALL_CURSOR:
                case DROP_ONE_CURSOR:
                case SWAP_WITH_CURSOR:
                case CLONE_STACK:
                case COLLECT_TO_CURSOR:
                case MOVE_TO_OTHER_INVENTORY:
                case HOTBAR_MOVE_AND_READD:
                case HOTBAR_SWAP:
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
                                    YzRecipe recipe=YzPlugin.instance.recipe.Recipe.get(index);
                                    checkRecipe(event,recipe);
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
