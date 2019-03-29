package com.shepherd.fuzhumod.world;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;

public class LightForestGenLayer extends GenLayer {

	public LightForestGenLayer(long seed) { super(seed); }

	public static GenLayer[] makeTheWorld(long seed, WorldType type) {
		GenLayer biomes = new GenLayerBiomes(1L);
		biomes = new GenLayerZoom(1000L, biomes);
		biomes = new GenLayerZoom(1001L, biomes);
		biomes = new GenLayerZoom(1002L, biomes);
		biomes = new GenLayerZoom(1003L, biomes);
		biomes = new GenLayerZoom(1004L, biomes);
		biomes = new GenLayerZoom(1005L, biomes);
		GenLayer genlayervoronoizoom = new GenLayerVoronoiZoom(10L, biomes);
		biomes.initWorldGenSeed(seed);
		genlayervoronoizoom.initWorldGenSeed(seed);
		return new GenLayer[]{biomes, genlayervoronoizoom};
	}

	@Override
	public int[] getInts(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_) {
		return null;
	}

	
	public static class GenLayerBiomes extends GenLayer {
		protected BiomeGenBase[] allowedBiomes = {BiomeGenBase.plains,};

		public GenLayerBiomes(long seed) { super(seed); }
		public GenLayerBiomes(long seed, GenLayer genlayer) {
			super(seed);
			this.parent = genlayer;
		}

		@Override
		public int[] getInts(int x, int z, int width, int depth) {
			int[] dest = IntCache.getIntCache(width * depth);
			for (int dz = 0; dz < depth; dz++) {
				for (int dx = 0; dx < width; dx++) {
					this.initChunkSeed(dx + x, dz + z);
					dest[(dx + dz * width)] = this.allowedBiomes[nextInt(this.allowedBiomes.length)].biomeID;
				}
			}
			return dest;
		}
	}
}
