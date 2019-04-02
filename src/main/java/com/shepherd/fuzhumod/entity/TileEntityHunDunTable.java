package com.shepherd.fuzhumod.entity;

import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.FuZhuMod;
import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.type.CrystalBlockType;
import com.shepherd.fuzhumod.type.CrystalItemType;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class TileEntityHunDunTable  extends TileEntity implements ISidedInventory{
	/** 0 = Crystal  */
	private ItemStack[] hunDunTableItemStacks = new ItemStack[19];
//	private ItemStack[] hunDunTableItemStacks = new ItemStack[3];
	private String inventoryName = "hunDunTableInventory";
	
	public TileEntityHunDunTable() {
	}

	@Override
	public int getSizeInventory() {
		return this.hunDunTableItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.hunDunTableItemStacks[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int num) {
		if (this.hunDunTableItemStacks[index] != null){
			ItemStack itemstack;
			if (this.hunDunTableItemStacks[index].stackSize <= num){
				itemstack = this.hunDunTableItemStacks[index];
				this.hunDunTableItemStacks[index] = null;
				return itemstack;
			}else{
				itemstack = this.hunDunTableItemStacks[index].splitStack(num);
				if (this.hunDunTableItemStacks[index].stackSize == 0){
					this.hunDunTableItemStacks[index] = null;
				}
				return itemstack;
			}
		}else{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		if (this.hunDunTableItemStacks[index] != null){
			ItemStack itemstack = this.hunDunTableItemStacks[index];
			this.hunDunTableItemStacks[index] = null;
			return itemstack;
		}else{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		this.hunDunTableItemStacks[index] = itemStack;
		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()){
			itemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.inventoryName : "hunDunTableInventory";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.inventoryName != null && this.inventoryName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 128;
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

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound){
		super.readFromNBT(nbtTagCompound);
		NBTTagList nbttaglist = nbtTagCompound.getTagList("Items", 10);
		this.hunDunTableItemStacks = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); ++i){
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");
			if (b0 >= 0 && b0 < this.hunDunTableItemStacks.length){
				this.hunDunTableItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		if (nbtTagCompound.hasKey("CustomName", 8)){
			this.inventoryName = nbtTagCompound.getString("CustomName");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound){
		super.writeToNBT(nbtTagCompound);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.hunDunTableItemStacks.length; ++i){
			if (this.hunDunTableItemStacks[i] != null){
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.hunDunTableItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbtTagCompound.setTag("Items", nbttaglist);
		if (this.hasCustomInventoryName()){
			nbtTagCompound.setString("CustomName", this.inventoryName);
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void execute(EntityPlayerMP player) {
		final World world = player.getEntityWorld();
		if(!world.isRemote) {
			ItemStack itemCrystal = hunDunTableItemStacks[0];
			if(!isItemStackEmpty(itemCrystal)) {
				//Crystal
				if(itemCrystal.getItem() == BaseControl.itemHunDunCrystal) {
					if(executeHunDunCrystal(player))
						world.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "block.end_portal.spawn", 1.0F, 1.0F);
				}else if(itemCrystal.getItem() == BaseControl.itemFuZhiCrystal) {
					if(executeFuZhiCrystal(player))
						world.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "block.end_portal.spawn", 1.0F, 1.0F);
				}else if(itemCrystal.getItem() == BaseControl.itemHuiMieCrystal) {
					if(executeHuiMieCrystal(player))
						world.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "random.anvil_land", 1.0F, 1.0F);
				}else if(itemCrystal.getItem() == BaseControl.itemShengMingCrystal) {
					if(executeShengMingCrystal(player))
						world.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "random.anvil_land", 1.0F, 1.0F);
				}
				//Set Empty
				for(int index = 0; index < hunDunTableItemStacks.length; index++) {
					if(isItemStackEmpty(hunDunTableItemStacks[index]))
						hunDunTableItemStacks[index] = null;
				}
			}else {
//				ContainerWorkbench
			}
		}
	}
	
	private boolean executeHunDunCrystal(EntityPlayerMP player) {//HunDun
		final World world = player.getEntityWorld();
		int hunDunCrystalNum = hunDunTableItemStacks[0].stackSize;
		
		int needStackNum = (hunDunCrystalNum / 64) * 3 + (hunDunCrystalNum % 64) > 0 ? 3 : 0;
		if(emptyStackNumOfOutputStack() >= needStackNum) {
			int remaining1 = putItemIntoOutputStack(BaseControl.itemHuiMieCrystal, hunDunCrystalNum);
			if(remaining1 > 0) {
				EntityItem entityitem1 = new EntityItem(world, this.xCoord, this.yCoord, this.zCoord, new ItemStack(BaseControl.itemHuiMieCrystal, remaining1));
				world.spawnEntityInWorld(entityitem1);
			}
			int remaining2 = putItemIntoOutputStack(BaseControl.itemShengMingCrystal, hunDunCrystalNum);
			if(remaining2 > 0) {
				EntityItem entityitem2 = new EntityItem(world, this.xCoord, this.yCoord, this.zCoord, new ItemStack(BaseControl.itemShengMingCrystal, remaining1));
				world.spawnEntityInWorld(entityitem2);
			}
			int remaining3 = putItemIntoOutputStack(BaseControl.itemFuZhiCrystal, hunDunCrystalNum);
			if(remaining3 > 0) {
				EntityItem entityitem3 = new EntityItem(world, this.xCoord, this.yCoord, this.zCoord, new ItemStack(BaseControl.itemFuZhiCrystal, remaining1));
				world.spawnEntityInWorld(entityitem3);
			}
			hunDunTableItemStacks[0].stackSize = 0;
			return true;
		}else {
			player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".tileEntityHunDunTable.hunDunCrystal1", new Object[] {})));
		}
		
		return false;
	}
	
	private boolean executeFuZhiCrystal(EntityPlayerMP player) {//FuZhi
		ItemStack sourceItemStack = null;
		for(int index = 1; index < 10; index++) {//The first one
			if(!isItemStackEmpty(hunDunTableItemStacks[index])) {
				sourceItemStack = hunDunTableItemStacks[index];
				break;
			}
		}
		if(null != sourceItemStack){
			if(sourceItemStack.getItem() instanceof CrystalItemType || sourceItemStack.getItem() instanceof CrystalBlockType) {
				player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".tileEntityHunDunTable.fuZhiCrystal4", new Object[] {})));
				return false;
			}
			int index = getTheEmptyIndexOfOutputStack();
			if(isItemStackEmpty(hunDunTableItemStacks[index])) {
				hunDunTableItemStacks[index] = sourceItemStack.copy();
				hunDunTableItemStacks[0].stackSize -= 1;
				player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".tileEntityHunDunTable.fuZhiCrystal1", new Object[] {})));
				return true;
			}else {
				player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".tileEntityHunDunTable.fuZhiCrystal2", new Object[] {})));
			}
		}else {
			player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".tileEntityHunDunTable.fuZhiCrystal3", new Object[] {})));
		}
		return false;
	}
	
	private boolean executeHuiMieCrystal(EntityPlayerMP player) {//HuiMie
		ItemStack sourceItemStack = null;
		for(int index = 1; index < 10; index++) {//The first tool
			ItemStack itemStack = hunDunTableItemStacks[index];
			if(!isItemStackEmpty(itemStack) && !(itemStack.getItem() instanceof ItemArmor) && itemStack.getMaxStackSize() == 1) {
				sourceItemStack = itemStack;
				break;
			}
		}
		if(null != sourceItemStack) {
			int index = getTheEmptyIndexOfOutputStack();
			if(isItemStackEmpty(hunDunTableItemStacks[index])) {
				NBTTagCompound stackTagCompound = sourceItemStack.getTagCompound();
				if(null == stackTagCompound){
					stackTagCompound = new NBTTagCompound();
					stackTagCompound.setString(Config.NBTTAG_TYPE, Config.NBTTAG_TYPE_ATTACK);
					stackTagCompound.setInteger(Config.NBTTAG_LEVEL, 0);
					stackTagCompound.setFloat(Config.NBTTAG_STRENGTH, 0F);
					sourceItemStack.setTagCompound(stackTagCompound);
				}
				int quantity = (int) ((stackTagCompound.getInteger(Config.NBTTAG_LEVEL) + 1) * FuZhuMod.config.getNbttag_Type_Attack_CK() + FuZhuMod.config.getNbttag_Type_Attack_CB());
				if(hunDunTableItemStacks[0].stackSize >= quantity) {
					stackTagCompound.setString(Config.NBTTAG_TYPE, Config.NBTTAG_TYPE_ATTACK);
					stackTagCompound.setInteger(Config.NBTTAG_LEVEL, stackTagCompound.getInteger(Config.NBTTAG_LEVEL) + 1);
					stackTagCompound.setFloat(Config.NBTTAG_STRENGTH, (float) (stackTagCompound.getInteger(Config.NBTTAG_LEVEL) * FuZhuMod.config.getNbttag_Type_Attack_K()));
					hunDunTableItemStacks[index] = sourceItemStack.copy();
					sourceItemStack.stackSize = 0;
					hunDunTableItemStacks[0].stackSize -= quantity;
					player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".tileEntityHunDunTable.huiMieCrystal1", new Object[] {stackTagCompound.getInteger(Config.NBTTAG_LEVEL)})));
					return true;
				}else {
					player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".tileEntityHunDunTable.huiMieCrystal2", new Object[] {quantity})));
				}
			}else {
				player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".tileEntityHunDunTable.huiMieCrystal3", new Object[] {})));
			}
		}else {
			player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".tileEntityHunDunTable.huiMieCrystal4", new Object[] {})));
		}
		return false;
	}
	
	private boolean executeShengMingCrystal(EntityPlayerMP player) {//ShengMing
		ItemStack sourceItemStack = null;
		for(int index = 1; index < 10; index++) {//The first armor
			if(!isItemStackEmpty(hunDunTableItemStacks[index]) && hunDunTableItemStacks[index].getItem() instanceof ItemArmor) {
				sourceItemStack = hunDunTableItemStacks[index];
				break;
			}
		}
		if(null != sourceItemStack) {
			int index = getTheEmptyIndexOfOutputStack();
			if(isItemStackEmpty(hunDunTableItemStacks[index])) {
				NBTTagCompound stackTagCompound = sourceItemStack.getTagCompound();
				if(null == stackTagCompound){
					stackTagCompound = new NBTTagCompound();
					stackTagCompound.setString(Config.NBTTAG_TYPE, Config.NBTTAG_TYPE_DEFENSE);
					stackTagCompound.setInteger(Config.NBTTAG_LEVEL, 0);
					stackTagCompound.setFloat(Config.NBTTAG_STRENGTH, 0F);
					sourceItemStack.setTagCompound(stackTagCompound);
				}
				int quantity = (int) ((stackTagCompound.getInteger(Config.NBTTAG_LEVEL) + 1) * FuZhuMod.config.getNbttag_Type_Defense_CK() + FuZhuMod.config.getNbttag_Type_Defense_CB());
				if(hunDunTableItemStacks[0].stackSize >= quantity) {
					stackTagCompound.setString(Config.NBTTAG_TYPE, Config.NBTTAG_TYPE_DEFENSE);
					stackTagCompound.setInteger(Config.NBTTAG_LEVEL, stackTagCompound.getInteger(Config.NBTTAG_LEVEL) + 1);
					stackTagCompound.setFloat(Config.NBTTAG_STRENGTH, (float) (stackTagCompound.getInteger(Config.NBTTAG_LEVEL) * FuZhuMod.config.getNbttag_Type_Defense_K()));
					hunDunTableItemStacks[index] = sourceItemStack.copy();
					sourceItemStack.stackSize = 0;
					hunDunTableItemStacks[0].stackSize -= quantity;
					player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".tileEntityHunDunTable.shengMingCrystal1", new Object[] {stackTagCompound.getInteger(Config.NBTTAG_LEVEL)})));
					return true;
				}else {
					player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".tileEntityHunDunTable.shengMingCrystal2", new Object[] {quantity})));
				}
			}else {
				player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".tileEntityHunDunTable.shengMingCrystal3", new Object[] {})));
			}
		}else {
			player.addChatMessage(new ChatComponentText(I18n.format(Config.MODID + ".tileEntityHunDunTable.shengMingCrystal4", new Object[] {})));
		}
		return false;
	}
	
	//------------------------------------------------------------------------------------------------------------------
	
	private boolean isItemStackEmpty(ItemStack itemStack) {
		return null == itemStack || itemStack.stackSize <= 0;
	}
	
	private int emptyStackNumOfOutputStack() {
		int emptyNum = 0;
		for(int index = 10; index < hunDunTableItemStacks.length; index++) {
			if(isItemStackEmpty(hunDunTableItemStacks[index])) {
				emptyNum++;
			}
		}
		return emptyNum;
	}
	
	private int getTheEmptyIndexOfOutputStack() {
		for(int index = 10; index < hunDunTableItemStacks.length; index++) {
			if(isItemStackEmpty(hunDunTableItemStacks[index])) {
				return index;
			}
		}
		return hunDunTableItemStacks.length - 1;
	}
	
	private boolean hasItemOfInputStack(Item item) {
		for(int index = 1; index < 10; index++) {
			if(!isItemStackEmpty(hunDunTableItemStacks[index]) && hunDunTableItemStacks[index].getItem() == item && hunDunTableItemStacks[index].stackSize > 0) {
				return true;
			}
		}
		return false;
	}
	
	private boolean hasEmptyOfOutputStack(Item item) {
		for(int index = 10; index < hunDunTableItemStacks.length; index++) {
			if(isItemStackEmpty(hunDunTableItemStacks[index])) {
				return true;
			}else if(hunDunTableItemStacks[index].getItem() == item && hunDunTableItemStacks[index].stackSize < hunDunTableItemStacks[index].getMaxStackSize()){
				return true;
			}
		}
		return false;
	}
	
	private int removeItemFromInputStack(Item item, int num) {
		for(int index = 1; index < 10; index++) {
			if(num <= 0) break;
			if(!isItemStackEmpty(hunDunTableItemStacks[index]) && hunDunTableItemStacks[index].getItem() == item) {
				if(hunDunTableItemStacks[index].stackSize > num) {
					hunDunTableItemStacks[index].stackSize -= num;
					num = 0;
				}else {
					num -= hunDunTableItemStacks[index].stackSize;
					hunDunTableItemStacks[index].stackSize = 0;
				}
			}
		}
		return num;
	}
	
	private int putItemIntoOutputStack(Item item, int num) {
		for(int index = 10; index < hunDunTableItemStacks.length; index++) {
			if(num <= 0) break;
			if(isItemStackEmpty(hunDunTableItemStacks[index])) {
				if(num > item.getItemStackLimit()) {
					num -= item.getItemStackLimit();
					hunDunTableItemStacks[index] = new ItemStack(item, item.getItemStackLimit());
				}else {
					hunDunTableItemStacks[index] = new ItemStack(item, num);
					num = 0;
				}
			}else if(hunDunTableItemStacks[index].getItem() == item && hunDunTableItemStacks[index].stackSize < item.getItemStackLimit()){
				if(num > item.getItemStackLimit() - hunDunTableItemStacks[index].stackSize) {
					num -= item.getItemStackLimit() - hunDunTableItemStacks[index].stackSize;
					hunDunTableItemStacks[index].stackSize = item.getItemStackLimit();
				}else {
					hunDunTableItemStacks[index].stackSize += num;
					num = 0;
				}
			}
		}
		return num;
	}
}
