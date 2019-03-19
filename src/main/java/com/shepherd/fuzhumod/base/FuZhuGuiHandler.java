package com.shepherd.fuzhumod.base;

import com.shepherd.fuzhumod.gui.GuiHunDunTable;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class FuZhuGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GuiHunDunTable.GUIID)//1
			return new GuiHunDunTable.MyContainer(world, x, y, z, player);
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GuiHunDunTable.GUIID)//1
			return new GuiHunDunTable.MyGuiContainer(world, x, y, z, player);
		return null;
	}

}
