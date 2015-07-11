package de.nonamelabs.devathlon.paccraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import de.nonamelabs.devathlon.Plugin_DevAthlon;
import de.nonamelabs.devathlon.paccraft.items.Armor;
import de.nonamelabs.devathlon.paccraft.items.Items;
import de.nonamelabs.devathlon.paccraft.items.LootBoxen;
import de.nonamelabs.devathlon.paccraft.items.Weapons;

public class Game implements Listener{
	//Von Lau
	public static final ChatColor PLUGIN_COLOR = ChatColor.AQUA;
	public static final ChatColor CHATCOLOR = ChatColor.WHITE;
	public static final ChatColor PLUGIN_NAME_COLOR = ChatColor.DARK_PURPLE;
	public static final Material COIN_MATERIAL = Material.GOLD_NUGGET;
	public static final Material COIN_BOTTOM_BLOCK = Material.BARRIER;	
	public static final Material COIN_BOTTOM_BLOCK2 = Material.WOOD;	
	public static final int TOTAL_TIME = 300;
	
	public List<Player> player_list = new ArrayList<Player>();
	public List<Player> ghost_list = new ArrayList<Player>();
	public List<Player> spectator_list = new ArrayList<Player>();
	public Map<Player, Scoreboard> scoreboards = new HashMap<Player, Scoreboard>();
	public Map<Player, Integer> scores = new HashMap<Player, Integer>();
	public List<Location> playerspawns;
	
