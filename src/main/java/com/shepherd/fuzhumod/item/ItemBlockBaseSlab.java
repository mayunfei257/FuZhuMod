package com.shepherd.fuzhumod.item;

import java.util.List;

import com.shepherd.fuzhumod.block.BlockBaseSlab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class ItemBlockBaseSlab extends ItemSlab{
	protected final Block block;

	public ItemBlockBaseSlab(Block block, BlockBaseSlab blockSlab, BlockBaseSlab doubleBlockSlab, Boolean isDouble) {
		super(block, blockSlab, doubleBlockSlab, isDouble);
		this.block = block;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean showAdvancedInfo) {
		
	}
}
