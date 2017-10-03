package com.codex.mobspawnerinjector.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.codex.mobspawnerinjector.Main;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class ParticleUtil {

	public static void createSpawnerParticleEffectAtLocation(final Location loc) {
		new BukkitRunnable(){
	        double t = Math.PI/4;
			public void run(){
		        t = t + 0.1*Math.PI;
		        for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/32){
		                double x = t*Math.cos(theta);
		                double y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
		                double z = t*Math.sin(theta);
		                loc.add(x,y,z);
		                createAndDisplayParticleAtLocation(EnumParticle.FLAME,loc,0f,0f,0f,0f,1);
		                loc.subtract(x,y,z);
		               
		                theta = theta + Math.PI/64;
		               
		                x = t*Math.cos(theta);
		                y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
		                z = t*Math.sin(theta);
		                loc.add(x,y,z);
		                createAndDisplayParticleAtLocation(EnumParticle.CLOUD,loc,0f,0f,0f,0f,1);
		                loc.subtract(x,y,z);
		        }
		        if (t > 5){
                    this.cancel();
		        }
			}
		}.runTaskTimer(Main.plugin, 0, 1);
	}
	
	
	private static void createAndDisplayParticleAtLocation(EnumParticle particle, Location loc, float xOffset, float yOffset, float zOffset, float speed, int amount) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
        				particle,    					// particle type.
                        true,                           // true
                        (float)loc.getX(),              //X
                        (float)loc.getY(),     			//Y
                        (float)loc.getZ(),     			//Z
                        xOffset,                        // x offset
                        yOffset,                        // y offset
                        zOffset,                        // z offset
                        speed,                          // speed
                        amount,                         // number of particles
                        null
        );
       
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
        	((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
	}
	
}
