package com.shepherd.fuzhumod.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.entity.TileEntityFuzhiTable;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiFuzhiTable {
	public static final int GUIID = 1;
	public static IInventory entityInventory;
	
	//Container
	public static class MyContainer extends Container {

		public MyContainer(World world, int x, int y, int z, EntityPlayer player) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity != null && (tileEntity instanceof TileEntityFuzhiTable))
				entityInventory = (IInventory) tileEntity;
			else
				entityInventory = new InventoryBasic("fuzhiTableInventory", true, 3);
			//tileEntity
			this.addSlotToContainer(new Slot(entityInventory, 0, 26, 30) {
				public boolean isItemValid(ItemStack stack) {
					return ((stack != null) && stack.getItem() == BaseControl.itemFuzhiCrystal);
				}
			});
			this.addSlotToContainer(new Slot(entityInventory, 1, 116, 10) {
				public boolean isItemValid(ItemStack stack) {
					return ((stack != null) && stack.getItem() != BaseControl.itemFuzhiCrystal && stack.getItem() != Item.getItemFromBlock(BaseControl.blockFuzhiCrystal) && stack.getItem() != Item.getItemFromBlock(BaseControl.blockWanwuCao));
				}
			});
			this.addSlotToContainer(new Slot(entityInventory, 2, 116, 52) {
				public boolean isItemValid(ItemStack stack) {
					return false;
				}
			});
			//player
			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 9; j++) {
					if(i == 0) {
						this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 142));
					}else {
						this.addSlotToContainer(new Slot(player.inventory, j + i * 9, 8 + j * 18, 84 + (i-1) * 18));
					}
				}
			}
		}

		@Override
		public boolean canInteractWith(EntityPlayer player) {
			return true;
		}
		
		@Override
		public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
			ItemStack itemstack = null;
			Slot slot = (Slot) this.inventorySlots.get(index);
			if (slot != null && slot.getHasStack()) {
				ItemStack itemstack1 = slot.getStack();
				itemstack = itemstack1.copy();
				if (index < 9) {
					if (!this.mergeItemStack(itemstack1, 9, (45 - 9), true)) {
						return null;
					}
				} else if (!this.mergeItemStack(itemstack1, 0, 9, false)) {
					return null;
				}
				if (itemstack1.stackSize == 0) {
					slot.putStack((ItemStack) null);
				} else {
					slot.onSlotChanged();
				}
				if (itemstack1.stackSize == itemstack.stackSize) {
					return null;
				}
				slot.onPickupFromSlot(playerIn, itemstack1);
			}
			return itemstack;
		}
	}

	//GuiContainer
	public static class MyGuiContainer extends GuiContainer {
		private static final ResourceLocation texture = new ResourceLocation(Config.MODID + ":fuzhiTableGui.png");

		public MyGuiContainer(World world, int i, int j, int k, EntityPlayer entity) {
			super(new MyContainer(world, i, j, k, entity));
			this.xSize = 176;
			this.ySize = 166;
		}
		
		protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
			int posX = (this.width) / 2;
			int posY = (this.height) / 2;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			this.mc.renderEngine.bindTexture(texture);
			int k = (this.width - this.xSize) / 2;
			int l = (this.height - this.ySize) / 2;
			this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
			zLevel = 100.0F;
		}

		public void onGuiClosed() {
			Keyboard.enableRepeatEvents(false);
		}

		public void initGui() {
			super.initGui();
			this.guiLeft = (this.width - 176) / 2;
			this.guiTop = (this.height - 166) / 2;
			Keyboard.enableRepeatEvents(true);
			this.buttonList.clear();
			int posX = (this.width) / 2;
			int posY = (this.height) / 2;
		}
	}
	
}
