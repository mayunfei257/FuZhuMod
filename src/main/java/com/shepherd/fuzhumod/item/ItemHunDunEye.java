package com.shepherd.fuzhumod.item;

import java.util.List;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.block.BlockHunDunPortal;
import com.shepherd.fuzhumod.world.Test;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHunDunEye extends Item {
	public ItemHunDunEye() {
		super();
		setMaxStackSize(1);
		setMaxDamage(1024);
		setUnlocalizedName("itemHunDunEye");
		setTextureName(Config.MODID + ":itemhunduneye");
		setCreativeTab(BaseControl.fuZhuTab);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (side == 0)
			y--;
		if (side == 1)
			y++;
		if (side == 2)
			z--;
		if (side == 3)
			z++;
		if (side == 4)
			x--;
		if (side == 5)
			x++;
		if (!player.canPlayerEdit(x, y, z, side, itemstack)) return false;
		
		if (world.getBlock(x, y, z) == Blocks.air) {
			world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
			((BlockHunDunPortal)BaseControl.blockHunDunPortal).tryToCreatePortal(world, x, y, z);
		}
		itemstack.damageItem(1, player);
		return true;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean showAdvancedInfo) {
		list.add("");
		list.add(I18n.format(Config.MODID + ".itemHunDunEyes.message1", new Object[]{}));
		list.add(I18n.format(Config.MODID + ".itemHunDunEyes.message2", new Object[]{}));
		list.add("");
	}
}
