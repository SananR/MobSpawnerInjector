package com.codex.mobspawnerinjector.util;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.google.common.collect.Lists;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMinecartAbstract;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.MobSpawnerAbstract;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import net.minecraft.server.v1_8_R3.UtilColor;
import net.minecraft.server.v1_8_R3.WeightedRandom;

public abstract class CustomMobSpawner extends MobSpawnerAbstract {

	public int spawnDelay = 20;
	private String mobName = "Zombie";
	private final List<a> mobs = Lists.newArrayList();
	private a spawnData;
	private double e;
	private double f;
	private int minSpawnDelay = 200;
	private int maxSpawnDelay = 800;
	private int spawnCount = 4;
	private Entity j;
	private int maxNearbyEntities = 6;
	private int requiredPlayerRange = 32;
	private int spawnRange = 4;

	  public String getMobName()
	  {
	    if (i() == null)
	    {
	      if (this.mobName == null) {
	        this.mobName = "Pig";
	      }
	      if ((this.mobName != null) && (this.mobName.equals("Minecart"))) {
	        this.mobName = "MinecartRideable";
	      }
	      return this.mobName;
	    }
	    return i().d;
	  }
	  
	  //If player in range
	public boolean g(){
		BlockPosition blockposition = b();
		return a().isPlayerNearby(blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D, this.requiredPlayerRange);
	}

	//ALWAYS RUNNING (TICK)
	@Override
	  public void c() {
	    if (g()) {
	      BlockPosition blockposition = b();
	      if (a().isClientSide)
	      {
	          double d1 = blockposition.getX() + a().random.nextFloat();
	          double d2 = blockposition.getY() + a().random.nextFloat();
	          
	          double d0 = blockposition.getZ() + a().random.nextFloat();
	          a().addParticle(EnumParticle.SMOKE_NORMAL, d1, d2, d0, 0.0D, 0.0D, 0.0D, new int[0]);
	          a().addParticle(EnumParticle.FLAME, d1, d2, d0, 0.0D, 0.0D, 0.0D, new int[0]);
	          if (this.spawnDelay > 0) {
	            this.spawnDelay -= 1;
	          }
	          this.f = this.e;
	          this.e = ((this.e + 1000.0F / (this.spawnDelay + 200.0F)) % 360.0D);
	      }
	      else
	      {
	          double d1 = blockposition.getX() + a().random.nextFloat();
	          double d2 = blockposition.getY() + a().random.nextFloat();
	          
	          double d0 = blockposition.getZ() + a().random.nextFloat();
	          a().addParticle(EnumParticle.SMOKE_NORMAL, d1, d2, d0, 0.0D, 0.0D, 0.0D, new int[0]);
	          a().addParticle(EnumParticle.FLAME, d1, d2, d0, 0.0D, 0.0D, 0.0D, new int[0]);
	          if (this.spawnDelay > 0) {
	            this.spawnDelay -= 1;
	          }
	          this.f = this.e;
	          this.e = ((this.e + 1000.0F / (this.spawnDelay + 200.0F)) % 360.0D);
	        if (this.spawnDelay == -1) {
	          h();
	        }
	        if (this.spawnDelay > 0)
	        {
	          this.spawnDelay -= 1;
	          return;
	        }
	        boolean flag = false;
	        for (int i = 0; i < this.spawnCount; i++)
	        {
	          Entity entity = EntityTypes.createEntityByName(getMobName(), a());
	          if (entity == null) {
	            return;
	          }
	          int j = a().a(entity.getClass(), new AxisAlignedBB(blockposition.getX(), blockposition.getY(), blockposition.getZ(), blockposition.getX() + 1, blockposition.getY() + 1, blockposition.getZ() + 1).grow(this.spawnRange, this.spawnRange, this.spawnRange)).size();
	          if (j >= this.maxNearbyEntities)
	          {
	            h();
	            return;
	          }
	          double d01 = blockposition.getX() + (a().random.nextDouble() - a().random.nextDouble()) * this.spawnRange + 0.5D;
	          double d3 = blockposition.getY() + a().random.nextInt(3) - 1;
	          double d4 = blockposition.getZ() + (a().random.nextDouble() - a().random.nextDouble()) * this.spawnRange + 0.5D;
	          EntityInsentient entityinsentient = (entity instanceof EntityInsentient) ? (EntityInsentient)entity : null;
	          
	          entity.setPositionRotation(d01, d3, d4, a().random.nextFloat() * 360.0F, 0.0F);
	          if ((entityinsentient == null) || ((entityinsentient.bR()) && (entityinsentient.canSpawn()))) {
	        	  //spawns entity
	            a(entity, true);

		        NBTTagCompound entityNbtTag = entity.getNBTTag();
		        String entityCustomName = entityNbtTag.getCompound("MobDisplayName").toString();
		        entity.setCustomName(entityCustomName);
		        entity.setCustomNameVisible(true);
		          
	            //Particle effects ON SPAWN
	            
	            Location loc = new Location(entity.getWorld().getWorld(), (double)blockposition.getX(), (double)blockposition.getY(), (double)blockposition.getZ());
	            FireworkUtil.spawnRandomFirework(loc);
	            
	            //a().triggerEffect(2004, blockposition, 0);
	            if (entityinsentient != null) {
	              //entityinsentient.y();
	            }
	            flag = true;
	          }
	        }
	        if (flag) {
	          h();
	        }
	      }
	    }
	  }

	  private void h()
	  {
	    if (this.maxSpawnDelay <= this.minSpawnDelay)
	    {
	      this.spawnDelay = this.minSpawnDelay;
	    }
	    else
	    {
	      int i = this.maxSpawnDelay - this.minSpawnDelay;
	      
	      this.spawnDelay = (this.minSpawnDelay + a().random.nextInt(i));
	    }
	    if (this.mobs.size() > 0) {
	      this.a((a)WeightedRandom.a(a().random, this.mobs));
	    }
	    a(1);
	  }
	  
