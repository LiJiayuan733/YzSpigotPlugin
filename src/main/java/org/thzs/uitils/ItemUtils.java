package org.thzs.uitils;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    public ItemStack item;
    public ItemMeta meta;
    public ItemUtils(ItemStack item){
        this.item=item.clone();
        this.meta=getMeta();
    }
    public ItemUtils(Material item){
        this.item=new ItemStack(item);
        this.meta = getMeta();
    }
    private ItemMeta getMeta(){
        this.meta=item.getItemMeta();
        return item.getItemMeta();
    }
    public ItemUtils enc(Enchantment enchantment, int i){
        meta.addEnchant(enchantment,i,true);
        return this;
    }
    public ItemUtils flags(ItemFlag... flags){
        meta.addItemFlags(flags);
        return this;
    }
    public ItemUtils attr(Attribute attr,AttributeModifier attrm){
        meta.addAttributeModifier(attr,attrm);
        return this;
    }
    public ItemUtils lore(String lore){
        if(meta.hasLore()){
            List<String> l=meta.getLore();
            l.add(lore);
            meta.setLore(l);
            return this;
        }
        ArrayList<String> l=new ArrayList<>();
        l.add(lore);
        meta.setLore(l);
        return this;
    }
    public ItemUtils delLore(int i){
        if(!meta.hasLore()){
            meta.setLore(new ArrayList<>());
        }
        if(i<meta.getLore().size()) {
            meta.getLore().remove(i);
        }
        return this;
    }
    public ItemUtils name(String name){
        meta.setDisplayName(name);
        return this;
    }
    public ItemUtils unbreakable(){
        meta.setUnbreakable(!meta.isUnbreakable());
        return this;
    }
    public ItemStack use(){
        item.setItemMeta(meta);
        return item;
    }
}
