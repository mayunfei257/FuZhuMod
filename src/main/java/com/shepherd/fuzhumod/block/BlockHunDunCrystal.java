package com.shepherd.fuzhumod.block;

import java.util.List;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockHunDunCrystal extends Block{

	public BlockHunDunCrystal() {
		super(Material.iron);
		setHardness(50f);
		setResistance(1000.0f);
		setLightLevel(0.5f);
		setHarvestLevel("pickaxe", 0);
		setStepSound(Block.soundTypeMetal);
		setBlockName("blockHunDunCrystal");
		setBlockTextureName(Config.MODID + ":blockhunduncrystal");
		setCreativeTab(BaseControl.fuZhuTab);
	}

	//ItemBlock
	public static class ItemBlockHunDunCrystal extends ItemBlock{

		public ItemBlockHunDunCrystal(Block blockHunDunCrystal) {
			super(blockHunDunCrystal);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
			list.add(I18n.format(Config.MODID + ".blockHunDunCrystal.message1", new Object[]{}));
			list.add(I18n.format(Config.MODID + ".blockHunDunCrystal.message2", new Object[]{}));
			list.add("");
		}
	}
}
