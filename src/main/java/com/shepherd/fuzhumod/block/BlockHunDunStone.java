package com.shepherd.fuzhumod.block;

import java.util.List;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockHunDunStone extends Block{
    public static final String[] nameStrings = new String[] {"default", "chiseled", "smooth"};
	private static final String[] sideIconStrings = new String[] {"normal", "carved", "smooth"};
	@SideOnly(Side.CLIENT)
	private IIcon[] sideIcons;
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon bottomIcon;

	public BlockHunDunStone(){
		super(Material.rock);
		setHarvestLevel("pickaxe", 0);
		setHardness(6f);
		setResistance(600.0f);
		setStepSound(Block.soundTypeMetal);
		setBlockName("blockHunDunStone");
		setBlockTextureName(Config.MODID + ":blockhundunstone");
		setCreativeTab(BaseControl.fuZhuTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta){
		if (side == 0){
			return this.bottomIcon;
		}else if(side == 1){
			return this.topIcon;
		}else{
			if (meta < 0 || meta >= this.sideIcons.length) meta = 0;
			return this.sideIcons[meta];
		}
	}

	@Override
	public int damageDropped(int damage){
		return damage;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List blockList){
		blockList.add(new ItemStack(item, 1, 0));
		blockList.add(new ItemStack(item, 1, 1));
		blockList.add(new ItemStack(item, 1, 2));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister){
		this.sideIcons = new IIcon[sideIconStrings.length];

		for (int i = 0; i < this.sideIcons.length; ++i){
			this.sideIcons[i] = iconRegister.registerIcon(this.getTextureName() + "_" + sideIconStrings[i]);
		}

		this.topIcon = iconRegister.registerIcon(this.getTextureName() + "_top");
		this.bottomIcon = iconRegister.registerIcon(this.getTextureName() + "_bottom");
	}
}