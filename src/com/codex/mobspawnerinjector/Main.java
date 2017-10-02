package com.codex.mobspawnerinjector;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.codex.mobspawnerinjector.listeners.CustomSpawnerPlace;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(new CustomSpawnerPlace(), this);
	}

}
