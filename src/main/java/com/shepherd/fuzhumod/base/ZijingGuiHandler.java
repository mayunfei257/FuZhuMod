package com.shepherd.fuzhumod.base;

import com.shepherd.fuzhumod.gui.GuiFuzhiTable;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ZijingGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GuiFuzhiTable.GUIID)//1
			return new GuiFuzhiTable.MyContainer(world, x, y, z, player);
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GuiFuzhiTable.GUIID)//1
			return new GuiFuzhiTable.MyGuiContainer(world, x, y, z, player);
		return null;
	}

}
