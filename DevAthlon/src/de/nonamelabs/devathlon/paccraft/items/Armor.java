package de.nonamelabs.devathlon.paccraft.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Armor {
	
	//Von Lau:
    public static ItemStack setColor(ItemStack item, Color color){
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        item.setItemMeta(meta);
        return item;
    }
	
    //Ab hier von PhoenixofForce
	public static ItemStack PlayerHelm() {
		ItemStack item = new ItemStack(Material.LEATHER_HELMET);
		item = setColor(item, Color.YELLOW);														//Diese Zeile von Lau
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Der Helm eines Abenteuerers");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Furchtloser Helm");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack PlayerChest() {
		ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
		item = setColor(item, Color.YELLOW);															//Diese Zeile von Lau
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Der Brustpanzer eines Abenteuerers");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Brustpanzer");
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack PlayerLegs() {
		ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
		item = setColor(item, Color.YELLOW);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Die Thermohose eines Abenteuerers");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Hose");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack PlayerBoots() {
		ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
		item = setColor(item, Color.YELLOW);																			//Diese Zeile von Lau
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Die Stiefel eines Abenteuerers");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Stiefel");
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack GhostHelm() {
		ItemStack item = new ItemStack(Material.LEATHER_HELMET);
		item = setColor(item, Color.WHITE);																						//Diese Zeile von Lau
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Der Helm eines unsichtbaren Geistes");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Erschrekender Helm");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack GhostChest() {
		ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
		item = setColor(item, Color.WHITE);																					//Diese Zeile von Lau
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Der Brustpanzer eines unsichtbaren Geistes");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Undurchdringlicher Brustpanzer");
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack GhostLegs() {
		ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
		item = setColor(item, Color.WHITE);																						//Diese Zeile von Lau
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Die Thermohose eines unsichtbaren Geistes");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Geschmeidige Hose");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack GhostBoots() {
		ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
		item = setColor(item, Color.WHITE);																							//Diese Zeile von Lau
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Die Stiefel eines unsichtbaren Geistes");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Geräuschlose Stiefel");
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}	
}
