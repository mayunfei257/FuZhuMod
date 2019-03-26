package com.shepherd.fuzhumod.item;

import java.util.List;

import com.shepherd.fuzhumod.base.Config;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBase extends ItemBlock{

	public ItemBlockBase(Block block) {
		super(block);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean showAdvancedInfo) {
		list.add(I18n.format(Config.MODID + ".blockHunDunCrystal.message1", new Object[]{}));
		list.add(I18n.format(Config.MODID + ".blockHunDunCrystal.message2", new Object[]{}));
		list.add("");
	}
}