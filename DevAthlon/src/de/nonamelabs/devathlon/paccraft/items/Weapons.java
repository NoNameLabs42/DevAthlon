package de.nonamelabs.devathlon.paccraft.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Weapons {
	public static ItemStack WoodenSword() {
		ItemStack item = new ItemStack(Material.WOOD_SWORD);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Das mutige Schwert");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Excalibur - Prototyp");
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack StoneSword() {
		ItemStack item = new ItemStack(Material.STONE_SWORD);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Das tapfere Schwert");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Excalibur - Demo");
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack IronSword() {
		ItemStack item = new ItemStack(Material.IRON_SWORD);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Das beste Schwert");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Excalibur");
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack DiamoSword() {
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Das beste Schwert, was je geschaffen wurde");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Göttliches Excalibur");
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}

}
