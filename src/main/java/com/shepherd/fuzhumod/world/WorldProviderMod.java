package com.shepherd.fuzhumod.world;

import com.shepherd.fuzhumod.BaseControl;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderMod extends WorldProvider{
	private static final String worldName = "HunDunWorld";
	
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerCustom(this.worldObj.getSeed(), WorldType.DEFAULT);
		this.isHellWorld = true;
		this.hasNoSky = true;
		this.dimensionId = BaseControl.dimensionID;
	}

	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float par1, float par2) {
		return Vec3.createVectorHelper(0.2D, 0.2D, 0.2D);
	}

	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderModded2(this.worldObj, this.worldObj.getSeed() - 11866);
	}

	public boolean isSurfaceWorld() {
		return false;
	}

	public boolean canCoordinateBeSpawn(int par1, int par2) {
		return false;
	}

	public boolean canRespawnHere() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean doesXZShowFog(int par1, int par2) {
		return false;
	}

	public String getDimensionName() {
		return worldName;
	}

	@Override
	protected void generateLightBrightnessTable() {
		float f = 0.5F;
		for (int i = 0; i <= 15; ++i) {
			float f1 = 1.0F - (float) i / 15.0F;
			this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
		}
	}
}
