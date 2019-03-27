package com.shepherd.fuzhumod.block;

import java.util.List;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.type.CrystalBlockType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockHunDunCrystal extends Block implements CrystalBlockType{

	public BlockHunDunCrystal() {
		super(Material.iron);
		setHardness(50f);
		setResistance(5000.0f);
		setLightLevel(0.7f);
		setHarvestLevel("pickaxe", 0);
		setStepSound(Block.soundTypeMetal);
		setBlockName("blockHunDunCrystal");
		setBlockTextureName(Config.MODID + ":blockhunduncrystal");
		setCreativeTab(BaseControl.fuZhuTab);
	}
}
