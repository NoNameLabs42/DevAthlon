package de.nonamelabs.devathlon.paccraft.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
	//Von PhoenixofForce
	public static ItemStack Shop() {  //Shop Item
		ItemStack item = new ItemStack(Material.NETHER_STAR);  //Ein Nether Stern
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Kommse ren k�nnse raus gucken");	//mit nem Untertitel
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Shop");	//Und einem Neuen namen ^^
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}
}
