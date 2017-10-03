package com.codex.mobspawnerinjector.listeners;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import com.codex.mobspawnerinjector.util.SpawnerUtil;

public class CustomSpawnerLoad implements Listener {
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		Chunk chunk = e.getChunk();
		World world = chunk.getWorld();
		int cx = chunk.getX() << 4;
		int cz = chunk.getZ() << 4;
		for (int x = cx; x < cx + 16; x++) {
			for (int z = cz; z < cz + 16; z++) {
				for (int y = 0; y < 256; y++) {
					if (world.getBlockAt(x, y, z).getType() == Material.MOB_SPAWNER) {
						SpawnerUtil.injectCustomSpawnerIntoTile(world.getBlockAt(x, y, z));
					}
				}
			}
		}
	}

}
