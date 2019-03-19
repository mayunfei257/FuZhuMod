package com.shepherd.fuzhumod.base;

import java.util.Random;

import com.shepherd.fuzhumod.BaseControl;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenFlowers;

public class FuZhuWorldGenerator implements IWorldGenerator{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

		chunkX = chunkX * 16;
		chunkZ = chunkZ * 16;
		if (world.provider.dimensionId == 0) {
			for (int i = 0; i < 0; i++) {
				int l6 = chunkX + random.nextInt(16) + 8;
				int i11 = random.nextInt(128);
				int l14 = chunkZ + random.nextInt(16) + 8;
				(new WorldGenFlowers(BaseControl.blockHunDunCao)).generate(world, random, l6, i11, l14);
			}
		}
	}

}
