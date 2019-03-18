package com.shepherd.fuzhumod.block;

import java.util.List;
import java.util.Random;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.FuZhuMod;
import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.entity.TileEntityHunDunTable;
import com.shepherd.fuzhumod.gui.GuiHunDunTable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockHunDunTable  extends BlockContainer{
    private final boolean working;
    private final Random random = new Random();
	IIcon gor = null, dol = null, st1 = null, st2 = null, st3 = null, st4 = null;
	
	public BlockHunDunTable() {
		super(Material.anvil);
		working = false;
        setHardness(10f);
        setResistance(100.0f);
        setHarvestLevel("pickaxe", 0);
        setStepSound(Block.soundTypeMetal);
        setBlockName("blockHunDunTable");
        setBlockTextureName(Config.MODID + ":blockhunduntable");
		setCreativeTab(BaseControl.fuZhuTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (side == 0) return gor;
		else if (side == 1) return dol;
		else if (side == 2) return st1;
		else if (side == 3) return st2;
		else if (side == 4) return st4;
		else if (side == 5) return st3;
		else return gor;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.gor = reg.registerIcon(this.getTextureName());
		this.dol = reg.registerIcon(Config.MODID + ":blockhunduntable_up");
		this.st1 = reg.registerIcon(this.getTextureName());
		this.st2 = reg.registerIcon(this.getTextureName());
		this.st3 = reg.registerIcon(this.getTextureName());
		this.st4 = reg.registerIcon(this.getTextureName());
	}

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		return new TileEntityHunDunTable();
	}
	
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_){
        if (world.isRemote){
            return true;
        } else{
        	TileEntityHunDunTable tileEntityFuzhiTable = (TileEntityHunDunTable)world.getTileEntity(x, y, z);
            if (tileEntityFuzhiTable != null){
				player.openGui(FuZhuMod.instance, GuiHunDunTable.GUIID, world, x, y, z);
            }
            return true;
        }
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random){
		if (this.working){
			float randomx = random.nextFloat();
			float randomy = random.nextFloat();
			float randomz = random.nextFloat();
			world.spawnParticle("smoke", x + randomx, y + randomy, z + randomz, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block bolck, int metadata){
		TileEntityHunDunTable tileEntityFuzhiTable = (TileEntityHunDunTable)world.getTileEntity(x, y, z);
		if (tileEntityFuzhiTable != null){
			for (int i1 = 0; i1 < tileEntityFuzhiTable.getSizeInventory(); ++i1){
				ItemStack itemstack = tileEntityFuzhiTable.getStackInSlot(i1);
				if (itemstack != null){
					float f = this.random.nextFloat() * 0.8F + 0.1F;
					float f1 = this.random.nextFloat() * 0.8F + 0.1F;
					float f2 = this.random.nextFloat() * 0.8F + 0.1F;

					while (itemstack.stackSize > 0){
						int dropNum = itemstack.stackSize;
						itemstack.stackSize -= dropNum;
						EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), dropNum, itemstack.getItemDamage()));

						if (itemstack.hasTagCompound()){
							entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
						}

						float f3 = 0.05F;
						entityitem.motionX = this.random.nextGaussian() * f3;
						entityitem.motionY = this.random.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = this.random.nextGaussian() * f3;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
			world.func_147453_f(x, y, z, bolck);
		}
		super.breakBlock(world, x, y, z, bolck, metadata);
	}
	
	//ItemBlock
	public static class ItemBlockHunDunTable extends ItemBlock{

		public ItemBlockHunDunTable(Block blockHunDunTable) {
			super(blockHunDunTable);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
			list.add("");
			list.add(I18n.format(Config.MODID + ".blockHunDunTable.message1", new Object[]{}));
			list.add(I18n.format(Config.MODID + ".blockHunDunTable.message2", new Object[]{}));
			list.add("");
		}
	}
}