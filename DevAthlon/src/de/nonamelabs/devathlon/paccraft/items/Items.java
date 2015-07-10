package de.nonamelabs.devathlon.paccraft.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
	public static ItemStack Shop() {
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Kommse ren könnse raus gucken");
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Shop");
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}
	
	
	
}
