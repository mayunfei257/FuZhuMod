package com.shepherd.fuzhumod.item;

import java.util.List;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.type.CrystalItemType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemFuZhiCrystal extends Item implements CrystalItemType{

	public ItemFuZhiCrystal() {
		super();
		setMaxStackSize(64);
		setUnlocalizedName("itemFuZhiCrystal");
		setTextureName(Config.MODID + ":itemfuzhicrystal");
		setCreativeTab(BaseControl.fuZhuTab);
	}
	

	@Override
    @SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean showAdvancedInfo) {
		list.add("");
		list.add(I18n.format(Config.MODID + ".itemFuZhiCrystal.message1", new Object[]{}));
		list.add(I18n.format(Config.MODID + ".itemFuZhiCrystal.message2", new Object[]{}));
		list.add("");
	}
}