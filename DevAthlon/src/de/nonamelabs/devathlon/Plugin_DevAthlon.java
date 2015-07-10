package de.nonamelabs.devathlon;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin_DevAthlon extends JavaPlugin{
	public static Plugin_DevAthlon Instance;
	
	public Logger logger = this.getLogger();
	
	//Variablen vor dem Spiel
	public List<Location> playerspawns = new ArrayList<Location>();  
	public Location ghostspawn;
	public int registered_playerspawns;
	
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
		} else if (commandlabel.equalsIgnoreCase("resetPlayerSpawns")) {
			if (args.length != 0) {
				return false;
			}
			playerspawns.clear();
			
			sender.sendMessage(ChatColor.GREEN + "Alle Spielerspawns wurden entfernt. Es gibt jetzt " + playerspawns.size() + " Spielerspawns.");
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
			
			sender.sendMessage(ChatColor.GREEN + "Das Spiel wurde mit " + players + " Spielern und " + ghosts + " Geistern gestartet!");
			return true;
		}
		
		return false;
	}
}
