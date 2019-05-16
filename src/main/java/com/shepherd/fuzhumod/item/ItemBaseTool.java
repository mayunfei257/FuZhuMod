package com.shepherd.fuzhumod.item;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.shepherd.fuzhumod.BaseControl;
import com.shepherd.fuzhumod.base.Config;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;

public class ItemBaseTool extends Item{
//	protected ToolMaterialS toolMaterial;
//	private float damageVsEntity;
//    protected float efficiencyOnProperMaterial = 1.0F;
//	private Set<Block> blockSet;
//
//	public ItemBaseTool(float addDamage, ToolMaterialS material, Set<Block> blockSet) {
//		super();
//		this.setMaxStackSize(1);
//		this.toolMaterial = material;
//		this.blockSet = blockSet;
//		this.setMaxDamage(material.getMaxUses());
//		this.damageVsEntity = addDamage + material.getDamageVsEntity();
//        this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
//		setCreativeTab(BaseControl.fuZhuTab);
//	}
//
//	@Override
//	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player){
//		return super.onItemRightClick(itemStack, world, player);
//	}
//
//	@Override
//	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
//		return super.onItemUse(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
//	}
//
//	@Override
//	public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entity){
//		if (block.getBlockHardness(world, x, y, z) >= 1.0D){
//			itemStack.damageItem(1, entity);
//	        return true;
//        }else {
//            return super.onBlockDestroyed(itemStack, world, block, x, y, z, entity);
//        }
//	}
//
//	@Override
//	public boolean hitEntity(ItemStack itemStack, EntityLivingBase entity, EntityLivingBase player){
//		itemStack.damageItem(1, player);
//		return true;
//	}
//
//	@Override
//	public float func_150893_a(ItemStack itemStack, Block block){
//		if(blockSet.contains(block)) return this.efficiencyOnProperMaterial;
//		
//		if(itemStack.getItem() == BaseControl.itemToolHunDunEye) {
//			return 1.0F;
//		}else if(itemStack.getItem() == BaseControl.itemToolShougeSickle) {
//			return block instanceof IPlantable ? this.efficiencyOnProperMaterial : 1.0F;
//		}
//		
//		return 1.0F;
//	}
//
//	@Override
//	public int getItemEnchantability(){
//		return this.toolMaterial.getEnchantability();
//	}
//
//	@Override
//	public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_){
//		return true;
//	}
//
//	@Override
//	public Multimap getItemAttributeModifiers(){
//		Multimap multimap = super.getItemAttributeModifiers();
//		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", this.damageVsEntity, 0));
//		return multimap;
//	}
//
//	@SideOnly(Side.CLIENT)
//	public boolean isFull3D(){
//		return true;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean showAdvancedInfo) {
//		list.add("");
//		list.add(I18n.format(Config.MODID + ".itemBaseTool.attack", new Object[]{this.damageVsEntity}));
//	}
//	
//	public ToolMaterialS getToolMaterialS() {
//		return this.toolMaterial;
//	}
//	
//	/*===================================== FORGE START =================================*/
//    private String toolClass;
//    @Override
//    public int getHarvestLevel(ItemStack stack, String toolClass)
//    {
//        int level = super.getHarvestLevel(stack, toolClass);
//        if (level == -1 && toolClass != null && toolClass.equals(this.toolClass))
//        {
//            return this.toolMaterial.getHarvestLevel();
//        }
//        else
//        {
//            return level;
//        }
//    }
//
//    @Override
//    public Set<String> getToolClasses(ItemStack stack)
//    {
//        return toolClass != null ? ImmutableSet.of(toolClass) : super.getToolClasses(stack);
//    }
//
//    @Override
//    public float getDigSpeed(ItemStack stack, Block block, int meta)
//    {
//        if (ForgeHooks.isToolEffective(stack, block, meta))
//        {
//            return efficiencyOnProperMaterial;
//        }
//        return super.getDigSpeed(stack, block, meta);
//    }
//    /*===================================== FORGE END =================================*/
//    
//	// ToolMaterialS --------------------------------------------------------------------------------------------------------------------------------------------------------------------
//
//	public static enum ToolMaterialS{
//		HUNDUN(4, 4096, 16.0F, 16.0F, 100);
//		
//		private final int harvestLevel;
//		private final int maxUses;
//		private final float efficiencyOnProperMaterial;
//		private final float damageVsEntity;
//		private final int enchantability;
//		private ItemStack repairMaterial = null;
//
//		private ToolMaterialS(int harvestLevel, int maxUses, float efficiencyOnProperMaterial, float damageVsEntity, int enchantability){
//			this.harvestLevel = harvestLevel;
//			this.maxUses = maxUses;
//			this.efficiencyOnProperMaterial = efficiencyOnProperMaterial;
//			this.damageVsEntity = damageVsEntity;
//			this.enchantability = enchantability;
//		}
//
//		public int getHarvestLevel(){
//			return this.harvestLevel;
//		}
//		public int getMaxUses(){
//			return this.maxUses;
//		}
//		public float getEfficiencyOnProperMaterial(){
//			return this.efficiencyOnProperMaterial;
//		}
//		public float getDamageVsEntity(){
//			return this.damageVsEntity;
//		}
//		public int getEnchantability(){
//			return this.enchantability;
//		}
//
//		public ToolMaterialS setRepairItem(ItemStack stack){
//			if (this.repairMaterial != null) throw new RuntimeException("Can not change already set repair material");
//			if (this == HUNDUN ) throw new RuntimeException("Can not change vanilla tool repair materials");
//			this.repairMaterial = stack;
//			return this;
//		}
//		
//		public ItemStack getRepairItemStack(){
//			if(null != repairMaterial) {
//				return repairMaterial;
//			}else {
//				Item item = null;
//				switch (this){
//					case HUNDUN:	item = BaseControl.itemHunDunCrystal;break;
//					default :		item = BaseControl.itemHunDunCrystal;
//				}
//				return new ItemStack(item, 1, net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE);
//			}
//		}
//	}
}
