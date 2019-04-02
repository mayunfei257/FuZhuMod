package com.shepherd.fuzhumod.block;

import com.shepherd.fuzhumod.BaseControl;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBaseStairs extends BlockStairs{
	protected final Block baseBlock;
	protected final int meta;

	public BlockBaseStairs(Block block, int meta) {
		super(block, meta);
		this.baseBlock = block;
		this.meta = meta;
		setCreativeTab(BaseControl.fuZhuTab);
	}

	public Block getBlock() {
		return this.baseBlock;
	}
	
	public int getMeta() {
		return this.meta;
	}
}
