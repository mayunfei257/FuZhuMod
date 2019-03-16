package com.shepherd.fuzhumod.block;

import java.util.Random;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockWanwuCao extends BlockReed {
	private static final int MaxHigh = 3;
	IIcon la;

	public BlockWanwuCao() {
		super();
        setHardness(0.01f);
        setResistance(2.0f);
        setStepSound(Block.soundTypeGrass);
        setBlockName("blockWanwuCao");
        setBlockTextureName(Config.MODID + ":blockwanwucao");
		setCreativeTab(BaseControl.fuZhuTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.la;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		la = reg.registerIcon(this.getTextureName());
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return EnumPlantType.Crop;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y - 1, z);
		return (block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this) || block == BaseControl.blockWanwuCao);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return 16777215;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if (world.getBlock(x, y - 1, z) == BaseControl.blockWanwuCao || this.func_150170_e(world, x, y, z)) {
			if (world.isAirBlock(x, y + 1, z)) {
				int l;
				for (l = 1; world.getBlock(x, y - l, z) == this; ++l) {}
				if (l < MaxHigh) {
					int i1 = world.getBlockMetadata(x, y, z);
					if (i1 == 15) {
						world.setBlock(x, y + 1, z, this);
						world.setBlockMetadataWithNotify(x, y, z, 0, 4);
					} else {
						world.setBlockMetadataWithNotify(x, y, z, i1 + 1, 4);
					}
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(BaseControl.blockWanwuCao);
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 1;
	}

	@Override
	public Item getItemDropped(int par1, Random random, int par3) {
		return Item.getItemFromBlock(BaseControl.blockWanwuCao);
	}
}
