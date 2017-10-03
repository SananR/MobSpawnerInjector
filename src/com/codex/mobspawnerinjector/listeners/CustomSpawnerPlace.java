package com.codex.mobspawnerinjector.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.codex.mobspawnerinjector.util.SpawnerUtil;

public class CustomSpawnerPlace implements Listener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		
		SpawnerUtil.injectCustomSpawnerIntoTile(block);
		
	}

}