	  private Entity a(Entity entity, boolean flag) {
	    if (i() != null) {
	      NBTTagCompound nbttagcompound = new NBTTagCompound();
	      
	      entity.d(nbttagcompound);
	      Iterator iterator = i().c.c().iterator();
	      while (iterator.hasNext()) {
	        String s = (String)iterator.next();
	        NBTBase nbtbase = i().c.get(s);
	        
	        nbttagcompound.set(s, nbtbase.clone());
	      }
	      
	      NBTBase nameBase = new NBTTagString("§3§lMobby McMobface");
	      nbttagcompound.set("MobDisplayName", nameBase);

          NBTTagCompound entityNbtTag = entity.getNBTTag();
          String entityCustomName = entityNbtTag.getCompound("MobDisplayName").toString();
          entity.setCustomName(entityCustomName);
          entity.setCustomNameVisible(true);
          
	      entity.f(nbttagcompound);
	      if ((entity.world != null) && (flag)) {
	        entity.world.addEntity(entity, CreatureSpawnEvent.SpawnReason.SPAWNER);
	      }
	      NBTTagCompound nbttagcompound1;
	      
	      //Riding entities (Jockey)
	      for (Entity entity1 = entity; nbttagcompound.hasKeyOfType("Riding", 10); nbttagcompound = nbttagcompound1)
	      {
	        nbttagcompound1 = nbttagcompound.getCompound("Riding");
	        Entity entity2 = EntityTypes.createEntityByName(nbttagcompound1.getString("id"), entity.world);
	        if (entity2 != null)
	        {
	          NBTTagCompound nbttagcompound2 = new NBTTagCompound();
	          
	          entity2.d(nbttagcompound2);
	          Iterator iterator1 = nbttagcompound1.c().iterator();
	          while (iterator1.hasNext())
	          {
	            String s1 = (String)iterator1.next();
	            NBTBase nbtbase1 = nbttagcompound1.get(s1);
	            
	            nbttagcompound2.set(s1, nbtbase1.clone());
	          }
	          entity2.f(nbttagcompound2);
	          entity2.setPositionRotation(entity1.locX, entity1.locY, entity1.locZ, entity1.yaw, entity1.pitch);
	          if ((entity.world != null) && (flag)) {
	            entity.world.addEntity(entity2, CreatureSpawnEvent.SpawnReason.SPAWNER);
	          }
	          entity1.mount(entity2);
	        }
	        entity1 = entity2;
	      }
	    }
	    else if (((entity instanceof EntityLiving)) && (entity.world != null) && (flag))
	    {
	      if ((entity instanceof EntityInsentient)) {
	        ((EntityInsentient)entity).prepare(entity.world.E(new BlockPosition(entity)), null);
	      }
	      entity.world.addEntity(entity, CreatureSpawnEvent.SpawnReason.SPAWNER);
	    }
	    return entity;
	  }
	  
	  
	public void b(NBTTagCompound nbttagcompound){
	    String s = getMobName();
	    if (!UtilColor.b(s)) {
	      nbttagcompound.setString("EntityId", mobName);
	      nbttagcompound.setShort("Delay", (short)this.spawnDelay);
	      nbttagcompound.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
	      nbttagcompound.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
	      nbttagcompound.setShort("SpawnCount", (short)this.spawnCount);
	      nbttagcompound.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
	      nbttagcompound.setShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
	      nbttagcompound.setShort("SpawnRange", (short)this.spawnRange);
	      if (i() != null) {
	        nbttagcompound.set("SpawnData", i().c.clone());
	      }
	      if ((i() != null) || (this.mobs.size() > 0))
	      {
	        NBTTagList nbttaglist = new NBTTagList();
	        if (this.mobs.size() > 0)
	        {
	          Iterator iterator = this.mobs.iterator();
	          while (iterator.hasNext())
	          {
	            a mobspawnerabstract_a = (a)iterator.next();
	            
	            nbttaglist.add(mobspawnerabstract_a.a());
	          }
	        }
	        else
	        {
	          nbttaglist.add(i().a());
	        }
	        nbttagcompound.set("SpawnPotentials", nbttaglist);
	      }
	    }
	  }

	public class a
    extends WeightedRandom.WeightedRandomChoice
  {
    private final NBTTagCompound c;
    private final String d;
    
    public a(NBTTagCompound nbttagcompound)
    {
      this(nbttagcompound.getCompound("Properties"), nbttagcompound.getString("Type"), nbttagcompound.getInt("Weight"));
    }
    
    public a(NBTTagCompound nbttagcompound, String s)
    {
      this(nbttagcompound, s, 1);
    }
    
    private a(NBTTagCompound nbttagcompound, String s, int i)
    {
      super(i);
      if (s.equals("Minecart")) {
        if (nbttagcompound != null) {
          s = EntityMinecartAbstract.EnumMinecartType.a(nbttagcompound.getInt("Type")).b();
        } else {
          s = "MinecartRideable";
        }
      }
      this.c = nbttagcompound;
      this.d = s;
    }
    
    public NBTTagCompound a()
    {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      
      nbttagcompound.set("Properties", this.c);
      nbttagcompound.setString("Type", this.d);
      nbttagcompound.setInt("Weight", this.a);
      return nbttagcompound;
    }
  }

	  public void a(a mobspawnerabstract_a)
	  {
	    this.spawnData = mobspawnerabstract_a;
	  }
	  
	  private a i() {
	    return this.spawnData;
	  }
}
