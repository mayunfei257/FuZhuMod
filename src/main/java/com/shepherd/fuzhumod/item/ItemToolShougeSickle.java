package com.shepherd.fuzhumod.item;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.FuZhuMod;
import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.util.SkillEntityPlayer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class ItemToolShougeSickle extends ItemTool{
	private static final Set<Block> blockSet = Sets.newHashSet(new Block[] {Blocks.web, Blocks.pumpkin, Blocks.melon_block});
	
	public ItemToolShougeSickle() {
		super(0, FuZhuMod.config.getHunDunToolMaterial(), blockSet);
		this.setUnlocalizedName("itemToolShougeSickle");
		this.setTextureName(Config.MODID + ":itemtoolshougesickle");
		this.setHarvestLevel("pickaxe", FuZhuMod.config.getHunDunToolMaterial().getHarvestLevel());
		this.setHarvestLevel("axe", FuZhuMod.config.getHunDunToolMaterial().getHarvestLevel());
		this.setHarvestLevel("shovel", FuZhuMod.config.getHunDunToolMaterial().getHarvestLevel());
        this.setCreativeTab(BaseControl.fuZhuTab);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player){
		if(null == itemStack || itemStack.stackSize <= 0 || null == itemStack.getItem()) return itemStack;
		if(!world.isRemote) {
			if(!player.isSneaking()) {
				
			}else {
				
			}
		}
		return itemStack;
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(null == itemStack || itemStack.stackSize <= 0 || null == itemStack.getItem()) return super.onItemUse(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
		if(!world.isRemote) {
			if(!player.isSneaking()) {
				
			}else{
				
			}
		}
		return true;
	}

	@Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase entityLivingBase, EntityLivingBase player){
        itemStack.damageItem(1, player);
        return true;
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entity){
        if (!world.isRemote){
			if(entity.isSneaking() && block instanceof IPlantable && entity instanceof EntityPlayer) {
				SkillEntityPlayer.chainDropPlantBlockSkill(itemStack, world, x, y, z, (EntityPlayer)entity);
//				for(; y >= 0;) {//find the first root block
//					Block thisBlock = world.getBlock(x, y, z);
//					Block thisBlockDown = world.getBlock(x, y - 1, z);
//					if((thisBlock == Blocks.air || thisBlock == block) && (thisBlockDown != block && thisBlockDown != Blocks.air)) {
//						dropBlock(world, block, x, y, z);
//						break;
//					}
//					y--;
//				}
		    	return true;
			}else {
				return super.onBlockDestroyed(itemStack, world, block, x, y, z, entity);
			}
        }
    	return false;
    }

	@Override
    public boolean func_150897_b(Block block){
        return true;
    }

	@Override
    public float func_150893_a(ItemStack itemStack, Block block){
		return block instanceof IPlantable ? this.efficiencyOnProperMaterial : 1.0F;
    }
    
	@Override
    @SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean showAdvancedInfo) {
		list.add(I18n.format(Config.MODID + ".itemToolShougeSickle.message1", new Object[]{}));
		list.add(I18n.format(Config.MODID + ".itemToolShougeSickle.message2", new Object[]{}));
	}
	
	// private ---------------------------------------------------------------------------------------------------------------------------------------------------

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
}
