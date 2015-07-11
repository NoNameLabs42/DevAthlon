package de.nonamelabs.devathlon.paccraft.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LootBoxen {
	//Alles von PhoenixofForce
	
	//Inventar Shop															//Name in Rot
	public static Inventory Shop = Bukkit.createInventory(null, 9, ChatColor.RED + "Kaufe Nahrung und Tränke");
	static {
		Shop.setItem(1, new ItemStack(Nahrung()));					//Der zweite Slot im Inventar, ist das Item um Essen zu kaufen
		Shop.setItem(3, new ItemStack(HeilTrank()));
		Shop.setItem(5, new ItemStack(SpeedTrank()));
		Shop.setItem(6, new ItemStack(Multiplyer()));
		
		/*Shop.setItem(4, new ItemStack(getArmorUpgrade()));		/Entfernte Funktion, sollte eigentlich Waffen und Rüstung verbessern
		Shop.setItem(7, new ItemStack(getWeaponUpgrade()));*/
	}
	
	/*
	public static Inventory ArmorUpgrade = Bukkit.createInventory(null, 9, ChatColor.RED + "Verbessere deine Rüstung");
	static{
		ArmorUpgrade.setItem(1, new ItemStack(Material.DIAMOND_HELMET));
		ArmorUpgrade.setItem(3, new ItemStack(Material.DIAMOND_CHESTPLATE));		Entfernte Funktion
		ArmorUpgrade.setItem(5, new ItemStack(Material.DIAMOND_LEGGINGS));			Man Sollte eigentlich aussuchen können, welches Rüstungsteil verbessert werden soll
		ArmorUpgrade.setItem(7, new ItemStack(Material.DIAMOND_BOOTS));
	}*/
	
	
	public static Inventory getShopInventory() {
		return Shop;								//gibt Inventar zurück
	}
	
	/*public static Inventory getArmorUpgradeInventory() {
		return ArmorUpgrade;
	}*/
	
	//LootItems
	
	//NahrungsItem
	public static ItemStack Nahrung() {
		ItemStack item = new ItemStack(Material.COOKED_CHICKEN);	//Ist ein gekochtes Huhn
		List<String> lore = new ArrayList<String>();				
		lore.add(ChatColor.GREEN + "Alles Nestle, alles Hühnchen");	//Ein Untertitel, der sichtbar wird wenn man im Inventar den Mauszeiger über das Item legt
		lore.add(ChatColor.GREEN + "1Punkt = 1Nestle");				//Dittp
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Essen für hungrige Kerlchen");	//Der Neue Name des Items #SchwarzZuBlau
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack HeilTrank() {
		ItemStack item = new ItemStack(Material.POTION);	
		List<String> lore = new ArrayList<String>();				
		lore.add(ChatColor.GREEN + "Heiltrank");	
		lore.add(ChatColor.GREEN + "10 Punkte");				
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Trinken für tote Kerlchen");	
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack SpeedTrank() {
		ItemStack item = new ItemStack(Material.POTION);	
		List<String> lore = new ArrayList<String>();				
		lore.add(ChatColor.GREEN + "Speedtrank");	
		lore.add(ChatColor.GREEN + "5 Punkte");				
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Trinken für langsame Kerlchen");	
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack Multiplyer() {
		ItemStack item = new ItemStack(Material.GOLD_BLOCK);	
		List<String> lore = new ArrayList<String>();				
		lore.add(ChatColor.GREEN + "Man bekommt doppelt so viel Gold wie ursprünglich");
		lore.add(ChatColor.GREEN + "Hält eine Minute");
		lore.add(ChatColor.GREEN + "100 Punkte");				
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Doppelt so viel Gold");	
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	/*Entfernter Inhalt
	public static ItemStack getWeaponUpgrade() {
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Knüppel sie alle nieder!");		
		
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
	*/
}
