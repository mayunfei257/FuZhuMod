package com.shepherd.fuzhumod.block;

import java.util.List;
import java.util.Random;

import com.shepherd.fuzhumod.BaseControl;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockSlabBase extends BlockSlab{
	protected final Block baseBlock;
	protected final int meta;

	public BlockSlabBase(Block block, int meta, boolean isDouble) {
		super(isDouble, block.getMaterial());
		this.baseBlock = block;
		this.meta = meta;
		this.setHardness(block.blockHardness);
		this.setResistance(block.blockResistance / 3.0F);
		this.setStepSound(block.stepSound);
		this.setLightOpacity(255);
		setCreativeTab(BaseControl.fuZhuTab);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta){
        return this.baseBlock.getIcon(side, this.meta);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister){
	}

	public String func_150002_b(int meta){
		return super.getUnlocalizedName();
	}
}
