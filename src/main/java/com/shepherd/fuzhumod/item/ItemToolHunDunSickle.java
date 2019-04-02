package com.shepherd.fuzhumod.item;

import java.util.HashSet;
import java.util.List;

import com.shepherd.fuzhumod.base.Config;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class ItemToolHunDunSickle extends ItemBaseTool{
	
	public ItemToolHunDunSickle() {
		super(0, ToolMaterialS.HUNDUN, new HashSet(), ToolType.Sickle);
		setUnlocalizedName("itemToolHunDunSickle");
		setTextureName(Config.MODID + ":itemtoolhundunsickle");
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
    
	@Override
    @SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean showAdvancedInfo) {
		super.addInformation(itemStack, entityPlayer, list, showAdvancedInfo);
		list.add(I18n.format(Config.MODID + ".itemToolHunDunSickle.message1", new Object[]{}));
		list.add(I18n.format(Config.MODID + ".itemToolHunDunSickle.message2", new Object[]{}));
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
