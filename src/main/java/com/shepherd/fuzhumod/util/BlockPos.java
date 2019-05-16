package com.shepherd.fuzhumod.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockPos {

	private int x;
	private int y;
	private int z;
	private Block block;
	private World world;
	private int meta;
	
	public BlockPos(int x, int y, int z, World world, Block block, int meta) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
		this.block = block;
		this.meta = meta;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getZ() {
		return z;
	}
	public Block getBlock() {
		return block;
	}
	public World getWorld() {
		return world;
	}
	public int getMeta() {
		return meta;
	}
}
