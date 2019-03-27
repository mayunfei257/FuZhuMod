package com.shepherd.fuzhumod.item;

import java.util.List;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBase extends ItemBlock{
	protected final Block block;

	public ItemBlockBase(Block block) {
		super(block);
		this.block = block;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean showAdvancedInfo) {
		if(block == BaseControl.blockHunDunCrystal) {
			list.add(I18n.format(Config.MODID + ".blockHunDunCrystal.message1", new Object[]{}));
			list.add(I18n.format(Config.MODID + ".blockHunDunCrystal.message2", new Object[]{}));
			list.add("");
		}else if(block == BaseControl.blockHunDunTable) {
			list.add(I18n.format(Config.MODID + ".blockHunDunTable.message1", new Object[]{}));
			list.add(I18n.format(Config.MODID + ".blockHunDunTable.message2", new Object[]{}));
			list.add("");
		}else if(block == BaseControl.blockHunDunCao) {
			list.add(I18n.format(Config.MODID + ".blockHunDunCao.message1", new Object[]{}));
			list.add("");
		}else if(block == BaseControl.blockHunDunPortal) {
			list.add(I18n.format(Config.MODID + ".blockHunDunPortal.message1", new Object[]{}));
			list.add("");
		}
	}
}