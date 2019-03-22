package com.shepherd.fuzhumod.block;

import java.util.List;
import java.util.Random;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.world.Test;
import com.shepherd.fuzhumod.world.Test.TeleporterDimensionMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHunDunPortal extends BlockBreakable {
	IIcon gor = null, dol = null, st1 = null, st2 = null, st3 = null, st4 = null;

	public BlockHunDunPortal() {
		super("", Material.portal, false);
		setTickRandomly(true);
		setHardness(-1.0F);
		setLightLevel(0.75F);
		setBlockName("blockHunDunPortal");
		setBlockTextureName(Config.MODID + ":blockhundunportal");
		setCreativeTab(BaseControl.fuZhuTab);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int par2) {
		if (i == 0)
			return gor;
		else if (i == 1)
			return dol;
		else if (i == 2)
			return st1;
		else if (i == 3)
			return st2;
		else if (i == 4)
			return st4;
		else if (i == 5)
			return st3;
		else
			return gor;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.gor = reg.registerIcon(this.getTextureName());
		this.dol = reg.registerIcon(this.getTextureName());
		this.st1 = reg.registerIcon(this.getTextureName());
		this.st2 = reg.registerIcon(this.getTextureName());
		this.st3 = reg.registerIcon(this.getTextureName());
		this.st4 = reg.registerIcon(this.getTextureName());
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		super.updateTick(par1World, par2, par3, par4, par5Random);
//		if (par1World.provider.isSurfaceWorld()) {
//			int l;
//			for (l = par3; !par1World.doesBlockHaveSolidTopSurface(par1World, par2, l, par4) && l > 0; --l) {
//				;
//			}
//			if (l > 0 && !par1World.isBlockNormalCubeDefault(par2, l + 1, par4, true)) {
//				Entity entity = ItemMonsterPlacer.spawnCreature(par1World, 57, (double) par2 + 0.5D, (double) l + 1.1D, (double) par4 + 0.5D);
//				if (entity != null) {
//					entity.timeUntilPortal = entity.getPortalCooldown();
//				}
//			}
//		}
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means
	 * this box can change after the pool has been cleared to be reused)
	 */
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return null;
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x,
	 * y, z
	 */
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		float f;
		float f1;
		if (par1IBlockAccess.getBlock(par2 - 1, par3, par4) != this && par1IBlockAccess.getBlock(par2 + 1, par3, par4) != this) {
			f = 0.125F;
			f1 = 0.5F;
			this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
		} else {
			f = 0.5F;
			f1 = 0.125F;
			this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
		}
	}

	/**
	 * Is this block (a) opaque and (B) a full 1m cube? This determines
	 * whether or not to render the shared face of two adjacent blocks and
	 * also whether the player can attach torches, redstone wire, etc to
	 * this block.
	 */
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return
	 * False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * Checks to see if this location is valid to create a portal and will
	 * return True if it does. Args: world, x, y, z
	 */
	public boolean tryToCreatePortal(World par1World, int par2, int par3, int par4) {
		byte b0 = 0;
		byte b1 = 0;
		if (par1World.getBlock(par2 - 1, par3, par4) == BaseControl.blockHunDunCrystal
				|| par1World.getBlock(par2 + 1, par3, par4) == BaseControl.blockHunDunCrystal) {
			b0 = 1;
		}
		if (par1World.getBlock(par2, par3, par4 - 1) == BaseControl.blockHunDunCrystal
				|| par1World.getBlock(par2, par3, par4 + 1) == BaseControl.blockHunDunCrystal) {
			b1 = 1;
		}
		if (b0 == b1) {
			return false;
		} else {
			if (par1World.getBlock(par2 - b0, par3, par4 - b1) == Blocks.air) {
				par2 -= b0;
				par4 -= b1;
			}
			int l;
			int i1;
			for (l = -1; l <= 2; ++l) {
				for (i1 = -1; i1 <= 3; ++i1) {
					boolean flag = l == -1 || l == 2 || i1 == -1 || i1 == 3;
					if (l != -1 && l != 2 || i1 != -1 && i1 != 3) {
						Block j1 = par1World.getBlock(par2 + b0 * l, par3 + i1, par4 + b1 * l);
						if (flag) {
							if (j1 != BaseControl.blockHunDunCrystal) {
								return false;
							}
						}
						/*
						 * else if (j1 != 0 && j1 !=
						 * Main.TutorialFire.blockID) { return false; }
						 */
					}
				}
			}
			for (l = 0; l < 2; ++l) {
				for (i1 = 0; i1 < 3; ++i1) {
					par1World.setBlock(par2 + b0 * l, par3 + i1, par4 + b1 * l, this, 0, 2);
				}
			}
			return true;
		}
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know
	 * which neighbor changed (coordinates passed are their own) Args: x, y,
	 * z, neighbor blockID
	 */
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		byte b0 = 0;
		byte b1 = 1;
		if (par1World.getBlock(par2 - 1, par3, par4) == this || par1World.getBlock(par2 + 1, par3, par4) == this) {
			b0 = 1;
			b1 = 0;
		}
		int i1;
		for (i1 = par3; par1World.getBlock(par2, i1 - 1, par4) == this; --i1) {
			;
		}
		if (par1World.getBlock(par2, i1 - 1, par4) != BaseControl.blockHunDunCrystal) {
			par1World.setBlockToAir(par2, par3, par4);
		} else {
			int j1;
			for (j1 = 1; j1 < 4 && par1World.getBlock(par2, i1 + j1, par4) == this; ++j1) {
				;
			}
			if (j1 == 3 && par1World.getBlock(par2, i1 + j1, par4) == BaseControl.blockHunDunCrystal) {
				boolean flag = par1World.getBlock(par2 - 1, par3, par4) == this || par1World.getBlock(par2 + 1, par3, par4) == this;
				boolean flag1 = par1World.getBlock(par2, par3, par4 - 1) == this || par1World.getBlock(par2, par3, par4 + 1) == this;
				if (flag && flag1) {
					par1World.setBlockToAir(par2, par3, par4);
				} else {
					if ((par1World.getBlock(par2 + b0, par3, par4 + b1) != BaseControl.blockHunDunCrystal || par1World.getBlock(par2 - b0, par3, par4
							- b1) != this)
							&& (par1World.getBlock(par2 - b0, par3, par4 - b1) != BaseControl.blockHunDunCrystal || par1World.getBlock(par2 + b0, par3,
									par4 + b1) != this)) {
						par1World.setBlockToAir(par2, par3, par4);
					}
				}
			} else {
				par1World.setBlockToAir(par2, par3, par4);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates. Args: blockAccess, x, y, z, side
	 */
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		if (par1IBlockAccess.getBlock(par2, par3, par4) == this) {
			return false;
		} else {
			boolean flag = par1IBlockAccess.getBlock(par2 - 1, par3, par4) == this && par1IBlockAccess.getBlock(par2 - 2, par3, par4) != this;
			boolean flag1 = par1IBlockAccess.getBlock(par2 + 1, par3, par4) == this && par1IBlockAccess.getBlock(par2 + 2, par3, par4) != this;
			boolean flag2 = par1IBlockAccess.getBlock(par2, par3, par4 - 1) == this && par1IBlockAccess.getBlock(par2, par3, par4 - 2) != this;
			boolean flag3 = par1IBlockAccess.getBlock(par2, par3, par4 + 1) == this && par1IBlockAccess.getBlock(par2, par3, par4 + 2) != this;
			boolean flag4 = flag || flag1;
			boolean flag5 = flag2 || flag3;
			return flag4 && par5 == 4 ? true : (flag4 && par5 == 5 ? true : (flag5 && par5 == 2 ? true : flag5 && par5 == 3));
		}
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random par1Random) {
		return 0;
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into
	 * the block). Args: world, x, y, z, entity
	 */
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		if ((par5Entity.ridingEntity == null) && (par5Entity.riddenByEntity == null) && ((par5Entity instanceof EntityPlayerMP))) {
			EntityPlayerMP thePlayer = (EntityPlayerMP) par5Entity;
			if (thePlayer.timeUntilPortal > 0) {
				thePlayer.timeUntilPortal = 10;
			} else if (thePlayer.dimension != BaseControl.dimensionID) {
				thePlayer.timeUntilPortal = 10;
				thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, BaseControl.dimensionID, new TeleporterDimensionMod(thePlayer.mcServer.worldServerForDimension(BaseControl.dimensionID)));
			} else {
				thePlayer.timeUntilPortal = 10;
				thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, 0, new TeleporterDimensionMod(thePlayer.mcServer.worldServerForDimension(0)));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
	 */
	public int getRenderBlockPass() {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (par5Random.nextInt(100) == 0) {
			par1World.playSound((double) par2 + 0.5D, (double) par3 + 0.5D, (double) par4 + 0.5D, "portal.portal", 0.5F,
					par5Random.nextFloat() * 0.4F + 0.8F, false);
		}
		for (int l = 0; l < 4; ++l) {
			double d0 = (double) ((float) par2 + par5Random.nextFloat());
			double d1 = (double) ((float) par3 + par5Random.nextFloat());
			double d2 = (double) ((float) par4 + par5Random.nextFloat());
			double d3 = 0.0D;
			double d4 = 0.0D;
			double d5 = 0.0D;
			int i1 = par5Random.nextInt(2) * 2 - 1;
			d3 = ((double) par5Random.nextFloat() - 0.5D) * 0.5D;
			d4 = ((double) par5Random.nextFloat() - 0.5D) * 0.5D;
			d5 = ((double) par5Random.nextFloat() - 0.5D) * 0.5D;
			if (par1World.getBlock(par2 - 1, par3, par4) != this && par1World.getBlock(par2 + 1, par3, par4) != this) {
				d0 = (double) par2 + 0.5D + 0.25D * (double) i1;
				d3 = (double) (par5Random.nextFloat() * 2.0F * (float) i1);
			} else {
				d2 = (double) par4 + 0.5D + 0.25D * (double) i1;
				d5 = (double) (par5Random.nextFloat() * 2.0F * (float) i1);
			}
			par1World.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
		}
	}

	@SideOnly(Side.CLIENT)
	/**
	 * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	 */
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return 0;
	}

	//ItemBlock
	public static class ItemHunDunPortal extends ItemBlock{

		public ItemHunDunPortal(Block blockHunDunPortal) {
			super(blockHunDunPortal);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
			list.add(I18n.format(Config.MODID + ".blockHunDunPortal.message1", new Object[]{}));
			list.add("");
		}
	}
}