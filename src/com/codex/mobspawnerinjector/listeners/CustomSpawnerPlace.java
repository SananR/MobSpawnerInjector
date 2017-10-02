package com.codex.mobspawnerinjector.listeners;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftCreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.codex.mobspawnerinjector.util.CustomMobSpawner;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.TileEntityMobSpawner;
import net.minecraft.server.v1_8_R3.World;

public class CustomSpawnerPlace implements Listener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Block block = e.getBlock();
		
		BlockState state = block.getState();
	
		final TileEntityMobSpawner tile = ((CraftCreatureSpawner) state).getTileEntity();
		
		CustomMobSpawner customSpawner = new CustomMobSpawner() {
			@Override
			public BlockPosition b() {
				return tile.getPosition();
			}
			@Override
			public World a() {
				return tile.getWorld();
			}
			@Override
			public void a(int paramAnonymousInt) {
				tile.getWorld().playBlockAction(tile.getPosition(), Blocks.MOB_SPAWNER, paramAnonymousInt, 0);
			}
		};
		
		try {
			Field spawnerField = tile.getClass().getDeclaredField("a");
			spawnerField.setAccessible(true);
			
			spawnerField.set(tile, customSpawner);
			
		} catch (NoSuchFieldException x) {
			x.printStackTrace();
		} catch (SecurityException x) {
			x.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		
	}

}
