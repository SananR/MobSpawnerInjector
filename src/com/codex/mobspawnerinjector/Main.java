package com.codex.mobspawnerinjector;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.codex.mobspawnerinjector.listeners.CustomSpawnerLoad;
import com.codex.mobspawnerinjector.listeners.CustomSpawnerPlace;

public class Main extends JavaPlugin {
	
	public static Plugin plugin;
	
	public void onEnable() {
		plugin = this;
		Bukkit.getServer().getPluginManager().registerEvents(new CustomSpawnerPlace(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new CustomSpawnerLoad(), this);
	}
	
	
	
}
