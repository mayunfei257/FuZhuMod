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
		int baseX = chunkX * 16;
		int baseY = 0;
		int baseZ = chunkZ * 16;
		
		if (world.provider.dimensionId == 0) {
			for (int i = 0; i < 0; i++) {
				int x = baseX + random.nextInt(16);
				int y = baseY + random.nextInt(128);
				int z = baseZ + random.nextInt(16);
				for (int l = 0; l < 64; ++l){
					int i1 = x + random.nextInt(8) - random.nextInt(8);
					int j1 = y + random.nextInt(4) - random.nextInt(4);
					int k1 = z + random.nextInt(8) - random.nextInt(8);

					if (world.isAirBlock(i1, j1, k1) && (!world.provider.hasNoSky || j1 < 255) && BaseControl.blockHunDunCao.canBlockStay(world, i1, j1, k1)){
						world.setBlock(i1, j1, k1, BaseControl.blockHunDunCao, 0, 2);
					}
				}
			}
		}else {
			
		}
	}
	
}
