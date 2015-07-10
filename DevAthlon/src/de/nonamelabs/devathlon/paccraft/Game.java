package de.nonamelabs.devathlon.paccraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.nonamelabs.devathlon.paccraft.items.Items;
import de.nonamelabs.devathlon.paccraft.items.LootBoxen;
import de.nonamelabs.devathlon.paccraft.items.Weapons;

public class Game implements Listener{
	List<Player> player_list = new ArrayList<Player>();
	List<Player> ghost_list = new ArrayList<Player>();
	List<Player> spectator_list = new ArrayList<Player>();
	public Game(int players, int ghosts, List<Location> playerspawns, Location ghostspawn) {
		if (Bukkit.getOnlinePlayers().size() < (players+ghosts)) {
			return;
		}
		
		for (int i = 0; i < players; i++) {
			for (Player p:Bukkit.getOnlinePlayers()) {
				if (player_list.size() < players) {
					player_list.add(p);
					initPlayer(p, playerspawns.get(player_list.size()-1));
				} else if (ghost_list.size() < ghosts) {
					ghost_list.add(p);
					initGhost(p, ghostspawn);
				} else {
					spectator_list.add(p);
					initSpectator(p, ghostspawn);
				}
			}
		}
	}
	
	public void initPlayer(Player p, Location l) {
		p.teleport(l);
		p.setGameMode(GameMode.ADVENTURE);
		p.setHealth(20);
		p.spigot().respawn();
		p.getInventory().clear();
		p.setFoodLevel(20);
		p.getInventory().setItem(8, Items.Shop());
	}
	
	public void initGhost(Player p, Location l) {
		p.teleport(l);
		p.setGameMode(GameMode.ADVENTURE);
		p.setHealth(20);
		p.spigot().respawn();
		p.getInventory().clear();
		p.setFoodLevel(20);
		p.getInventory().setItem(8, Items.Shop());		
	}
	
	public void initSpectator(Player p, Location l) {
		p.teleport(l);
		p.setGameMode(GameMode.SPECTATOR);
		p.setHealth(20);
		p.spigot().respawn();
		p.getInventory().clear();
		p.setFoodLevel(20);
		
	}
	
	
	
	//Events-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event){
		if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null) {
			if (event.getItem().equals((Items.Shop()))) {
				event.setCancelled(true);
				event.getPlayer().openInventory(LootBoxen.Shop);
			}
		}
	}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		event.setCancelled(true);
			
		ItemStack clicked_item = event.getCurrentItem();
		Player p = (Player) event.getWhoClicked();
				
		if (event.getInventory().getName().equals(Items.Shop())) {		
			if (clicked_item.equals(LootBoxen.Nahrung())) {
				p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN));			
			}else if(clicked_item.equals(LootBoxen.getArmorUpgrade())){
				event.getWhoClicked().openInventory(LootBoxen.ArmorUpgrade);
			}else if(clicked_item.equals(LootBoxen.getWeaponUpgrade(p))){
				if(p.getInventory().contains(Weapons.WoodenSword())){
					p.getInventory().remove(Weapons.WoodenSword());
					p.getInventory().addItem(Weapons.StoneSword());		
				}else if(p.getInventory().contains(Weapons.StoneSword())){
					p.getInventory().remove(Weapons.StoneSword());
					p.getInventory().addItem(Weapons.IronSword());
				}else if(p.getInventory().contains(Weapons.IronSword())){
					p.getInventory().remove(Weapons.IronSword());
					p.getInventory().addItem(Weapons.DiamoSword());
				}			
			}		
		}
	}
	
}
