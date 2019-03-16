package com.shepherd.fuzhumod.block;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockFuzhiCrystal extends Block{

	public BlockFuzhiCrystal() {
		super(Material.iron);
        setHardness(50f);
        setResistance(1000.0f);
        setLightLevel(1.0f);
        setHarvestLevel("pickaxe", 0);
        setStepSound(Block.soundTypeMetal);
        setBlockName("blockFuzhiCrystal");
        setBlockTextureName(Config.MODID + ":blockfuzhicrystal");
		setCreativeTab(BaseControl.fuZhuTab);
	}

}
