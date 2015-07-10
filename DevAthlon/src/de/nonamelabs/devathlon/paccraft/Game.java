package de.nonamelabs.devathlon.paccraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.WeatherType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.nonamelabs.devathlon.paccraft.items.Armor;
import de.nonamelabs.devathlon.paccraft.items.Items;
import de.nonamelabs.devathlon.paccraft.items.LootBoxen;
import de.nonamelabs.devathlon.paccraft.items.Weapons;

public class Game implements Listener{
	public static final ChatColor PLUGIN_COLOR = ChatColor.AQUA;
	public static final ChatColor CHATCOLOR = ChatColor.WHITE;
	public static final ChatColor PLUGIN_NAME_COLOR = ChatColor.DARK_PURPLE;
	public static final Material COIN_MATERIAL = Material.GOLD_NUGGET;
	public static final Material COIN_BOTTOM_BLOCK = Material.BARRIER;	
	public static final Material COIN_BOTTOM_BLOCK2 = Material.WOOD;	
	
	public List<Player> player_list = new ArrayList<Player>();
	public List<Player> ghost_list = new ArrayList<Player>();
	public List<Player> spectator_list = new ArrayList<Player>();
	
	public int players;
	public int ghosts;
	public Location ghostspawn;
	public Random r = new Random();
	public Location mapcorner1;
	public Location mapcorner2;	
	
	public Game(int players, int ghosts, List<Location> playerspawns, Location ghostspawn, Location mapcorner1, Location mapcorner2) {
		for (Player p:Bukkit.getOnlinePlayers()) {
			if (r.nextBoolean()) {
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
			} else {
				if (ghost_list.size() < ghosts) {
					ghost_list.add(p);
					initGhost(p, ghostspawn);
				} else if (player_list.size() < players) {
					player_list.add(p);
					initPlayer(p, playerspawns.get(player_list.size()-1));
				} else {
				spectator_list.add(p);
					initSpectator(p, ghostspawn);
				}
			}
		}
		
		this.players = players;
		this.ghosts = ghosts;
		this.ghostspawn = ghostspawn;
		this.mapcorner1 = mapcorner1;
		this.mapcorner2 = mapcorner2;
		
		initMap();
	}
	
	public void initMap() {
		int counter = 0;
		for (int x = Math.min(mapcorner1.getBlockX(), mapcorner2.getBlockX()); x <= Math.max(mapcorner1.getBlockX(), mapcorner2.getBlockX()); x++) {
			for (int y = Math.min(mapcorner1.getBlockY(), mapcorner2.getBlockY()); y <= Math.max(mapcorner1.getBlockY(), mapcorner2.getBlockY()); y++) {
				for (int z = Math.min(mapcorner1.getBlockZ(), mapcorner2.getBlockZ()); z <= Math.max(mapcorner1.getBlockZ(), mapcorner2.getBlockZ()); z++) {
					if (mapcorner1.getWorld().getBlockAt(x, y, z).getType() == Material.AIR && (mapcorner1.getWorld().getBlockAt(x, y-1, z).getType() == COIN_BOTTOM_BLOCK || mapcorner1.getWorld().getBlockAt(x, y-1, z).getType() == COIN_BOTTOM_BLOCK2)) {
						if (r.nextBoolean() && r.nextBoolean()) {
							ItemStack coin = new ItemStack(COIN_MATERIAL, 1);
							ItemMeta meta = coin.getItemMeta();
							meta.setDisplayName(counter + "");
							counter++;
							coin.setItemMeta(meta);
							mapcorner1.getWorld().dropItem(new Location(mapcorner1.getWorld(), x + 0.5, y, z + 0.5), coin);
						}
					}
				}
			}
		}
	}
	