	public int players;
	public int ghosts;
	public Location ghostspawn;
	public Random r = new Random();
	public Location mapcorner1;
	public Location mapcorner2;	
	public String remainingTime;
	public boolean task_running = true;
	
	
	public Game(int players, int ghosts, List<Location> playerspawns, Location ghostspawn, Location mapcorner1, Location mapcorner2) {
		for (Player p: Bukkit.getOnlinePlayers()) {
			Scoreboard sc = Bukkit.getScoreboardManager().getNewScoreboard();
			scoreboards.put(p, sc);
			p.setScoreboard(sc);
		}
		
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
		this.playerspawns = playerspawns;
		
		initMap();
		
		Bukkit.getScheduler().runTaskAsynchronously(Plugin_DevAthlon.Instance, new Runnable() {
			
			@Override
			public void run() {
				for (int t = 1; t <= TOTAL_TIME && task_running; t++) {
					int time = TOTAL_TIME - t;
					String seconds = String.valueOf(time % 60);
					for (int i = 0; i < 2 - seconds.length(); i++) {
						seconds = "0" + seconds;
					}
					remainingTime = (time / 60) + ":" + seconds;
					
					if ((t % 30) == 0) {
						sendGameMessage(remainingTime + " verbleibend!");
					}
					
					updateScoreboards();
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				//TODO: finish Game!
			}
		});
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
		scores.put(p, 0);
		
		PlayerInventory inv = p.getInventory();	
		inv.clear();	
		inv.setArmorContents(new ItemStack[] {Armor.PlayerBoots(), Armor.PlayerLegs(), Armor.PlayerChest(), Armor.PlayerHelm()});
		inv.setItem(8, Items.Shop());
		inv.setItem(0, Weapons.WoodenSword());
		
		sendGameMessage("Du bist ein Spieler!", p);
		updateScoreboards();
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
		scores.put(p, 0);
		
		PlayerInventory inv = p.getInventory();	
		inv.clear();	
		inv.setArmorContents(new ItemStack[] {Armor.GhostBoots(), Armor.GhostLegs(), Armor.GhostChest(), Armor.GhostHelm()});
		inv.setItem(8, Items.Shop());
		inv.setItem(0, Weapons.StoneSword());
		
		sendGameMessage("Du bist ein Geist!", p);
		updateScoreboards();
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
		updateScoreboard(p);
	}
	
	public void updateScoreboards() {
		for (Player p: Bukkit.getOnlinePlayers()) {
			updateScoreboard(p);
		}
	}
	
	public void updateScoreboard(Player p) {
		Scoreboard sc = scoreboards.get(p);
		Objective obj;
		
		if (sc.getObjective("PacCraft") == null) {
			obj = sc.registerNewObjective("PacCraft", "dummy");
		} else {
			obj = sc.getObjective("PacCraft");
			obj.unregister();
			obj = sc.registerNewObjective("PacCraft", "dummy");
		}
		
		obj.setDisplayName(PLUGIN_NAME_COLOR + "PacCraft");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		int curr = 0;
		if (player_list.contains(p)) {
			obj.getScore(PLUGIN_COLOR + "Score: " + scores.get(p)).setScore(curr++);
			obj.getScore(PLUGIN_NAME_COLOR + "Spieler").setScore(curr++);
		} else if (ghost_list.contains(p)) {
			obj.getScore(PLUGIN_COLOR + "Score: " + scores.get(p)).setScore(curr++);
			obj.getScore(PLUGIN_NAME_COLOR + "Geist").setScore(curr++);
		} else if (spectator_list.contains(p)) {
			obj.getScore(PLUGIN_NAME_COLOR + "Zuschauer").setScore(curr++);
		}
		obj.getScore(PLUGIN_COLOR + "---------------").setScore(curr++);
		obj.getScore(PLUGIN_COLOR + "Geister: " + ghost_list.size()).setScore(curr++);
		obj.getScore(PLUGIN_COLOR + "Spieler: " + player_list.size()).setScore(curr++);
		obj.getScore(ChatColor.RESET + "" + PLUGIN_COLOR + "---------------").setScore(curr++);
		
		Player p1 = null, p2 = null, p3 = null;
		
		for (Player player: player_list) {
			if (p1 == null || scores.get(p1) < scores.get(player)) {
				p3 = p2;
				p2 = p1;
				p1 = player;
			} else if (p2 == null || scores.get(p2) < scores.get(player)) {
				p3 = p2;
				p2 = player;
			} else if (p3 == null || scores.get(p3) < scores.get(player)) {
				p3 = player;
			}
		}
		
		for (Player player: ghost_list) {
			if (p1 == null || scores.get(p1) < scores.get(player)) {
				p3 = p2;
				p2 = p1;
				p1 = player;
			} else if (p2 == null || scores.get(p2) < scores.get(player)) {
				p3 = p2;
				p2 = player;
			} else if (p3 == null || scores.get(p3) < scores.get(player)) {
				p3 = player;
			}
		}
		
		if (p3 != null)	obj.getScore(ChatColor.DARK_GRAY + "3. " + p3.getName() + " - " + scores.get(p3)).setScore(curr++);
		if (p2 != null) obj.getScore(ChatColor.GRAY + "2. " + p2.getName() + " - " + scores.get(p2)).setScore(curr++);		
		if (p1 != null) obj.getScore(ChatColor.GOLD + "1. " + p1.getName() + " - " + scores.get(p1)).setScore(curr++);
		obj.getScore(ChatColor.RESET + "" + ChatColor.RESET + PLUGIN_COLOR + "---------------").setScore(curr++);
		obj.getScore(PLUGIN_COLOR + "Zeit: " + remainingTime).setScore(curr++);
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
			p.getInventory().setArmorContents(new ItemStack[4]);
			
			p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		}
		
		task_running = false;
	}
	//Events-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage("");
		spectator_list.add(event.getPlayer());
		initSpectator(event.getPlayer(), ghostspawn);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		event.setQuitMessage("");
		if (ghost_list.contains(event.getPlayer())) {
			ghost_list.remove(event.getPlayer());
			sendGameMessage(event.getPlayer().getName() + " hat das Spiel verlassen! Es gibt noch " + ghost_list.size() + " Geister!");
			updateScoreboards();
		} else if (player_list.contains(event.getPlayer())) {
			player_list.remove(event.getPlayer());
			sendGameMessage(event.getPlayer().getName() + " hat das Spiel verlassen! Es gibt noch " + player_list.size() + " Spieler!");
			updateScoreboards();
		} else {
			spectator_list.remove(event.getPlayer());
		}
		scoreboards.remove(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if (!event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
			event.setCancelled(true);	
		}
	}
	
