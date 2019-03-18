package com.shepherd.fuzhumod.base;

import com.shepherd.fuzhumod.BaseControl;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FuZhuTab extends CreativeTabs{

	public FuZhuTab() {
		super(Config.MODTAB);
	}

	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return BaseControl.itemHunDunCrystal;
	}
}