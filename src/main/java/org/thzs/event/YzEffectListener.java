package org.thzs.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.thzs.effect.*;
import org.thzs.effect.core.YzEffectFactory;
import org.thzs.effect.core.YzEffectHandlerThread;
import org.thzs.uitils.YzRecipeUtils;

import java.util.Objects;


public class YzEffectListener implements Listener {
    static class RecipeSetRunnable implements Runnable{
        int uncheck;
        int uncheckIndex;
        ItemStack uncheckItem;
        CraftingInventory inventory;
        ItemStack result;
        public RecipeSetRunnable(int uncheckIndex,ItemStack uncheckItem,int uncheck,CraftingInventory inventory,ItemStack result){
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
            if (uncheck == 1 && ((CraftingInventory) inventory).getMatrix()[uncheckIndex] != null && ((CraftingInventory) inventory).getMatrix()[uncheckIndex].getType() == uncheckItem.getType()) {
                ((CraftingInventory) inventory).setResult(result);
            }
        }
    }
    public static int ChestBlockIndexMap[]={0,1,2,9,10,11,18,19,20,13};
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event){

    }
    public void RecipeCheck(Inventory inventory, HumanEntity player,InventoryClickEvent event){
        for (Location i:YzRecipeUtils.RecipeLocation){
            Block block=i.getBlock();
            if(block.getType()==Material.CHEST){
                Chest chest= (Chest) block.getState();
                Inventory chestBlockInventory=chest.getBlockInventory();
                int uncheck=0;
                ItemStack uncheckItem=null;
                int uncheckIndex=0;
                for (int j=0;j<9;j++){
                    ItemStack type1=chestBlockInventory.getItem(ChestBlockIndexMap[j]);
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
                new Thread(new RecipeSetRunnable(uncheckIndex, uncheckItem, uncheck, (CraftingInventory) inventory, chestBlockInventory.getItem(ChestBlockIndexMap[9]).clone())).start();
            }
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        InventoryAction action=event.getAction();
        Inventory inventory=event.getClickedInventory();
        if(inventory==null){
            return;
        }
        if(inventory.getType()==InventoryType.WORKBENCH){
            switch (action){
                case PLACE_ONE:
                case PLACE_ALL:
                case PLACE_SOME:
                case SWAP_WITH_CURSOR:
                    ItemStack itemStack=event.getCursor();
                    RecipeCheck(inventory,event.getView().getPlayer(),event);
                    break;
                default:
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player p=event.getPlayer();
        if(p.hasPermission("yz.editor")){
            Block block=event.getBlockPlaced();
            if(block.getType()== Material.CHEST){
                Location location=event.getBlockReplacedState().getLocation();
                if(Objects.requireNonNull(location.getWorld()).getBlockAt(location.add(0,-1,0)).getType()==Material.CRAFTING_TABLE){
                    YzRecipeUtils.RecipeLocation.add(location.add(0,1,0));
                    event.getPlayer().sendMessage("配方箱位置已记录");
                }
            }
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player||event.getDamager() instanceof Arrow) {
            Player p;
            if(event.getDamager() instanceof Player){
                p= (Player) event.getDamager();
            }else{
                if(((Arrow)event.getDamager()).getShooter() instanceof Player){
                    p=(Player) ((Arrow)event.getDamager()).getShooter();
                }else {
                    return;
                }
            }
            ItemStack item = Objects.requireNonNull(p.getEquipment()).getItemInMainHand();
            if (item.getItemMeta().getLore() != null) {
                for (String s : item.getItemMeta().getLore()) {
                    if (s.startsWith("[Yz]")) {
                        String type = s.replace("[Yz]", "");
                        switch (type) {
                            case "PointAttack":
                                YzEffectHandlerThread.instance.add(new YzEffectPoint(event.getEntity(), new Location(p.getWorld(), 0, 3, 0), 2000, 60));
                                continue;
                            case "Circle":
                                YzEffectHandlerThread.instance.add(new YzEffectCircle(p.getLocation(), 10, 1000,30));
                                continue;
                            case "LineAttack":
                                YzEffectHandlerThread.instance.add(new YzEffectLine(p, event.getEntity(), 1000, 30));
                                continue;
                            default:
                                if(YzEffectFactory.EventCheck(type,event.getClass())) {
                                    YzEffectFactory.create(type).apply(YzEffectHandlerThread.instance, event);
                                }
                        }
                    }
                }
            }
        }
    }
}
