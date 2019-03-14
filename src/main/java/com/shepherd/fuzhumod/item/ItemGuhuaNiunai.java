package com.shepherd.fuzhumod.item;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.Config;

import net.minecraft.item.Item;

public class ItemGuhuaNiunai extends Item{

	public ItemGuhuaNiunai() {
		super();
		maxStackSize = 64;
		setUnlocalizedName("itemGuhuaNiunai");
		setTextureName(Config.MODID + ":itemguhuaniunai");
		setCreativeTab(BaseControl.fuZhuTab);
	}
}
