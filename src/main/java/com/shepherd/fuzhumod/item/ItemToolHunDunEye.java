package com.shepherd.fuzhumod.item;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.FuZhuMod;
import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.block.BlockHunDunPortal;
import com.shepherd.fuzhumod.util.SkillEntityPlayer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class ItemToolHunDunEye extends ItemTool {
	private static final Set<Block> blockSet = Sets.newHashSet(new Block[] {});
	
	public ItemToolHunDunEye() {
		super(0, FuZhuMod.config.getHunDunToolMaterial(), blockSet);
		this.setUnlocalizedName("itemToolHunDunEye");
		this.setTextureName(Config.MODID + ":itemtoolhunduneye");
        this.setCreativeTab(BaseControl.fuZhuTab);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player){
		if(null == itemStack || itemStack.stackSize <= 0 || null == itemStack.getItem()) return itemStack;
		if(!world.isRemote) {
			if(!player.isSneaking()) {
				SkillEntityPlayer.healthBoostSkill(player);
			}else {
				SkillEntityPlayer.removeEffectSkill(player);
			}
		}
		return itemStack;
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(null == itemStack || itemStack.stackSize <= 0 || null == itemStack.getItem()) return super.onItemUse(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);

		if(!world.isRemote) {
			if(world.getBlock(x, y, z) != BaseControl.blockHunDunCrystal) {
				if(!player.isSneaking()) {
					SkillEntityPlayer.teleportUpSkill(player, x, y, z);
				}else{
					SkillEntityPlayer.teleportDownSkill(player, x, y, z);
				}
			}else {
				switch(side) {
				case 0: y--; break;
				case 1: y++; break;
				case 2: z--; break;
				case 3: z++; break;
				case 4: x--; break;
				case 5: x++; break;
				}
				if (player.canPlayerEdit(x, y, z, side, itemStack) && world.getBlock(x, y, z) == Blocks.air) {
					world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
					((BlockHunDunPortal)BaseControl.blockHunDunPortal).tryToCreatePortal(world, x, y, z);
					itemStack.damageItem(1, player);
				}
			}
		}

		return true;
	}

	@Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase entityLivingBase, EntityLivingBase player){
        return true;
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entity){
        return true;
	}
	
	@Override
    public boolean func_150897_b(Block block){
        return true;
    }

	@Override
    public float func_150893_a(ItemStack itemStack, Block block){
		return 1.0F;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean showAdvancedInfo) {
		list.add(I18n.format(Config.MODID + ".itemToolHunDunEye.skill1", new Object[]{}));
		list.add(I18n.format(Config.MODID + ".itemToolHunDunEye.skill2", new Object[]{}));
		list.add(I18n.format(Config.MODID + ".itemToolHunDunEye.skill3", new Object[]{}));
		list.add(I18n.format(Config.MODID + ".itemToolHunDunEye.skill4", new Object[]{}));
		list.add(I18n.format(Config.MODID + ".itemToolHunDunEye.skill5", new Object[]{}));
		list.add("");
		list.add(I18n.format(Config.MODID + ".itemToolHunDunEye.message1", new Object[]{}));
		list.add(I18n.format(Config.MODID + ".itemToolHunDunEye.message2", new Object[]{}));
		list.add(I18n.format(Config.MODID + ".itemToolHunDunEye.message3", new Object[]{}));
	}
}
