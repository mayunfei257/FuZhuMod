package com.shepherd.fuzhumod.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.entity.TileEntityHunDunTable;
import com.shepherd.fuzhumod.item.ItemCrystal;
import com.shepherd.fuzhumod.message.ClientToServerMessage;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
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

public class GuiHunDunTable {
	public static final int GUIID = 1;
	public static final String GUINAME = "GuiHunDunTable";
	public static IInventory entityInventory;
	
	//Container
	public static class MyContainer extends Container {

		public MyContainer(World world, int x, int y, int z, EntityPlayer player) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity != null && (tileEntity instanceof TileEntityHunDunTable))
				entityInventory = (IInventory) tileEntity;
			
			//tileEntity
			this.addSlotToContainer(new Slot(entityInventory, 0, 80, 8) {//Crystal
				public boolean isItemValid(ItemStack stack) {
					return (stack != null) && stack.getItem() instanceof ItemCrystal;
				}
			});
//			this.addSlotToContainer(new Slot(entityInventory, 0, 80, 14) {//Crystal
//				public boolean isItemValid(ItemStack stack) {
//					return (stack != null) && stack.getItem() instanceof ItemCrystal;
//				}
//			});
			for(int i = 0; i < 3; i++) {//Input
				for(int j = 0; j < 3; j++) {
					this.addSlotToContainer(new Slot(entityInventory, i * 3 + j + 1, 8 + j * 18, 8 + i * 18) {
						public boolean isItemValid(ItemStack stack) {
							return stack != null
									&& (!(stack.getItem() instanceof ItemCrystal) || stack.getItem() == BaseControl.itemHunDunCrystal)
									&& stack.getItem() != Item.getItemFromBlock(BaseControl.blockHunDunCrystal)
									&& stack.getItem() != Item.getItemFromBlock(BaseControl.blockHunDunCao);
						}
					});
				}
			}
//			this.addSlotToContainer(new Slot(entityInventory, 1, 44, 34) {//Input
//				public boolean isItemValid(ItemStack stack) {
//					return stack != null
//							&& (!(stack.getItem() instanceof ItemCrystal) || stack.getItem() == BaseControl.itemHunDunCrystal)
//							&& stack.getItem() != Item.getItemFromBlock(BaseControl.blockHunDunCrystal)
//							&& stack.getItem() != Item.getItemFromBlock(BaseControl.blockHunDunCao);
//				}
//			});
			for(int i = 0; i < 3; i++) {//Output
				for(int j = 0; j < 3; j++) {
					this.addSlotToContainer(new Slot(entityInventory, i * 3 + j + 10, 116 + j * 18, 8 + i * 18) {
						public boolean isItemValid(ItemStack stack) {
							return false;
						}
					});
				}
			}
//			this.addSlotToContainer(new Slot(entityInventory, 2, 116, 34) {//Output
//				public boolean isItemValid(ItemStack stack) {
//					return false;
//				}
//			});
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
		private EntityPlayer player;
		private int dimensionId;
		private int x;
		private int y;
		private int z;

		public MyGuiContainer(World world, int i, int j, int k, EntityPlayer player) {
			super(new MyContainer(world, i, j, k, player));
			this.player = player;
			this.dimensionId = world.provider.dimensionId;
			this.x = i;
			this.y = j;
			this.z = k;
			this.xSize = 176;
			this.ySize = 166;
		}

		@Override
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

		@Override
		public void onGuiClosed() {
			Keyboard.enableRepeatEvents(false);
		}

		@Override
		public void initGui() {
			super.initGui();
			this.guiLeft = (this.width - 176) / 2;
			this.guiTop = (this.height - 166) / 2;
			Keyboard.enableRepeatEvents(true);
			this.buttonList.clear();
			int posX = (this.width) / 2;
			int posY = (this.height) / 2;
			this.buttonList.add(new GuiButton(0, this.guiLeft + 72, this.guiTop + 40, 32, 20, I18n.format(Config.MODID + ".guiHunDunTable.button", new Object[]{})));
//			this.buttonList.add(new GuiButton(0, this.guiLeft + 65, this.guiTop + 55, 35, 20, I18n.format(Config.MODID + ".guiHunDunTable.button", new Object[]{})));

		}
		
		@Override
		protected void actionPerformed(GuiButton button) {
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			World world = server.worldServers[0];
			if (button.id == 0) {
				NBTTagCompound nbtTag = new NBTTagCompound();
				nbtTag.setInteger("DimensionId", this.dimensionId);
				nbtTag.setInteger("X", this.x);
				nbtTag.setInteger("Y", this.y);
				nbtTag.setInteger("Z", this.z);
				BaseControl.netWorkWrapper.sendToServer(new ClientToServerMessage(this.player.getUniqueID(), GuiHunDunTable.GUINAME, nbtTag));
			}
		}
	}
	
}
