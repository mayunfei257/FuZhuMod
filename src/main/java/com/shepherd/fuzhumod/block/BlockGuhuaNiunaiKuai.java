package com.shepherd.fuzhumod.block;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.Config;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockGuhuaNiunaiKuai extends Block{

	public BlockGuhuaNiunaiKuai() {
		super(Material.ice);
        setHardness(1f);
        setResistance(3.0f);
        setStepSound(Block.soundTypeSnow);
        setBlockName("blockGuhuaNiunaiKuai");
        setBlockTextureName(Config.MODID + ":blockguhuaniunaikuai");
		setCreativeTab(BaseControl.fuZhuTab);
	}

}
