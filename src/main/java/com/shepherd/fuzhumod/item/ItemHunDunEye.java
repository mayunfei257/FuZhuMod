package com.shepherd.fuzhumod.item;

import java.util.List;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.block.BlockHunDunPortal;
import com.shepherd.fuzhumod.util.SkillEntityPlayer;
import com.shepherd.fuzhumod.world.Test;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class ItemHunDunEye extends Item {
	public ItemHunDunEye() {
		super();
		setMaxStackSize(1);
		setMaxDamage(1024);
		setUnlocalizedName("itemHunDunEye");
		setTextureName(Config.MODID + ":itemhunduneye");
		setCreativeTab(BaseControl.fuZhuTab);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player){
		if(null == itemStack || itemStack.stackSize <= 0 || null == itemStack.getItem()) return itemStack;
		if(!world.isRemote) {
			if(!player.isSneaking()) {
				
			}else {
				SkillEntityPlayer.removeEffectSkill(player);
			}
		}
		return itemStack;
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(null == itemStack || itemStack.stackSize <= 0 || null == itemStack.getItem()) return super.onItemUse(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
		
		if(world.getBlock(x, y, z) != BaseControl.blockHunDunCrystal) {
			if(!world.isRemote) {
				if(!player.isSneaking()) {
					SkillEntityPlayer.teleportUpSkill(player, x, y, z);
				}else{
					SkillEntityPlayer.teleportDownSkill(player, x, y, z);
				}
			}
		}else {
			switch(side) {
			case 0: y--; break;
			case 1: y++; break;
			case 2: z--; break;
			case 3: z++; break;
			case 4: x--; break;
			case 5: x++; break;
			}
			if (player.canPlayerEdit(x, y, z, side, itemStack) && world.getBlock(x, y, z) == Blocks.air) {
				world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				((BlockHunDunPortal)BaseControl.blockHunDunPortal).tryToCreatePortal(world, x, y, z);
				itemStack.damageItem(1, player);
			}
		}
		
		return true;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entity){
        if (!world.isRemote){
			if(entity.isSneaking() && block instanceof IPlantable) {
				for(; y >= 0;) {//find the first root block
					Block thisBlock = world.getBlock(x, y, z);
					Block thisBlockDown = world.getBlock(x, y - 1, z);
					if((thisBlock == Blocks.air || thisBlock == block) && (thisBlockDown != block && thisBlockDown != Blocks.air)) {
						dropBlock(world, block, x, y, z);
						break;
					}
					y--;
				}
			}
        }
    	return true;
    }

	private void dropBlock(World world, Block block, int x, int y, int z) {
		for(int i = 1; i >= -1; i--) {
			for(int k = 1; k >= -1; k--) {
				if(i == 0 && k == 0) { continue; }
				if(dropIPlant(world, block, x + i, y, z + k)) {
					dropBlock(world, block, x + i, y, z + k);
				}
			}
		}
	}
	
	private boolean dropIPlant(World world, Block block, int x, int y, int z) {
		if(world.getBlock(x, y, z) != block) { return false; }
		if(world.getBlock(x, y - 1, z) == block) {
			for(; y >= 0;) {
				if(world.getBlock(x, y, z) != block) { return false; }
				if(world.getBlock(x, y - 1, z) != block) {
					break;
				}
				y--;
			}
		}
		if(world.getBlock(x, y + 1, z) == block && world.getBlock(x, y, z) == block && world.getBlock(x, y - 1, z) != block) {
			block.dropBlockAsItem(world, x, y + 1, z, 0, 0);
			world.setBlockToAir(x, y + 1, z);
			return true;
		}
		return false;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean showAdvancedInfo) {
		list.add("");
		list.add(I18n.format(Config.MODID + ".itemHunDunEyes.message1", new Object[]{}));
		list.add(I18n.format(Config.MODID + ".itemHunDunEyes.message2", new Object[]{}));
		list.add("");
	}
}
