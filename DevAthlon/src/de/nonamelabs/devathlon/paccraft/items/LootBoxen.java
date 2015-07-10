package de.nonamelabs.devathlon.paccraft.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;



public class LootBoxen {
	public static Inventory Shop = Bukkit.createInventory(null, 9, ChatColor.RED + "Kaufe Nahrung, Tränke, verbesser deine Rüstung und Waffen");
	static {
		Shop.setItem(1, new ItemStack(Nahrung()));
		Shop.setItem(4, new ItemStack(getArmorUpgrade()));
		Shop.setItem(7, new ItemStack(getWeaponUpgrade(null)));
	}
	
	public static Inventory ArmorUpgrade = Bukkit.createInventory(null, 9, ChatColor.RED + "Verbessere deine Rüstung");
	static{
		ArmorUpgrade.setItem(1, new ItemStack(Material.DIAMOND_HELMET));
		ArmorUpgrade.setItem(3, new ItemStack(Material.DIAMOND_CHESTPLATE));
		ArmorUpgrade.setItem(5, new ItemStack(Material.DIAMOND_LEGGINGS));
		ArmorUpgrade.setItem(7, new ItemStack(Material.DIAMOND_BOOTS));
	}
	
	
	//LootItems
	public static ItemStack Nahrung() {
		ItemStack item = new ItemStack(Material.COOKED_CHICKEN);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Alles Nestle, alles Hühnchen");
		lore.add(ChatColor.GREEN + "1Punkt = 1Nestle");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Essen für hungrige Kerlchen");
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack getWeaponUpgrade(Player p) {
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Knüppel sie alle nieder!");
		if(p.getInventory().contains(Weapons.WoodenSword())) lore.add(ChatColor.GREEN + "Kosten: 50");
		if(p.getInventory().contains(Weapons.StoneSword())) lore.add(ChatColor.GREEN + "Kosten: 250");
		if(p.getInventory().contains(Weapons.IronSword())) lore.add(ChatColor.GREEN + "Kosten: 600");
		
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Verbessert deine Waffe");
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack getArmorUpgrade() {
		ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Bleib Geschmeidig");
		lore.add(ChatColor.GREEN + "Kostet zwei Appel und nen Ei");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Verbessert deine Waffe");
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}
	
}
