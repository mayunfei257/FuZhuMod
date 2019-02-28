package com.shepherd.fuzhumod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FuZhuTab extends CreativeTabs{
	//CreativeTab
	public static final CreativeTabs fuZhuTab = new FuZhuTab();

	public FuZhuTab() {
		super("fuZhuTab");
	}

	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return BaseControl.itemGuhuaNiunai;
	}
}