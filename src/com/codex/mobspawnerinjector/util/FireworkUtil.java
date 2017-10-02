package com.codex.mobspawnerinjector.util;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkUtil {

	public static void spawnRandomFirework(Location loc) {
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        
        Random r = new Random();
        
        Type type = Type.STAR;       
        
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.AQUA).withFade(Color.BLUE).with(type).trail(r.nextBoolean()).build();
        
        fwm.addEffect(effect);
        
        fwm.setPower(3);
        
        fw.setFireworkMeta(fwm);
	}
}
