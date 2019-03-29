package com.shepherd.fuzhumod.item;

import java.util.List;

import com.shepherd.fuzhumod.block.BlockSlabBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class ItemBlockSlabBase extends ItemSlab{
	protected final Block block;

	public ItemBlockSlabBase(Block block, BlockSlabBase blockSlab, BlockSlabBase doubleBlockSlab, Boolean isDouble) {
		super(block, blockSlab, doubleBlockSlab, isDouble);
		this.block = block;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean showAdvancedInfo) {
		
	}
}
