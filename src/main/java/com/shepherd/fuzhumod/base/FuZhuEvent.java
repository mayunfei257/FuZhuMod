package com.shepherd.fuzhumod.base;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class FuZhuEvent {
	
	@SubscribeEvent
	public void itemTooltipEvent(ItemTooltipEvent event) {
		final EntityPlayer player = event.entityPlayer;
		final ItemStack itemStack = event.itemStack;
		if (player.worldObj.isRemote && itemStack.hasTagCompound()) {
			NBTTagCompound stackTagCompound = itemStack.getTagCompound();
			String type = stackTagCompound.getString(Config.NBTTAG_TYPE);
			int level = stackTagCompound.getInteger(Config.NBTTAG_LEVEL);
			float strength = stackTagCompound.getFloat(Config.NBTTAG_STRENGTH);
			DecimalFormat df = new DecimalFormat("#0.0");
			if(Config.NBTTAG_TYPE_ATTACK.equals(type) && level > 0) {
				event.toolTip.add(I18n.format(Config.MODID + ".tooltipEvent.attack1", new Object[]{level}));
				event.toolTip.add(I18n.format(Config.MODID + ".tooltipEvent.attack2", new Object[]{df.format(strength)}));
			}else if(Config.NBTTAG_TYPE_DEFENSE.equals(type) && level > 0) {
				event.toolTip.add(I18n.format(Config.MODID + ".tooltipEvent.defense1", new Object[]{level}));
				event.toolTip.add(I18n.format(Config.MODID + ".tooltipEvent.defense2", new Object[]{df.format(strength)}));
			}
		}
	}

//	@SubscribeEvent
//	public void livingAttackEvent(LivingAttackEvent event) {
//		DamageSource source = event.source;
//		if(source instanceof EntityDamageSource && ((EntityDamageSource)source).getEntity() instanceof EntityPlayer) {
//			EntityPlayer player = (EntityPlayer)(((EntityDamageSource)source).getEntity());
//			if (!player.worldObj.isRemote) {
//				ItemStack itemStack = player.getHeldItem();
//				if(null != itemStack && itemStack.hasTagCompound()) {
//					NBTTagCompound stackTagCompound = itemStack.getTagCompound();
//					String type = stackTagCompound.getString(Config.NBTTAG_TYPE);
//					float strength = stackTagCompound.getFloat(Config.NBTTAG_STRENGTH);
//					if(Config.NBTTAG_TYPE_ATTACK.equals(type)) {
//						System.out.println("LivingAttackEvent.ammount = " + event.ammount);//
//						try {
//							Field field = null;
//							for(Field f : event.getClass().getFields()){//event.ammount is public
//								if("float".equals(f.getType().toString()))
//									field = f;
//							}
//							Field modifiersField = Field.class.getDeclaredField("modifiers");
//							modifiersField.setAccessible(true);
//							modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
//							field.set(event, event.ammount + strength);
//						}catch(Exception e) {
//							e.printStackTrace();
//						}
//						System.out.println("LivingAttackEvent.ammount = " + event.ammount);//
//					}
//				}
//			}
//		}
//	}

	@SubscribeEvent
	public void livingHurtEvent(LivingHurtEvent event) {
		DamageSource source = event.source;
		if(null != source && source instanceof EntityDamageSource && ((EntityDamageSource)source).getEntity() instanceof EntityLivingBase) {
			EntityLivingBase entityLivingBase = (EntityLivingBase)(((EntityDamageSource)source).getEntity());
			if (!entityLivingBase.worldObj.isRemote && null != entityLivingBase.getHeldItem() && entityLivingBase.getHeldItem().hasTagCompound()) {
				NBTTagCompound stackTagCompound = entityLivingBase.getHeldItem().getTagCompound();
				String type = stackTagCompound.getString(Config.NBTTAG_TYPE);
				float strength = stackTagCompound.getFloat(Config.NBTTAG_STRENGTH);
				if(Config.NBTTAG_TYPE_ATTACK.equals(type)) {
					event.ammount += strength;
				}
			}
		}
		//
		EntityLivingBase entity = event.entityLiving;
		if (null != entity && !entity.worldObj.isRemote && event.ammount > 0 && entity instanceof EntityLivingBase) {
			EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
			float totalStrength = 0F;
			for(int index = 1; index <= 4; index++) {
				ItemStack itemStack = entityLivingBase.getEquipmentInSlot(index);
				if(null != itemStack && itemStack.stackSize > 0 && itemStack.hasTagCompound()) {
					NBTTagCompound stackTagCompound = itemStack.getTagCompound();
					String type = stackTagCompound.getString(Config.NBTTAG_TYPE);
					if(Config.NBTTAG_TYPE_DEFENSE.equals(type)) {
						totalStrength += stackTagCompound.getFloat(Config.NBTTAG_STRENGTH);
					}
				}
			}
			if(totalStrength > 0) {
				float reduce = Math.min(totalStrength, event.ammount);
				event.ammount = event.ammount - reduce;
				if(entityLivingBase instanceof EntityPlayer) {
					((EntityPlayer)entityLivingBase).addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".livingHurtEvent.ammount", new Object[] {new DecimalFormat("#0.0").format(reduce)})));
				}
			}
		}
	}
	
}
