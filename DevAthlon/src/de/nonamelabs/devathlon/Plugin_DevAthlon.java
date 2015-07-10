package de.nonamelabs.devathlon;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Plugin_DevAthlon extends JavaPlugin{
	public static Plugin_DevAthlon Instance;
	
	public Logger logger = this.getLogger();
	
	public void onEnable() {
		Instance = this;
		logger.info("Plugin wurde gestartet!");
	}
	
	public void onDisable() {
		logger.info("Plugin wurde gestoppt!");
	}
}