	public void initPlayer(Player p, Location l) {
		p.teleport(l);
		p.setGameMode(GameMode.ADVENTURE);
		p.setHealth(20);
		p.spigot().respawn();
		p.setFoodLevel(20);
		p.setExp(0);
		p.setLevel(0);
		p.setPlayerWeather(WeatherType.DOWNFALL);
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 4));
		p.setDisplayName(ChatColor.YELLOW + p.getName());
		p.setPlayerListName(ChatColor.YELLOW + p.getName());
		
		PlayerInventory inv = p.getInventory();	
		inv.clear();	
		inv.setArmorContents(new ItemStack[] {Armor.PlayerBoots(), Armor.PlayerLegs(), Armor.PlayerChest(), Armor.PlayerHelm()});
		inv.setItem(8, Items.Shop());
		
		sendGameMessage("Du bist ein Spieler!", p);
	}
	
	public void initGhost(Player p, Location l) {
		p.teleport(l);
		p.setGameMode(GameMode.ADVENTURE);
		p.setHealth(20);
		p.spigot().respawn();
		p.setFoodLevel(20);
		p.setExp(0);
		p.setLevel(0);
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 2));
		p.setDisplayName(ChatColor.WHITE + p.getName());
		p.setPlayerListName(ChatColor.WHITE + p.getName());

		PlayerInventory inv = p.getInventory();	
		inv.clear();	
		inv.setArmorContents(new ItemStack[] {Armor.GhostBoots(), Armor.GhostLegs(), Armor.GhostChest(), Armor.GhostHelm()});
		inv.setItem(8, Items.Shop());
		
		
		sendGameMessage("Du bist ein Geist!", p);
	}
	
	public void initSpectator(Player p, Location l) {
		p.teleport(l);
		p.setGameMode(GameMode.SPECTATOR);
		p.setHealth(20);
		p.spigot().respawn();
		p.getInventory().clear();
		p.setFoodLevel(20);
		p.setExp(0);
		p.setLevel(0);
		sendGameMessage("Das Spiel ist bereits voll! Du bist nun ein Spectator!", p);
	}
	
	public void sendGameMessage(String message) {
		Bukkit.broadcastMessage(PLUGIN_COLOR + "[" + PLUGIN_NAME_COLOR + "PacCraft" + PLUGIN_COLOR + "] " + message);
	}
	
	public void sendGameMessage(String message, Player p) {
		p.sendMessage(PLUGIN_COLOR + "[" + PLUGIN_NAME_COLOR + "PacCraft" + PLUGIN_COLOR + "] " + message);
	}
	
	public void stopGame() {
		HandlerList.unregisterAll(this);
		
		for (Entity e:mapcorner1.getWorld().getEntities()) {
			if (e instanceof Item) {
				e.remove();
			}
		}
		
		for (Player p: Bukkit.getOnlinePlayers()) {
			p.setDisplayName(p.getName());
			p.setPlayerListName(p.getDisplayName());
			p.setPlayerWeather(WeatherType.CLEAR);
			p.removePotionEffect(PotionEffectType.BLINDNESS);
			p.getInventory().clear();
		}
	}
	//Events-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		spectator_list.add(event.getPlayer());
		initSpectator(event.getPlayer(), ghostspawn);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		event.setQuitMessage("");
		if (ghost_list.contains(event.getPlayer())) {
			ghost_list.remove(event.getPlayer());
			sendGameMessage(event.getPlayer().getName() + " hat das Spiel verlassen! Es gibt noch " + ghost_list.size() + " Geister!");
		} else if (player_list.contains(event.getPlayer())) {
			player_list.remove(event.getPlayer());
			sendGameMessage(event.getPlayer().getName() + " hat das Spiel verlassen! Es gibt noch " + player_list.size() + " Spieler!");
		} else {
			spectator_list.remove(event.getPlayer());
		}
	}
	
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event){
		event.setCancelled(true);
		
		if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null) {
			if (event.getItem().equals((Items.Shop()))) {
				event.setCancelled(true);
				event.getPlayer().openInventory(LootBoxen.getShopInventory());
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
				event.getWhoClicked().openInventory(LootBoxen.getArmorUpgradeInventory());
			}else if(clicked_item.equals(LootBoxen.getWeaponUpgrade())){
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
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerLooseHunger(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (event.getMessage().contains("%")) {
			event.setCancelled(true);
			return;
		}

		if (spectator_list.contains(event.getPlayer())) {
			event.setCancelled(true);
			sendGameMessage("Du bist Spectator, du kannst nichts in den Chat schreiben", event.getPlayer());
			return;
		}
		
		Player p = event.getPlayer();
		String color = ChatColor.getLastColors(p.getDisplayName());
		event.setFormat(color + "%1$s" + CHATCOLOR + ": " + "%2$s");
	}
	
	@EventHandler
	public void onPlayerPickup(PlayerPickupItemEvent event) {
		Player p = event.getPlayer();
		if (player_list.contains(p) && event.getItem() != null && event.getItem().getItemStack().getType() == COIN_MATERIAL) {
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
			event.getItem().remove();
		}
		event.setCancelled(true);
	}	
}
