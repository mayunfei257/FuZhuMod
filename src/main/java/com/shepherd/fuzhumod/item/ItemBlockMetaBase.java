package com.shepherd.fuzhumod.item;

import java.util.List;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.block.BlockHunDunStone;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBlockMetaBase extends ItemBlock{
	protected final Block block;
	protected final String[] metaNameStrings;
	
	public ItemBlockMetaBase(Block block){
		super(block);
		this.block = block;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		if(block == BaseControl.blockHunDunStone) {
			this.metaNameStrings = BlockHunDunStone.nameStrings;
		}else {
			metaNameStrings = new String[]{};
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta){
		return this.block.getIcon(2, meta);
	}

	@Override
	public int getMetadata(int itemDamage){
		return itemDamage;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack){
		int i = itemStack.getItemDamage();
		if (i < 0 || i >= this.metaNameStrings.length){
			i = 0;
		}
		return super.getUnlocalizedName() + "." + this.metaNameStrings[i];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean showAdvancedInfo) {
		int meta = itemStack.getItemDamage();
		if (meta < 0 || meta >= this.metaNameStrings.length) meta = 0;
		String name = this.metaNameStrings[meta];
		
		if(block == BaseControl.blockHunDunStone) {
			list.add(I18n.format(Config.MODID + ".blockHunDunStone."+ name +".message1", new Object[]{}));
			list.add("");
		}
	}
}