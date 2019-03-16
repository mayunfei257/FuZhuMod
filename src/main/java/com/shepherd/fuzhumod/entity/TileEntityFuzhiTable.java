package com.shepherd.fuzhumod.entity;

import com.shepherd.fuzhumod.BaseControl;

import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityFuzhiTable  extends TileEntity implements ISidedInventory{
	private ItemStack[] fuzhiTableItemStacks = new ItemStack[3];
	private String inventoryName = "fuzhiTableInventory";

	@Override
	public int getSizeInventory() {
		return this.fuzhiTableItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.fuzhiTableItemStacks[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int num) {
		if (this.fuzhiTableItemStacks[index] != null){
			ItemStack itemstack;
			if (this.fuzhiTableItemStacks[index].stackSize <= num){
				itemstack = this.fuzhiTableItemStacks[index];
				this.fuzhiTableItemStacks[index] = null;
				return itemstack;
			}else{
				itemstack = this.fuzhiTableItemStacks[index].splitStack(num);
				if (this.fuzhiTableItemStacks[index].stackSize == 0){
					this.fuzhiTableItemStacks[index] = null;
				}
				return itemstack;
			}
		}else{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		if (this.fuzhiTableItemStacks[index] != null){
			ItemStack itemstack = this.fuzhiTableItemStacks[index];
			this.fuzhiTableItemStacks[index] = null;
			return itemstack;
		}else{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		this.fuzhiTableItemStacks[index] = itemStack;
		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()){
			itemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.inventoryName : "fuzhiTableInventory";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.inventoryName != null && this.inventoryName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return null;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return false;
	}

	public void readFromNBT(NBTTagCompound nbtTagCompound){
		super.readFromNBT(nbtTagCompound);
		NBTTagList nbttaglist = nbtTagCompound.getTagList("Items", 10);
		this.fuzhiTableItemStacks = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); ++i){
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");
			if (b0 >= 0 && b0 < this.fuzhiTableItemStacks.length){
				this.fuzhiTableItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		if (nbtTagCompound.hasKey("CustomName", 8)){
			this.inventoryName = nbtTagCompound.getString("CustomName");
		}
	}

	public void writeToNBT(NBTTagCompound nbtTagCompound){
		super.writeToNBT(nbtTagCompound);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.fuzhiTableItemStacks.length; ++i){
			if (this.fuzhiTableItemStacks[i] != null){
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.fuzhiTableItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbtTagCompound.setTag("Items", nbttaglist);
		if (this.hasCustomInventoryName()){
			nbtTagCompound.setString("CustomName", this.inventoryName);
		}
	}

	public void updateEntity(){
		if(!this.worldObj.isRemote) {
			if(this.fuzhiTableItemStacks[2] == null || this.fuzhiTableItemStacks[2].stackSize <= 0) {
				if(this.fuzhiTableItemStacks[1] != null && this.fuzhiTableItemStacks[1].stackSize > 0) {
					if(this.fuzhiTableItemStacks[0] != null && this.fuzhiTableItemStacks[0].stackSize > 0 && this.fuzhiTableItemStacks[0].getItem() == BaseControl.itemFuzhiCrystal) {
						this.fuzhiTableItemStacks[2] = this.fuzhiTableItemStacks[1].copy();
						this.fuzhiTableItemStacks[0].stackSize -= 1;
					}
				}
			}
		}
	}
	
}
