package com.shepherd.fuzhumod.util;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Predicate;
import com.shepherd.fuzhumod.BaseControl;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class SkillBase {
	protected static final int WORLD_MAX_HIGHT = 512;

	//TODO ------Base Skill Start--------------------
//	protected static EntityArrowBingDan shootBingDanBase(EntityLivingBase thrower, double throwerX, double throwerY, double throwerZ, double vecX, double vecY, double vecZ, float attackDamage, float slownessProbability, int slownessStrength, boolean checkFaction) {
//		EntityArrowBingDan entityDan = new EntityArrowBingDan(thrower.world, thrower, attackDamage, slownessProbability, 80, slownessStrength, checkFaction);
//    	entityDan.setPosition(throwerX, throwerY, throwerZ);
//		entityDan.shoot(vecX, vecY, vecZ, VELOCITY, INACCURACY);
//		thrower.world.spawnEntity(entityDan);
//		thrower.world.playSound((EntityPlayer) null, throwerX, throwerY, throwerZ, SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.snowball.throw")), SoundCategory.NEUTRAL, 1.0F, 1.0F);
//		return entityDan;
//	}
//
//	protected static EntityArrowHuoDan shootHuoDanBase(EntityLivingBase thrower, double throwerX, double throwerY, double throwerZ, double vecX, double vecY, double vecZ, float attackDamage, float explosionProbability, float explosionStrength, boolean canExplosionOnBlock, boolean checkFaction) {
//		EntityArrowHuoDan entityDan = new EntityArrowHuoDan(thrower.world, thrower, attackDamage, explosionProbability, explosionStrength, canExplosionOnBlock, checkFaction);
//    	entityDan.setPosition(throwerX, throwerY, throwerZ);
//		entityDan.shoot(vecX, vecY, vecZ, VELOCITY, INACCURACY);
//		entityDan.setFire(5);
//		thrower.world.spawnEntity(entityDan);
//		thrower.world.playSound((EntityPlayer) null, throwerX, throwerY, throwerZ, SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.snowball.throw")), SoundCategory.NEUTRAL, 1.0F, 1.0F);
//		return entityDan;
//	}
//
//	protected static EntityArrowXukongDan shootXukongDanBase(EntityLivingBase thrower, double throwerX, double throwerY, double throwerZ, double vecX, double vecY, double vecZ, float attackDamage, boolean checkFaction) {
//		EntityArrowXukongDan entityDan = new EntityArrowXukongDan(thrower.world, thrower, attackDamage, checkFaction);
//    	entityDan.setPosition(throwerX, throwerY, throwerZ);
//		entityDan.shoot(vecX, vecY, vecZ, VELOCITY, INACCURACY);
//		thrower.world.spawnEntity(entityDan);
//		thrower.world.playSound((EntityPlayer) null, throwerX, throwerY, throwerZ, SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.snowball.throw")), SoundCategory.NEUTRAL, 1.0F, 1.0F);
//		return entityDan;
//	}
//	
//	protected static EntityArrowFengyinDan shootFengyinDanBase(EntityLivingBase thrower, double throwerX, double throwerY, double throwerZ, double vecX, double vecY, double vecZ, float attackDamage, boolean checkFaction) {
//		EntityArrowFengyinDan entityDan = new EntityArrowFengyinDan(thrower.world, thrower, attackDamage, checkFaction);
//		entityDan.setPosition(throwerX, throwerY, throwerZ);
//		entityDan.shoot(vecX, vecY, vecZ, VELOCITY, INACCURACY);
//		thrower.world.spawnEntity(entityDan);
//		thrower.world.playSound((EntityPlayer) null, throwerX, throwerY, throwerZ, SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.snowball.throw")), SoundCategory.NEUTRAL, 1.0F, 1.0F);
//		return entityDan;
//	}

	protected static void addEffectBase(EntityLivingBase entityLivingBase, int potionID, int duration, int amplifier) {
		entityLivingBase.addPotionEffect(new PotionEffect(potionID, duration, amplifier, false));
		entityLivingBase.worldObj.playSound(entityLivingBase.posX, entityLivingBase.posY + entityLivingBase.getEyeHeight(), entityLivingBase.posZ, "entity.endermen.teleport", 1.0F, 1.0F, false);
	}
	protected static void addEffectBase(List<EntityLivingBase> entityLivingBaseList, int potionID, int duration, int amplifier) {
		for(EntityLivingBase entityLivingBase: entityLivingBaseList) {
			addEffectBase(entityLivingBase, potionID, duration, amplifier);
		}
	}
	protected static void removeEffectBase(EntityLivingBase entityLivingBase) {
		Collection<PotionEffect> potionEffects = entityLivingBase.getActivePotionEffects();
		if(null != potionEffects && potionEffects.size() > 0) {
			for(PotionEffect potionEffect: potionEffects) {
				entityLivingBase.removePotionEffect(potionEffect.getPotionID());
			}
			entityLivingBase.worldObj.playSound(entityLivingBase.posX, entityLivingBase.posY + entityLivingBase.getEyeHeight(), entityLivingBase.posZ, "entity.endermen.teleport", 1.0F, 1.0F, false);
		}
	}
	protected static void removeEffectBase(List<EntityLivingBase> entityLivingBaseList) {
		for(EntityLivingBase entityLivingBase: entityLivingBaseList) {
			removeEffectBase(entityLivingBase);
		}
	}

	protected static boolean ImmuneFallDamage() {
		//---
		return true;
	}

//	protected static void thousandsFrozenBase(EntityLivingBase entity, World world, int centerX, int centerY, int centerZ, int rangeX, int rangeY, int rangeZ, float slownessProbability, int slownessStrength, float attackDamage) {
//		boolean slownessFlag = world.rand.nextFloat() < slownessProbability;
//		for(int i = - rangeX; i <= rangeX; i++) {
//			for(int j = - rangeY; j <= rangeY; j++) {
//				for(int k = - rangeZ; k <= rangeZ; k++) {
//					BlockPos blockPos = new BlockPos(centerX + i, centerY + j, centerZ + k);
//					IBlockState blockState = world.getBlockState(blockPos);
//					if(blockState.getBlock() == Blocks.FIRE || blockState.getBlock() == Blocks.FLOWING_WATER) {
//						world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
//					}else if(blockState.getBlock() == Blocks.WATER) {
//						world.setBlockState(blockPos, Blocks.ICE.getDefaultState());
//					}else if(blockState.getBlock() == Blocks.FLOWING_LAVA) {
//						world.setBlockState(blockPos, Blocks.COBBLESTONE.getDefaultState());
//					}else if((blockState.getBlock() == Blocks.AIR) && world.getBlockState(blockPos.down()).getBlock() != Blocks.AIR) {
//						setBlockStateBase(world, Blocks.SNOW_LAYER.getDefaultState(), blockPos);
//					}
//				}
//			}
//		}
//		attackAreaEntitiesBase(entity, world, EntityLiving.class, new AxisAlignedBB(centerX - rangeX, centerY - rangeY, centerZ - rangeZ, centerX + rangeX, centerY + rangeY, centerZ + rangeZ), IMob.MOB_SELECTOR, attackDamage, slownessFlag ? MobEffects.SLOWNESS : null, 20, slownessStrength);
//		world.playSound((EntityPlayer) null, centerX, centerY + 0.5D, centerZ, SoundEvent.REGISTRY.getObject(new ResourceLocation("block.snow.break")), SoundCategory.NEUTRAL, 1.0F, 1.0F);
//	}
//
//	protected static void firestormBase(EntityLivingBase entity, World world, int centerX, int centerY, int centerZ, int rangeX, int rangeY, int rangeZ, float explosionProbability, float explosionStrength, boolean exceptCenter, float attackDamage) {
//		boolean explosionFlag = world.rand.nextFloat() < explosionProbability;
//		for(int i = - rangeX; i <= rangeX; i++) {
//			for(int j = - rangeY; j <= rangeY; j++) {
//				for(int k = - rangeZ; k <= rangeZ; k++) {
//					if(exceptCenter && i == 0 && j == 0 && k == 0) { continue; }
//					BlockPos blockPos = new BlockPos(centerX + i, centerY + j, centerZ + k);
//					IBlockState blockState = world.getBlockState(blockPos);
//					if((blockState.getBlock() == Blocks.AIR || blockState.getBlock() == Blocks.SNOW_LAYER) && world.getBlockState(blockPos.down()).getBlock() != Blocks.AIR) {
//						if(explosionFlag) {
//							world.createExplosion(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), explosionStrength, true);
//						}else {
//							world.setBlockState(blockPos, Blocks.FIRE.getDefaultState());
//						}
//						world.spawnEntity(new EntityLightningBolt(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), true));
//					}else if(blockState.getBlock() == Blocks.ICE || blockState.getBlock() == Blocks.PACKED_ICE || blockState.getBlock() == Blocks.FROSTED_ICE) {
//						world.setBlockState(blockPos, Blocks.WATER.getDefaultState());
//					}
//				}
//			}
//		}
//		attackAreaEntitiesBase(entity, world, EntityLiving.class, new AxisAlignedBB(centerX - rangeX, centerY - rangeY, centerZ - rangeZ, centerX + rangeX, centerY + rangeY, centerZ + rangeZ), IMob.MOB_SELECTOR, attackDamage, null, 0, 0);
//		world.playSound((EntityPlayer) null, centerX, centerY + 0.5D, centerZ, SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.lightning.impact")), SoundCategory.NEUTRAL, 1.0F, 1.0F);
//		world.playSound((EntityPlayer) null, centerX, centerY + 0.5D, centerZ, SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.lightning.thunder")), SoundCategory.NEUTRAL, 1.0F, 1.0F);
//	}
	
	protected static boolean teleportPointBase(EntityLivingBase entityLivingBase, int x, int y, int z) {
		entityLivingBase.setPositionAndUpdate(x + 0.5D, y, z + 0.5D);
		entityLivingBase.worldObj.playSound(x + 0.5D, y + 0.5D, z + 0.5D, "entity.endermen.teleport", 1.0F, 1.0F, false);
		return true;
	}
	
	protected static boolean teleportBlurPointBase(EntityLivingBase entityLivingBase, int x, int y, int z, int blurRange) {
		World world = entityLivingBase.worldObj;
		if(utilCanStand(world, x, y, z, true, true)) {
			return teleportPointBase(entityLivingBase, x, y, z);
		}else {
			double distends = blurRange * blurRange + blurRange * blurRange + blurRange * blurRange + 1;
			int xTem = x;
			int yTem = y;
			int zTem = z;
			for(int i = - blurRange; i <= blurRange; i++) {
				for(int j = - blurRange; j <= blurRange; j++) {
					for(int k = - blurRange; k <= blurRange; k++) {
						double tem = i * i + j * j + k * k;
						if(tem < distends && y + j > 0 && utilCanStand(world, x + i, y + j, z + k, true, true)) {
							distends = tem;
							xTem = x + i;
							yTem = y + j;
							zTem = z + k;
						}
					}
				}
			}
			Block blockDown = world.getBlock(xTem, yTem - 1, zTem);
			if(blockDown == Blocks.lava || blockDown == Blocks.flowing_lava) {
				world.setBlock(xTem, yTem - 1, zTem, Blocks.stone);
			}
			return teleportPointBase(entityLivingBase, xTem, yTem, zTem);
		}
	}
	
	protected static boolean teleportVerticalBlurPointBase(EntityLivingBase entityLivingBase, int x, int y, int z, int blurRange) {
		boolean isSuccess = teleportBlurPointBase(entityLivingBase, x, y, z, blurRange);
		if(!isSuccess) {
			World world = entityLivingBase.worldObj;
			for(int j = WORLD_MAX_HIGHT; j > 0; j--) {
				for(int i = - blurRange; i <= blurRange; i++) {
					for(int k = - blurRange; k <= blurRange; k++) {
						if(utilCanStand(world, x + i, j, z + k, true, false)) {
							if(world.getBlock(x + i, j - 1, z + k) == Blocks.lava || world.getBlock(x + i, j - 1, z + k) == Blocks.flowing_lava) {
								world.setBlock(x + i, j - 1, z + k, Blocks.stone);
							}
							return teleportPointBase(entityLivingBase, x + i, j, z + k);
						}
					}
				}
			}
			return false;
		}
		return isSuccess;
	}
	
	protected static boolean teleportUpBase(EntityLivingBase entityLivingBase, int x, int y, int z, boolean checkBedRock) {
		World world = entityLivingBase.worldObj;
		for(; y <= WORLD_MAX_HIGHT; y++) {
			if(checkBedRock && world.getBlock(x, y, z) == Blocks.bedrock) { break; }
			if(utilCanStand(world, x, y, z, false, false)) {
				if(world.getBlock(x, y - 1, z) == Blocks.lava || world.getBlock(x, y - 1, z) == Blocks.flowing_lava) {
					world.setBlock(x, y - 1, z, Blocks.stone);
				}
				return teleportPointBase(entityLivingBase, x, y, z);
			}
		}
		return false;
	}

	protected static boolean teleportDownBase(EntityLivingBase entityLivingBase,  int x, int y, int z, boolean checkBedRock) {
		World world = entityLivingBase.worldObj;
		for(; y > 0; y--) {
			if(checkBedRock && world.getBlock(x, y, z) == Blocks.bedrock) { break; }
			if(utilCanStand(world, x, y, z, false, false)) {
				if(world.getBlock(x, y - 1, z) == Blocks.lava || world.getBlock(x, y - 1, z) == Blocks.flowing_lava) {
					world.setBlock(x, y - 1, z, Blocks.stone);
				}
				return teleportPointBase(entityLivingBase, x, y, z);
			}
		}
		return false;
	}
    
//	protected static void growBlockBase(World world, BlockPos pos) {
//		IBlockState iBlockState = world.getBlockState(pos);
//		iBlockState.getBlock().updateTick(world, pos, iBlockState, world.rand);
//		world.playSound((EntityPlayer) null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.endermen.teleport")), SoundCategory.NEUTRAL, 1.0F, 1.0F);
//    }

//    @SideOnly(Side.CLIENT)
//    protected static void spawnParticlesBlockPosBase(BlockPos blockPos, EnumParticleTypes particleType, World world, int particleNum){
//        for (int i = 0; i < particleNum; ++i){
//            double d0 = world.rand.nextGaussian() * 0.02D;
//            double d1 = world.rand.nextGaussian() * 0.02D;
//            double d2 = world.rand.nextGaussian() * 0.02D;
//            double xCoord = blockPos.getX() - 0.5D + (world.rand.nextFloat() * 2.0F);
//            double yCoord = blockPos.getY() + 0.5D + world.rand.nextFloat();
//            double zCoord = blockPos.getZ() - 0.5D + (world.rand.nextFloat() * 2.0F);
//            world.spawnParticle(particleType, xCoord, yCoord, zCoord, d0, d1, d2);
//        }
//    }
//    
//    @SideOnly(Side.CLIENT)
//    protected static void spawnParticlesEntityBase(Entity entity, EnumParticleTypes particleType, World world, int particleNum){
//        for (int i = 0; i < particleNum; ++i){
//            double d0 = world.rand.nextGaussian() * 0.02D;
//            double d1 = world.rand.nextGaussian() * 0.02D;
//            double d2 = world.rand.nextGaussian() * 0.02D;
//            double xCoord = entity.posX + (world.rand.nextFloat() * entity.width * 2.0F) - entity.width;
//            double yCoord = entity.posY + 1.0D + (world.rand.nextFloat() * entity.height);
//            double zCoord = entity.posZ + (world.rand.nextFloat() * entity.width * 2.0F) - entity.width;
//            world.spawnParticle(particleType, xCoord, yCoord, zCoord, d0, d1, d2);
//        }
//    }
    
//    protected static void setBlockStateBase(World world, IBlockState blockState, BlockPos blockPos) {
//    	if(blockState.getBlock().canPlaceBlockAt(world, blockPos)) {
//        	world.setBlockState(blockPos, blockState);
//    	}
//    }
//    
//    @Deprecated
//    protected static void setAreaBlockStateBase(World world, IBlockState blockState, BlockPos centerBlockPos, int radiusX, int radiusY, int radiusZ) {
//    	for(int x = -radiusX; x <= radiusX; x++) {
//    		for(int y = -radiusY; y <= radiusY; y++) {
//    			for(int z = -radiusZ; z <= radiusZ; z++) {
//    				setBlockStateBase(world, blockState, new BlockPos(centerBlockPos.getX() + x, centerBlockPos.getY() + y, centerBlockPos.getZ() + z));
//    			}
//    		}
//    	}
//    }
//    
//    protected static boolean attackAreaEntitiesBase(EntityLivingBase entity, World world, Class <? extends Entity > clazz, AxisAlignedBB boundingBox, Predicate <? super Entity > predicate, float attackDamage, Potion potion, int durationIn, int amplifierIn){
//    	boolean flag = false;
//		List<EntityLiving> entityLivingList = (List<EntityLiving>) getEntitiesBase(world, EntityLiving.class, boundingBox, predicate);
//		if(null != entityLivingList && entityLivingList.size() > 0) {
//			for(EntityLiving entityLiving: entityLivingList) {
//				flag |= entityLiving.attackEntityFrom(DamageSource.causeMobDamage(entity), attackDamage);
//				if(null != potion) {
//					addEffectBase(entityLiving, potion, durationIn, amplifierIn);
//				}
//			}
//		}
//		return flag;
//    }
//    
//    protected static List<?> getEntitiesBase(World world, Class <? extends Entity > clazz, AxisAlignedBB boundingBox, Predicate <? super Entity > predicate){
//    	return world.getEntitiesWithinAABB(clazz, boundingBox, predicate);
//    }

//    protected static EntitySuperSnowman SummonSuperSnowmanBase(World world, BlockPos blockPos, int baseLevel, float yaw, float pitch, int distance) {
//    	EntitySuperSnowman entity = new EntitySuperSnowman(world, baseLevel);
//    	entity.setLocationAndAngles(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D, yaw, pitch);
//    	entity.setHomePosAndDistance(blockPos, distance);
//    	entity.updataSwordDamageAndArmorValue();
//    	entity.playLivingSound();
//    	world.spawnEntity(entity);
//    	world.playSound((EntityPlayer) null, blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D, SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.endermen.teleport")), SoundCategory.NEUTRAL, 1.0F, 1.0F);
//    	return entity;
//    }
//
//    protected static EntitySuperIronGolem summonSuperIronGolemBase(World world, BlockPos blockPos, int baseLevel, float yaw, float pitch, int distance) {
//    	EntitySuperIronGolem entity = new EntitySuperIronGolem(world, 20);
//    	entity.setLocationAndAngles(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D, yaw, pitch);
//    	entity.setHomePosAndDistance(blockPos, distance);
//    	entity.updataSwordDamageAndArmorValue();
//    	entity.playLivingSound();
//    	world.spawnEntity(entity);
//    	world.playSound((EntityPlayer) null, blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D, SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.endermen.teleport")), SoundCategory.NEUTRAL, 1.0F, 1.0F);
//    	return entity;
//    }
//
//    protected static EntityDisciple summonDiscipleBase(World world, BlockPos blockPos, int baseLevel, EnumGender gender, float yaw, float pitch, int distance) {
//    	EntityDisciple entity = new EntityDisciple(world, baseLevel, gender.getType());
//    	entity.setLocationAndAngles(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D, yaw, pitch);
//    	entity.setHomePosAndDistance(blockPos, distance);
//    	entity.updataSwordDamageAndArmorValue();
//    	world.spawnEntity(entity);
//    	world.playSound((EntityPlayer) null, blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D, SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.endermen.teleport")), SoundCategory.NEUTRAL, 1.0F, 1.0F);
//    	return entity;
//    }
//
//    protected static EntityEvilTaoist summonEvilTaoistBase(World world, BlockPos blockPos, int baseLevel, float yaw, float pitch, int distance) {
//    	EntityEvilTaoist entity = new EntityEvilTaoist(world, baseLevel);
//    	entity.setLocationAndAngles(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D, yaw, pitch);
//    	entity.setHomePosAndDistance(blockPos, distance);
//    	entity.updataSwordDamageAndArmorValue();
//    	world.spawnEntity(entity);
//    	world.playSound((EntityPlayer) null, blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D, SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.endermen.teleport")), SoundCategory.NEUTRAL, 1.0F, 1.0F);
//    	return entity;
//    }
	
	protected static int chainDropBlockBase(World world, BlockPos pos, int amount, int maxAmount) {
		for(int y = 1; y >= -1; y--) {
			for(int x = 1; x >= -1; x--) {
				for(int z = 1; z >= -1; z--) {
					if(amount >= maxAmount) { return amount; }
					Block block = world.getBlock(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
					if((x == 0 && y == 0 && z == 0) || block == Blocks.air) {
						continue;
					}else {
						int meta = world.getBlockMetadata(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
						BlockPos thisPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z, world, block, meta);
						if(isSameBlock(thisPos, pos)) {
							block.dropBlockAsItem(world, thisPos.getX(), thisPos.getY(), thisPos.getZ(), meta, 1);
							world.setBlockToAir(thisPos.getX(), thisPos.getY(), thisPos.getZ());
							amount = chainDropBlockBase(world, thisPos, ++amount, maxAmount);
						}
					}
				}
			}
		}
		return amount;
	}
	
	protected static int chainDropBlockBySameYBase(World world, BlockPos pos, int amount, int maxAmount) {
		for(int x = 1; x >= -1; x--) {
			for(int z = 1; z >= -1; z--) {
				if(amount >= maxAmount) { return amount; }
				Block block = world.getBlock(pos.getX() + x, pos.getY(), pos.getZ() + z);
				if((x == 0 && z == 0) || block == Blocks.air) {
					continue;
				}else {
					int meta = world.getBlockMetadata(pos.getX() + x, pos.getY(), pos.getZ() + z);
					BlockPos thisPos = new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z, world, block, meta);
					if(isSameBlock(thisPos, pos)) {
						block.dropBlockAsItem(world, thisPos.getX(), thisPos.getY(), thisPos.getZ(), meta, 1);
						world.setBlockToAir(thisPos.getX(), thisPos.getY(), thisPos.getZ());
						amount = chainDropBlockBase(world, thisPos, ++amount, maxAmount);
					}
				}
			}
		}
		return amount;
	}
	//------Base Skill End--------------------
	

	//TODO------Base Skill Util--------------------
    private static boolean utilCanStand(World world, int x, int y, int z, boolean canWater, boolean canPortal) {
		if((world.getBlock(x, y, z) == Blocks.air
				|| world.getBlock(x, y, z) == Blocks.portal
				|| world.getBlock(x, y, z) == Blocks.water
				|| world.getBlock(x, y, z) == Blocks.flowing_water
			)&&(world.getBlock(x, y + 1, z) == Blocks.air
				|| world.getBlock(x, y + 1, z) == Blocks.portal
				|| world.getBlock(x, y + 1, z) == Blocks.water
				|| world.getBlock(x, y + 1, z) == Blocks.flowing_water
			)&& world.getBlock(x, y - 1, z) != Blocks.air) {
			return true;
		}
		return false;
	}
    
    private static boolean isSameBlock(BlockPos thisBlockPos, BlockPos baseBlockPos) {
    	if(baseBlockPos.getBlock() == thisBlockPos.getBlock() && baseBlockPos.getMeta() == thisBlockPos.getMeta()) {
    		return true;
		}else if(BaseControl.blockHunDunCao == baseBlockPos.getBlock() && BaseControl.blockHunDunCao == thisBlockPos.getBlock()) {
			return true;
		}else if(Blocks.pumpkin == baseBlockPos.getBlock() && Blocks.pumpkin == thisBlockPos.getBlock()) {
			return true;
		}else if((Blocks.lit_redstone_ore == baseBlockPos.getBlock() || Blocks.redstone_ore == baseBlockPos.getBlock()) && (Blocks.lit_redstone_ore == thisBlockPos.getBlock() || Blocks.redstone_ore == thisBlockPos.getBlock())) {
			return true;
		}else if(Blocks.log == baseBlockPos.getBlock() && Blocks.log == thisBlockPos.getBlock()) {
			int baseMeta = baseBlockPos.getMeta();
			int thisMeta = thisBlockPos.getMeta();
			int baseBlockPlanksType = (baseMeta & 3) % 4;
			int thisBlockPlanksType = (thisMeta & 3) % 4;
			return baseBlockPlanksType == thisBlockPlanksType;
		}else if(Blocks.log2 == baseBlockPos.getBlock() && Blocks.log2 == thisBlockPos.getBlock()) {
			int baseMeta = baseBlockPos.getMeta();
			int thisMeta = thisBlockPos.getMeta();
			int baseBlockPlanksType = (baseMeta & 3) % 4;
			int thisBlockPlanksType = (thisMeta & 3) % 4;
			return baseBlockPlanksType == thisBlockPlanksType;
		}
    	return false;
    }
}
