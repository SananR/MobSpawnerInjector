package com.codex.mobspawnerinjector.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftCreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.codex.mobspawnerinjector.util.CustomMobSpawner;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
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
		NBTTagCompound nbt = new NBTTagCompound();

		//Populates nbt with spawners data
		customSpawner.b(nbt);
		
		//Sets tile nbt to the NBT
		tile.a(nbt);
		
	}

}
