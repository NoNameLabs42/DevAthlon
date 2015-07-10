package de.nonamelabs.devathlon;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


import de.nonamelabs.devathlon.paccraft.Game;
import de.nonamelabs.devathlon.paccraft.items.LootBoxen;
import de.nonamelabs.devathlon.paccraft.items.Weapons;

public class Plugin_DevAthlon extends JavaPlugin{
	public static Plugin_DevAthlon Instance;
	
	public int curUpg;
	
	public int FirstArmUpg  = 50;
	public int SecArmUpg	= 250;
	public int ThirdArmUpg	= 600;
	public int FourthArmUpg = 1050;
	public int FiftArmUpg   = 1750;
	
	public int FirstWeaUpg  = 50;
	public int SecWeaUpg	= 250;
	public int ThirdWeaUpg	= 600;
	public int FourthWeaUpg = 1050;
	public int FiftWeaUpg   = 1750;
	
	
	
	public Logger logger = this.getLogger();
	
	//Variablen vor dem Spiel
	public List<Location> playerspawns = new ArrayList<Location>();  
	public Location ghostspawn;
	public int registered_playerspawns;
	
	//Spiel
	public Game game;
	
	public void onEnable() {
		Instance = this;
		
		loadConfig();
		
		logger.info("Plugin wurde gestartet!");
	}
	
	public void onDisable() {
		save_Config();
		
		logger.info("Plugin wurde gestoppt!");
	}
	
	public void loadConfig() {
    	FileConfiguration config = getConfig();
    	
    	ghostspawn = (Location) config.get("Geisterspawn");
    	registered_playerspawns = config.getInt("Playerspawnanzahl");
    	
    	for (int i = 0; i < registered_playerspawns; i++) {
    		playerspawns.add((Location) config.get("Playerspawn" + i));
    	}
    	    	
    	saveConfig();
    }
    
    public void save_Config() {
    	FileConfiguration config = getConfig();
    	
    	config.set("Geisterspawn", ghostspawn);
    	registered_playerspawns = playerspawns.size();
    	config.set("Playerspawnanzahl", registered_playerspawns);
    	for (int i = 0; i < registered_playerspawns; i++) {
    		config.set("Playerspawn" + i, playerspawns.get(i));
    	}
    	
    	saveConfig();
    }
	
	public boolean onCommand(CommandSender sender, Command command, String commandlabel, String[] args) {
		
		if (commandlabel.equalsIgnoreCase("addPlayerSpawn")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Du bist kein Spieler!");
				return false;
			}
			
			if (args.length != 0) {
				return false;
			}
			Player p = (Player) sender;
			playerspawns.add(p.getLocation());
			
			p.sendMessage(ChatColor.GREEN + "Deine Aktuelle Position wurde als Spawnpunkt für Spieler gesetzt. Es gibt jetzt " + playerspawns.size() + " Spielerspawns.");
			return true;
		} else if (commandlabel.equalsIgnoreCase("resetPlayerSpawns")) {
			if (args.length != 0) {
				return false;
			}
			playerspawns.clear();
			
			sender.sendMessage(ChatColor.GREEN + "Alle Spielerspawns wurden entfernt. Es gibt jetzt " + playerspawns.size() + " Spielerspawns.");
			return true;
		} else if (commandlabel.equalsIgnoreCase("setGhostSpawn")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Du bist kein Spieler!");
				return false;
			}
			
			if (args.length != 0) {
				return false;
			}
			
			Player p = (Player) sender;
			ghostspawn = p.getLocation();
			p.sendMessage(ChatColor.GREEN + "Deine Aktuelle Position wurde als Spawnpunkt für die Geister gesetzt.");
			return true;
		} else if (commandlabel.equalsIgnoreCase("startgame")) {
			if (args.length != 2) {
				sender.sendMessage(ChatColor.RED + "Das erste Argument muss die Anzahl der Spieler sein und das zweite Argument muss die Anzahl der Geister sein!");
				return false;
			}
			
			int players = 0;
			try {
				players = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "Das erste Argument muss die Anzahl der Spieler sein!");
				return false;
			}
			
			int ghosts = 0;
			try {
				ghosts = Integer.valueOf(args[1]);
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "Das Zweite Argument muss die Anzahl der Geister sein!");
				return false;
			}
			
			if (playerspawns.size() < players) {
				sender.sendMessage(ChatColor.RED + "Zu wenig Spieler Spawns!");
			}
			
			if (ghostspawn == null) {
				sender.sendMessage(ChatColor.RED + "Es wurde noch kein Geister Spawn gesetzt!");
			}
			
			game = new Game(players, ghosts, playerspawns, ghostspawn);
			sender.sendMessage(ChatColor.GREEN + "Das Spiel wurde mit " + players + " Spielern und " + ghosts + " Geistern gestartet!");
			return true;
		}
		
		return false;
	}
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event){
		if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null) {
			if (event.getItem().equals((de.nonamelabs.devathlon.paccraft.items.Items.Shop()))) {
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
				
				if (event.getInventory().getName().equals(de.nonamelabs.devathlon.paccraft.items.Items.Shop())) {		
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
