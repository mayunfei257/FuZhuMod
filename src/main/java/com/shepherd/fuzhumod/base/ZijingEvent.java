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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ZijingEvent {
	
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

	@SubscribeEvent
	public void livingAttackEvent(LivingAttackEvent event) {
//		EntityLivingBase entity = event.entityLiving;
//		if (!entity.worldObj.isRemote && entity instanceof EntityPlayer) {
//			EntityPlayer player = (EntityPlayer)entity;
//			ItemStack itemStack = player.getHeldItem();
//			if(null != itemStack && itemStack.hasTagCompound()) {
//				NBTTagCompound stackTagCompound = itemStack.getTagCompound();
//				String type = stackTagCompound.getString(Config.NBTTAG_TYPE);
//				float strength = stackTagCompound.getFloat(Config.NBTTAG_STRENGTH);
//				if(Config.NBTTAG_TYPE_ATTACK.equals(type)) {
//					System.out.println("LivingAttackEvent.ammount = " + event.ammount);//
//					try {
//						Field field = null;
//						for(Field f : event.getClass().getFields()){//event.ammount is public
//							if("float".equals(f.getType().toString()))
//								field = f;
//						}
//						Field modifiersField = Field.class.getDeclaredField("modifiers");
//						modifiersField.setAccessible(true);
//						modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
//						field.set(event, event.ammount + strength);
//					}catch(Exception e) {
//						e.printStackTrace();
//					}
//					System.out.println("LivingAttackEvent.ammount = " + event.ammount);//
//				}
//			}
//		}
	}

	@SubscribeEvent
	public void livingHurtEvent(LivingHurtEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (!entity.worldObj.isRemote && event.ammount > 0 && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			float totalstrength = 0F;
			for(ItemStack itemStack: player.inventory.armorInventory) {
				if(null != itemStack && itemStack.stackSize > 0 && itemStack.hasTagCompound()) {
					NBTTagCompound stackTagCompound = itemStack.getTagCompound();
					String type = stackTagCompound.getString(Config.NBTTAG_TYPE);
					if(Config.NBTTAG_TYPE_DEFENSE.equals(type)) {
						totalstrength += stackTagCompound.getFloat(Config.NBTTAG_STRENGTH);
					}
				}
			}
			event.ammount = Math.max(event.ammount - totalstrength, 0);
			DecimalFormat df = new DecimalFormat("#0.0");
			player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".livingHurtEvent.ammount", new Object[] {df.format(totalstrength)})));
		}
	}
	
}
