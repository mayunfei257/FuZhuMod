package com.shepherd.fuzhumod.item;

import java.util.List;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemFuZhiCrystal extends ItemCrystal{

	public ItemFuZhiCrystal() {
		super();
		setMaxStackSize(64);
		setUnlocalizedName("itemFuZhiCrystal");
		setTextureName(Config.MODID + ":itemfuzhicrystal");
		setCreativeTab(BaseControl.fuZhuTab);
	}
	

	@Override
    @SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		list.add("");
		list.add(I18n.format(Config.MODID + ".itemFuZhiCrystal.message1", new Object[]{}));
		list.add(I18n.format(Config.MODID + ".itemFuZhiCrystal.message2", new Object[]{}));
		list.add("");
	}
}