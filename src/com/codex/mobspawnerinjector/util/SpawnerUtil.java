package com.codex.mobspawnerinjector.util;

import java.lang.reflect.Field;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftCreatureSpawner;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.TileEntityMobSpawner;
import net.minecraft.server.v1_8_R3.World;

public class SpawnerUtil {

	public static void injectCustomSpawnerIntoTile(Block block) {
		
		BlockState blockState = block.getState();

		try {
			final TileEntityMobSpawner tileMobSpawner = ((CraftCreatureSpawner) blockState).getTileEntity();
			
			CustomMobSpawner customSpawner = new CustomMobSpawner() {
				@Override
				public BlockPosition b() {
					return tileMobSpawner.getPosition();
				}
				@Override
				public World a() {
					return tileMobSpawner.getWorld();
				}
				@Override
				public void a(int paramAnonymousInt) {
					tileMobSpawner.getWorld().playBlockAction(tileMobSpawner.getPosition(), Blocks.MOB_SPAWNER, paramAnonymousInt, 0);
				}
			};
			
			//Reflection to inject custom spawner
			
			Field abstractSpawnerField = tileMobSpawner.getClass().getDeclaredField("a");
			abstractSpawnerField.setAccessible(true);
			
			abstractSpawnerField.set(tileMobSpawner, customSpawner);
		} catch (Exception exception) {
			exception.printStackTrace();
		} 
	}
	
}