	@EventHandler
	public void onPlayerDamagePlayer(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			if (ghost_list.contains(((Player)event.getDamager())) && player_list.contains(((Player)event.getEntity()))) {				
				event.setCancelled(false);
				return;
			}
		}
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerLooseHunger(FoodLevelChangeEvent event) {
		if (!player_list.contains((Player) event.getEntity())) {
			event.setCancelled(true);
		}
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
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
			event.getItem().remove();
			
			int coins = 0;
			int random = r.nextInt(10);
			if (random < 6) {
				coins = 1;
			} else if (random < 9){
				coins = 2;
			} else {
				coins = 3;
			}
			scores.put(p, scores.get(p) + coins);
			updateScoreboard(p);
		}
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		final Player p = event.getEntity();
		
		if (p.getLastDamageCause().getEntity() instanceof Player) {
			Player p2 = (Player) p.getKiller();
			
			sendGameMessage(p2.getDisplayName() + PLUGIN_COLOR + " hat " + p.getDisplayName() + PLUGIN_COLOR + " getötet!");
			sendGameMessage("Du wurdes von " + p2.getDisplayName() + PLUGIN_COLOR + " getötet!", p);
			sendGameMessage("Du hast " + p.getDisplayName() + PLUGIN_COLOR + " getötet!", p2);
			
			scores.put(p2, scores.get(p2) + scores.get(p)/4);
			scores.put(p, scores.get(p) - scores.get(p)/4);
		}
		
		event.setDeathMessage("");
		event.setKeepInventory(true);
		event.setDroppedExp(0);
		
		Location l = null;
		if (ghost_list.contains(p)) {
			l = ghostspawn;
		} else if (player_list.contains(p)) {
			l = playerspawns.get(r.nextInt(playerspawns.size()));;
		}
		final Location spawnpoint = l;
		
		Bukkit.getScheduler().runTaskLater(Plugin_DevAthlon.Instance, new Runnable() {
			
			@Override
			public void run() {
				p.spigot().respawn();
				p.teleport(spawnpoint);
				if (player_list.contains(p)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 4));
				} else if (ghost_list.contains(p)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 2));
				}
			}
		}, 60L);
	}
	
	//Von PhoenixoForce
		@EventHandler
		public void onPlayerClick(PlayerInteractEvent event){	//Wenn der Spieler mit Irgendetwas interagiert wird dieses Event aufgerufen
			if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null) {
				//Wenn der Spieler rechtsklick mit einem Item in der Hand macht
				event.setCancelled(true);	//Eigentliche Event wird unterdrückt
				if (event.getItem().equals((Items.Shop()))) {
					//und das Item das Shop-Item ist
					event.setCancelled(true);
					event.getPlayer().openInventory(LootBoxen.getShopInventory());
					//Öffne das Shop-Inventar
				}
			}
		}
		@EventHandler
		public void onInventoryClick(InventoryClickEvent event) {  //wenn der Spieler in seinem Inventar etwas Anklickt wird dieses Ecent aufgerufen
			event.setCancelled(true);
				
			ItemStack clicked_item = event.getCurrentItem();
			//clicked_item ist das Item auf das der Spieler geklickt hat
			Player p = (Player) event.getWhoClicked();
			//p ist der Spieler der geklickt hat
					
			if (event.getInventory().getName().equals(Items.Shop())) {		
				//wenn das Shop Inventar offen ist
				if (clicked_item.equals(LootBoxen.Nahrung())) {
					//und das NahrungsItem Angeklickt wurde
					if(scores.get(p)>=1){
					//und der Spieler genug Geld hat
					p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN));
					//Gebe dem Spieler ein Huhn
					scores.put(p, scores.get(p)-1);
					//Und entferne ein Geld
				}}/*else if(clicked_item.equals(LootBoxen.getArmorUpgrade())){
					event.getWhoClicked().openInventory(LootBoxen.getArmorUpgradeInventory());
				}else if(clicked_item.equals(LootBoxen.getWeaponUpgrade())){    	Entfernter Inhalt
					if(p.getInventory().contains(Weapons.WoodenSword())){
						p.getInventory().remove(Weapons.WoodenSword());
						p.getInventory().addItem(Weapons.StoneSword());		
					}else if(p.getInventory().contains(Weapons.StoneSword())){
						p.getInventory().remove(Weapons.StoneSword());
						p.getInventory().addItem(Weapons.IronSword());
					}else if(p.getInventory().contains(Weapons.IronSword())){
						p.getInventory().remove(Weapons.IronSword());
						p.getInventory().addItem(Weapons.DiamoSword());
					}			*/
				}		
			}
}
