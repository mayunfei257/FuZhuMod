package com.shepherd.fuzhumod.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.FuZhuMod;
import com.shepherd.fuzhumod.block.BlockHunDunPortal;
import com.shepherd.fuzhumod.item.ItemHunDunEye;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class Test {
	private static final String worldName = "HunDunWorld";

	public static class WorldProviderMod extends WorldProvider {

		public void registerWorldChunkManager() {
			this.worldChunkMgr = new WorldChunkManagerCustom(this.worldObj.getSeed(), WorldType.DEFAULT);
			this.isHellWorld = true;
			this.hasNoSky = true;
			this.dimensionId = BaseControl.dimensionID;
		}

		@SideOnly(Side.CLIENT)
		public Vec3 getFogColor(float par1, float par2) {
			return Vec3.createVectorHelper(1.0D, 1.0D, 1.0D);
		}

		public IChunkProvider createChunkGenerator() {
			return new ChunkProviderModded(this.worldObj, this.worldObj.getSeed() - 12794);
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

	}

	public class TutorialPortalPosition extends ChunkCoordinates {
		public long field_85087_d;
		final TeleporterDimensionMod field_85088_e;

		public TutorialPortalPosition(TeleporterDimensionMod tutorialTeleporter, int par2, int par3, int par4, long par5) {
			super(par2, par3, par4);
			this.field_85088_e = tutorialTeleporter;
			this.field_85087_d = par5;
		}
	}

	public static class TeleporterDimensionMod extends Teleporter {

		private final WorldServer worldServerInstance;
		/**
		 * A private Random() function in Teleporter
		 */
		private final Random random;
		/**
		 * Stores successful portal placement locations for rapid lookup.
		 */
		private final LongHashMap destinationCoordinateCache = new LongHashMap();
		/**
		 * A list of valid keys for the destinationCoordainteCache. These are
		 * based on the X & Z of the players initial location.
		 */
		private final List destinationCoordinateKeys = new ArrayList();
		private static final String __OBFID = "CL_00000153";

		public TeleporterDimensionMod(WorldServer par1WorldServer) {
			super(par1WorldServer);
			this.worldServerInstance = par1WorldServer;
			this.random = new Random(par1WorldServer.getSeed());
		}

		/**
		 * Place an entity in a nearby portal, creating one if necessary.
		 */
		public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8) {
			if (this.worldServerInstance.provider.dimensionId != 1) {
				if (!this.placeInExistingPortal(par1Entity, par2, par4, par6, par8)) {
					this.makePortal(par1Entity);
					this.placeInExistingPortal(par1Entity, par2, par4, par6, par8);
				}
			} else {
				int i = MathHelper.floor_double(par1Entity.posX);
				int j = MathHelper.floor_double(par1Entity.posY) - 1;
				int k = MathHelper.floor_double(par1Entity.posZ);
				byte b0 = 1;
				byte b1 = 0;

				for (int l = -2; l <= 2; ++l) {
					for (int i1 = -2; i1 <= 2; ++i1) {
						for (int j1 = -1; j1 < 3; ++j1) {
							int k1 = i + i1 * b0 + l * b1;
							int l1 = j + j1;
							int i2 = k + i1 * b1 - l * b0;
							boolean flag = j1 < 0;
							this.worldServerInstance.setBlock(k1, l1, i2, flag ? BaseControl.blockHunDunCrystal : Blocks.air);
						}
					}
				}

				par1Entity.setLocationAndAngles((double) i, (double) j, (double) k, par1Entity.rotationYaw, 0.0F);
				par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
			}
		}

		/**
		 * Place an entity in a nearby portal which already exists.
		 */
		public boolean placeInExistingPortal(Entity par1Entity, double par2, double par4, double par6, float par8) {
			short short1 = 128;
			double d3 = -1.0D;
			int i = 0;
			int j = 0;
			int k = 0;
			int l = MathHelper.floor_double(par1Entity.posX);
			int i1 = MathHelper.floor_double(par1Entity.posZ);
			long j1 = ChunkCoordIntPair.chunkXZ2Int(l, i1);
			boolean flag = true;
			double d7;
			int l3;

			if (this.destinationCoordinateCache.containsItem(j1)) {
				Teleporter.PortalPosition portalposition = (Teleporter.PortalPosition) this.destinationCoordinateCache.getValueByKey(j1);
				d3 = 0.0D;
				i = portalposition.posX;
				j = portalposition.posY;
				k = portalposition.posZ;
				portalposition.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
				flag = false;
			} else {
				for (l3 = l - short1; l3 <= l + short1; ++l3) {
					double d4 = (double) l3 + 0.5D - par1Entity.posX;

					for (int l1 = i1 - short1; l1 <= i1 + short1; ++l1) {
						double d5 = (double) l1 + 0.5D - par1Entity.posZ;

						for (int i2 = this.worldServerInstance.getActualHeight() - 1; i2 >= 0; --i2) {
							if (this.worldServerInstance.getBlock(l3, i2, l1) == BaseControl.blockHunDunPortal) {
								while (this.worldServerInstance.getBlock(l3, i2 - 1, l1) == BaseControl.blockHunDunPortal) {
									--i2;
								}

								d7 = (double) i2 + 0.5D - par1Entity.posY;
								double d8 = d4 * d4 + d7 * d7 + d5 * d5;

								if (d3 < 0.0D || d8 < d3) {
									d3 = d8;
									i = l3;
									j = i2;
									k = l1;
								}
							}
						}
					}
				}
			}

			if (d3 >= 0.0D) {
				if (flag) {
					this.destinationCoordinateCache.add(j1, new Teleporter.PortalPosition(i, j, k, this.worldServerInstance.getTotalWorldTime()));
					this.destinationCoordinateKeys.add(Long.valueOf(j1));
				}

				double d11 = (double) i + 0.5D;
				double d6 = (double) j + 0.5D;
				d7 = (double) k + 0.5D;
				int i4 = -1;

				if (this.worldServerInstance.getBlock(i - 1, j, k) == BaseControl.blockHunDunPortal) {
					i4 = 2;
				}

				if (this.worldServerInstance.getBlock(i + 1, j, k) == BaseControl.blockHunDunPortal) {
					i4 = 0;
				}

				if (this.worldServerInstance.getBlock(i, j, k - 1) == BaseControl.blockHunDunPortal) {
					i4 = 3;
				}

				if (this.worldServerInstance.getBlock(i, j, k + 1) == BaseControl.blockHunDunPortal) {
					i4 = 1;
				}

				int j2 = par1Entity.getTeleportDirection();

				if (i4 > -1) {
					int k2 = Direction.rotateLeft[i4];
					int l2 = Direction.offsetX[i4];
					int i3 = Direction.offsetZ[i4];
					int j3 = Direction.offsetX[k2];
					int k3 = Direction.offsetZ[k2];
					boolean flag1 = !this.worldServerInstance.isAirBlock(i + l2 + j3, j, k + i3 + k3)
							|| !this.worldServerInstance.isAirBlock(i + l2 + j3, j + 1, k + i3 + k3);
					boolean flag2 = !this.worldServerInstance.isAirBlock(i + l2, j, k + i3)
							|| !this.worldServerInstance.isAirBlock(i + l2, j + 1, k + i3);

					if (flag1 && flag2) {
						i4 = Direction.rotateOpposite[i4];
						k2 = Direction.rotateOpposite[k2];
						l2 = Direction.offsetX[i4];
						i3 = Direction.offsetZ[i4];
						j3 = Direction.offsetX[k2];
						k3 = Direction.offsetZ[k2];
						l3 = i - j3;
						d11 -= (double) j3;
						int k1 = k - k3;
						d7 -= (double) k3;
						flag1 = !this.worldServerInstance.isAirBlock(l3 + l2 + j3, j, k1 + i3 + k3)
								|| !this.worldServerInstance.isAirBlock(l3 + l2 + j3, j + 1, k1 + i3 + k3);
						flag2 = !this.worldServerInstance.isAirBlock(l3 + l2, j, k1 + i3)
								|| !this.worldServerInstance.isAirBlock(l3 + l2, j + 1, k1 + i3);
					}

					float f1 = 0.5F;
					float f2 = 0.5F;

					if (!flag1 && flag2) {
						f1 = 1.0F;
					} else if (flag1 && !flag2) {
						f1 = 0.0F;
					} else if (flag1 && flag2) {
						f2 = 0.0F;
					}

					d11 += (double) ((float) j3 * f1 + f2 * (float) l2);
					d7 += (double) ((float) k3 * f1 + f2 * (float) i3);
					float f3 = 0.0F;
					float f4 = 0.0F;
					float f5 = 0.0F;
					float f6 = 0.0F;

					if (i4 == j2) {
						f3 = 1.0F;
						f4 = 1.0F;
					} else if (i4 == Direction.rotateOpposite[j2]) {
						f3 = -1.0F;
						f4 = -1.0F;
					} else if (i4 == Direction.rotateRight[j2]) {
						f5 = 1.0F;
						f6 = -1.0F;
					} else {
						f5 = -1.0F;
						f6 = 1.0F;
					}

					double d9 = par1Entity.motionX;
					double d10 = par1Entity.motionZ;
					par1Entity.motionX = d9 * (double) f3 + d10 * (double) f6;
					par1Entity.motionZ = d9 * (double) f5 + d10 * (double) f4;
					par1Entity.rotationYaw = par8 - (float) (j2 * 90) + (float) (i4 * 90);
				} else {
					par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
				}

				par1Entity.setLocationAndAngles(d11, d6, d7, par1Entity.rotationYaw, par1Entity.rotationPitch);
				return true;
			} else {
				return false;
			}
		}

		public boolean makePortal(Entity par1Entity) {
			byte b0 = 16;
			double d0 = -1.0D;
			int i = MathHelper.floor_double(par1Entity.posX);
			int j = MathHelper.floor_double(par1Entity.posY);
			int k = MathHelper.floor_double(par1Entity.posZ);
			int l = i;
			int i1 = j;
			int j1 = k;
			int k1 = 0;
			int l1 = this.random.nextInt(4);
			int i2;
			double d1;
			double d2;
			int k2;
			int i3;
			int k3;
			int j3;
			int i4;
			int l3;
			int k4;
			int j4;
			int i5;
			int l4;
			double d3;
			double d4;

			for (i2 = i - b0; i2 <= i + b0; ++i2) {
				d1 = (double) i2 + 0.5D - par1Entity.posX;

				for (k2 = k - b0; k2 <= k + b0; ++k2) {
					d2 = (double) k2 + 0.5D - par1Entity.posZ;
					label274 :

					for (i3 = this.worldServerInstance.getActualHeight() - 1; i3 >= 0; --i3) {
						if (this.worldServerInstance.isAirBlock(i2, i3, k2)) {
							while (i3 > 0 && this.worldServerInstance.isAirBlock(i2, i3 - 1, k2)) {
								--i3;
							}

							for (j3 = l1; j3 < l1 + 4; ++j3) {
								k3 = j3 % 2;
								l3 = 1 - k3;

								if (j3 % 4 >= 2) {
									k3 = -k3;
									l3 = -l3;
								}

								for (i4 = 0; i4 < 3; ++i4) {
									for (j4 = 0; j4 < 4; ++j4) {
										for (k4 = -1; k4 < 4; ++k4) {
											l4 = i2 + (j4 - 1) * k3 + i4 * l3;
											i5 = i3 + k4;
											int j5 = k2 + (j4 - 1) * l3 - i4 * k3;

											if (k4 < 0 && !this.worldServerInstance.getBlock(l4, i5, j5).getMaterial().isSolid() || k4 >= 0
													&& !this.worldServerInstance.isAirBlock(l4, i5, j5)) {
												continue label274;
											}
										}
									}
								}

								d4 = (double) i3 + 0.5D - par1Entity.posY;
								d3 = d1 * d1 + d4 * d4 + d2 * d2;

								if (d0 < 0.0D || d3 < d0) {
									d0 = d3;
									l = i2;
									i1 = i3;
									j1 = k2;
									k1 = j3 % 4;
								}
							}
						}
					}
				}
			}

			if (d0 < 0.0D) {
				for (i2 = i - b0; i2 <= i + b0; ++i2) {
					d1 = (double) i2 + 0.5D - par1Entity.posX;

					for (k2 = k - b0; k2 <= k + b0; ++k2) {
						d2 = (double) k2 + 0.5D - par1Entity.posZ;
						label222 :

						for (i3 = this.worldServerInstance.getActualHeight() - 1; i3 >= 0; --i3) {
							if (this.worldServerInstance.isAirBlock(i2, i3, k2)) {
								while (i3 > 0 && this.worldServerInstance.isAirBlock(i2, i3 - 1, k2)) {
									--i3;
								}

								for (j3 = l1; j3 < l1 + 2; ++j3) {
									k3 = j3 % 2;
									l3 = 1 - k3;

									for (i4 = 0; i4 < 4; ++i4) {
										for (j4 = -1; j4 < 4; ++j4) {
											k4 = i2 + (i4 - 1) * k3;
											l4 = i3 + j4;
											i5 = k2 + (i4 - 1) * l3;

											if (j4 < 0 && !this.worldServerInstance.getBlock(k4, l4, i5).getMaterial().isSolid() || j4 >= 0
													&& !this.worldServerInstance.isAirBlock(k4, l4, i5)) {
												continue label222;
											}
										}
									}

									d4 = (double) i3 + 0.5D - par1Entity.posY;
									d3 = d1 * d1 + d4 * d4 + d2 * d2;

									if (d0 < 0.0D || d3 < d0) {
										d0 = d3;
										l = i2;
										i1 = i3;
										j1 = k2;
										k1 = j3 % 2;
									}
								}
							}
						}
					}
				}
			}

			int k5 = l;
			int j2 = i1;
			k2 = j1;
			int l5 = k1 % 2;
			int l2 = 1 - l5;

			if (k1 % 4 >= 2) {
				l5 = -l5;
				l2 = -l2;
			}

			boolean flag;

			if (d0 < 0.0D) {
				if (i1 < 70) {
					i1 = 70;
				}

				if (i1 > this.worldServerInstance.getActualHeight() - 10) {
					i1 = this.worldServerInstance.getActualHeight() - 10;
				}

				j2 = i1;

				for (i3 = -1; i3 <= 1; ++i3) {
					for (j3 = 1; j3 < 3; ++j3) {
						for (k3 = -1; k3 < 3; ++k3) {
							l3 = k5 + (j3 - 1) * l5 + i3 * l2;
							i4 = j2 + k3;
							j4 = k2 + (j3 - 1) * l2 - i3 * l5;
							flag = k3 < 0;
							this.worldServerInstance.setBlock(l3, i4, j4, flag ? BaseControl.blockHunDunCrystal : Blocks.air);
						}
					}
				}
			}

			for (i3 = 0; i3 < 4; ++i3) {
				for (j3 = 0; j3 < 4; ++j3) {
					for (k3 = -1; k3 < 4; ++k3) {
						l3 = k5 + (j3 - 1) * l5;
						i4 = j2 + k3;
						j4 = k2 + (j3 - 1) * l2;
						flag = j3 == 0 || j3 == 3 || k3 == -1 || k3 == 3;
						this.worldServerInstance.setBlock(l3, i4, j4, (Block) (flag ? BaseControl.blockHunDunCrystal : BaseControl.blockHunDunPortal), 0, 2);
					}
				}

				for (j3 = 0; j3 < 4; ++j3) {
					for (k3 = -1; k3 < 4; ++k3) {
						l3 = k5 + (j3 - 1) * l5;
						i4 = j2 + k3;
						j4 = k2 + (j3 - 1) * l2;
						this.worldServerInstance.notifyBlocksOfNeighborChange(l3, i4, j4, this.worldServerInstance.getBlock(l3, i4, j4));
					}
				}
			}

			return true;
		}

		/**
		 * called periodically to remove out-of-date portal locations from the
		 * cache list. Argument par1 is a WorldServer.getTotalWorldTime() value.
		 */
		public void removeStalePortalLocations(long par1) {
			if (par1 % 100L == 0L) {
				Iterator iterator = this.destinationCoordinateKeys.iterator();
				long j = par1 - 600L;

				while (iterator.hasNext()) {
					Long olong = (Long) iterator.next();
					Teleporter.PortalPosition portalposition = (Teleporter.PortalPosition) this.destinationCoordinateCache.getValueByKey(olong
							.longValue());

					if (portalposition == null || portalposition.lastUpdateTime < j) {
						iterator.remove();
						this.destinationCoordinateCache.remove(olong.longValue());
					}
				}
			}
		}

		public class PortalPosition extends ChunkCoordinates {
			/**
			 * The worldtime at which this PortalPosition was last verified
			 */
			public long lastUpdateTime;
			private static final String __OBFID = "CL_00000154";

			public PortalPosition(int par2, int par3, int par4, long par5) {
				super(par2, par3, par4);
				this.lastUpdateTime = par5;
			}
		}
	}

	// /FIRE BLOCK

	static class BlockFireMod extends Block {

		protected BlockFireMod() {
			super(Material.ground);
		}

		public void onBlockAdded(World par1World, int par2, int par3, int par4) {
			/* TutorialPortal.tryToCreatePortal(par1World, par2, par3, par4); */
		}

	}// fire block end



	public static class WorldChunkManagerCustom extends WorldChunkManager {

		private GenLayer genBiomes;
		/** A GenLayer containing the indices into BiomeGenBase.biomeList[] */
		private GenLayer biomeIndexLayer;
		/** The BiomeCache object for this world. */
		private BiomeCache biomeCache;
		/** A list of biomes that the player can spawn in. */
		private List<BiomeGenBase> biomesToSpawnIn;

		public WorldChunkManagerCustom() {
			this.biomeCache = new BiomeCache(this);
			this.biomesToSpawnIn = new ArrayList();
			this.biomesToSpawnIn.addAll(allowedBiomes);
		}

		public WorldChunkManagerCustom(long seed, WorldType worldType) {
			this();
			// i changed this to my GenLayerTutorial
			GenLayer[] agenlayer = LightForestGenLayer.makeTheWorld(seed, worldType);

			agenlayer = getModdedBiomeGenerators(worldType, seed, agenlayer);
			this.genBiomes = agenlayer[0];
			this.biomeIndexLayer = agenlayer[1];

		}

		public WorldChunkManagerCustom(World world) {
			this(world.getSeed(), world.getWorldInfo().getTerrainType());

		}

		/**
		 * Gets the list of valid biomes for the player to spawn in.
		 */
		@Override
		public List<BiomeGenBase> getBiomesToSpawnIn() {
			return this.biomesToSpawnIn;
		}

		/**
		 * Returns a list of rainfall values for the specified blocks. Args:
		 * listToReuse, x, z, width, length.
		 */
		@Override
		public float[] getRainfall(float[] listToReuse, int x, int z, int width, int length) {
			IntCache.resetIntCache();

			if (listToReuse == null || listToReuse.length < width * length) {
				listToReuse = new float[width * length];
			}

			int[] aint = this.biomeIndexLayer.getInts(x, z, width, length);

			for (int i1 = 0; i1 < width * length; ++i1) {
				try {
					float f = (float) BiomeGenBase.getBiome(aint[i1]).getIntRainfall() / 65536.0F;

					if (f > 1.0F) {
						f = 1.0F;
					}

					listToReuse[i1] = f;
				} catch (Throwable throwable) {
					CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
					CrashReportCategory crashreportcategory = crashreport.makeCategory("DownfallBlock");
					crashreportcategory.addCrashSection("biome id", Integer.valueOf(i1));
					crashreportcategory.addCrashSection("downfalls[] size", Integer.valueOf(listToReuse.length));
					crashreportcategory.addCrashSection("x", Integer.valueOf(x));
					crashreportcategory.addCrashSection("z", Integer.valueOf(z));
					crashreportcategory.addCrashSection("w", Integer.valueOf(width));
					crashreportcategory.addCrashSection("h", Integer.valueOf(length));
					throw new ReportedException(crashreport);
				}
			}

			return listToReuse;
		}

		/**
		 * Return an adjusted version of a given temperature based on the y
		 * height
		 */
		@Override
		@SideOnly(Side.CLIENT)
		public float getTemperatureAtHeight(float par1, int par2) {
			return par1;
		}

		/**
		 * Returns an array of biomes for the location input.
		 */
		@Override
		public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5) {
			IntCache.resetIntCache();

			if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5) {
				par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
			}

			int[] aint = this.genBiomes.getInts(par2, par3, par4, par5);

			try {
				for (int i = 0; i < par4 * par5; ++i) {
					par1ArrayOfBiomeGenBase[i] = BiomeGenBase.getBiome(aint[i]);
				}

				return par1ArrayOfBiomeGenBase;
			} catch (Throwable throwable) {
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
				CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
				crashreportcategory.addCrashSection("biomes[] size", Integer.valueOf(par1ArrayOfBiomeGenBase.length));
				crashreportcategory.addCrashSection("x", Integer.valueOf(par2));
				crashreportcategory.addCrashSection("z", Integer.valueOf(par3));
				crashreportcategory.addCrashSection("w", Integer.valueOf(par4));
				crashreportcategory.addCrashSection("h", Integer.valueOf(par5));
				throw new ReportedException(crashreport);
			}
		}

		/**
		 * Returns biomes to use for the blocks and loads the other data like
		 * temperature and humidity onto the WorldChunkManager Args:
		 * oldBiomeList, x, z, width, depth
		 */
		@Override
		public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] oldBiomeList, int x, int z, int width, int depth) {
			return this.getBiomeGenAt(oldBiomeList, x, z, width, depth, true);
		}

		/**
		 * Return a list of biomes for the specified blocks. Args: listToReuse,
		 * x, y, width, length, cacheFlag (if false, don't check biomeCache to
		 * avoid infinite loop in BiomeCacheBlock)
		 */
		@Override
		public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] listToReuse, int x, int y, int width, int length, boolean cacheFlag) {
			IntCache.resetIntCache();

			if (listToReuse == null || listToReuse.length < width * length) {
				listToReuse = new BiomeGenBase[width * length];
			}

			if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (y & 15) == 0) {
				BiomeGenBase[] abiomegenbase1 = this.biomeCache.getCachedBiomes(x, y);
				System.arraycopy(abiomegenbase1, 0, listToReuse, 0, width * length);
				return listToReuse;
			} else {
				int[] aint = this.biomeIndexLayer.getInts(x, y, width, length);

				for (int i = 0; i < width * length; ++i) {
					listToReuse[i] = BiomeGenBase.getBiome(aint[i]);
				}
				return listToReuse;
			}
		}

		/**
		 * checks given Chunk's Biomes against List of allowed ones
		 */
		@Override
		public boolean areBiomesViable(int x, int y, int z, List par4List) {
			IntCache.resetIntCache();
			int l = x - z >> 2;
			int i1 = y - z >> 2;
			int j1 = x + z >> 2;
			int k1 = y + z >> 2;
			int l1 = j1 - l + 1;
			int i2 = k1 - i1 + 1;
			int[] aint = this.genBiomes.getInts(l, i1, l1, i2);

			try {
				for (int j2 = 0; j2 < l1 * i2; ++j2) {
					BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[j2]);

					if (!par4List.contains(biomegenbase)) {
						return false;
					}
				}

				return true;
			} catch (Throwable throwable) {
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
				CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
				crashreportcategory.addCrashSection("Layer", this.genBiomes.toString());
				crashreportcategory.addCrashSection("x", Integer.valueOf(x));
				crashreportcategory.addCrashSection("z", Integer.valueOf(y));
				crashreportcategory.addCrashSection("radius", Integer.valueOf(z));
				crashreportcategory.addCrashSection("allowed", par4List);
				throw new ReportedException(crashreport);
			}
		}

		/**
		 * Finds a valid position within a range, that is in one of the listed
		 * biomes. Searches {par1,par2} +-par3 blocks. Strongly favors positive
		 * y positions.
		 */
		@Override
		public ChunkPosition findBiomePosition(int p_150795_1_, int p_150795_2_, int p_150795_3_, List p_150795_4_, Random p_150795_5_) {
			IntCache.resetIntCache();
			int l = p_150795_1_ - p_150795_3_ >> 2;
			int i1 = p_150795_2_ - p_150795_3_ >> 2;
			int j1 = p_150795_1_ + p_150795_3_ >> 2;
			int k1 = p_150795_2_ + p_150795_3_ >> 2;
			int l1 = j1 - l + 1;
			int i2 = k1 - i1 + 1;
			int[] aint = this.genBiomes.getInts(l, i1, l1, i2);
			ChunkPosition chunkposition = null;
			int j2 = 0;

			for (int k2 = 0; k2 < l1 * i2; ++k2) {
				int l2 = l + k2 % l1 << 2;
				int i3 = i1 + k2 / l1 << 2;
				BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[k2]);

				if (p_150795_4_.contains(biomegenbase) && (chunkposition == null || p_150795_5_.nextInt(j2 + 1) == 0)) {
					chunkposition = new ChunkPosition(l2, 0, i3);
					++j2;
				}
			}

			return chunkposition;
		}

		/**
		 * Calls the WorldChunkManager's biomeCache.cleanupCache()
		 */
		@Override
		public void cleanupCache() {
			this.biomeCache.cleanupCache();
		}
	}

	public static class LightForestGenLayer extends GenLayer {

		public LightForestGenLayer(long seed) {
			super(seed);
		}

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
	}

	public static class GenLayerBiomes extends GenLayer {

		protected BiomeGenBase[] allowedBiomes = {BiomeGenBase.ocean,};

		public GenLayerBiomes(long seed) {
			super(seed);
		}

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
