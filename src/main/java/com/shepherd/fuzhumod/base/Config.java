package com.shepherd.fuzhumod.base;

import java.io.File;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.config.Configuration;

public class Config {
    public static final String MODID = "fuzhumod";
    public static final String VERSION = "1.0.0";
    public static final String CLIENTSIDE = "com.shepherd.fuzhumod.ClientProxy";
    public static final String SERVERSIDE = "com.shepherd.fuzhumod.CommonProxy";
    public static final String MODTAB = "fuZhuTab";

    public static final String NBTTAG_TYPE = MODID + ":Type";
    public static final String NBTTAG_LEVEL = MODID + ":Level";
    public static final String NBTTAG_STRENGTH = MODID + ":Strength";
    
    public static final String NBTTAG_TYPE_ATTACK = "attack";
    public static final String NBTTAG_TYPE_DEFENSE = "defense";
    

	private static Configuration configuration;
	private static Config config;
	
	private int MAX_COPY_TICK;
	private int TOOL_HARVEST_LEVEL;
	private int[] ARMOR_REDUCTION_AMOUNTS;
	
	private Item.ToolMaterial zijingToolMaterial;
	private ItemArmor.ArmorMaterial zijingArmorMaterial;
	
	private Config(FMLPreInitializationEvent e){
		configuration = new Configuration(new File(e.getModConfigurationDirectory(), "/shepherd/fuzhu.cfg"));
		configuration.load();
		load();
		configuration.save();
//		zijingToolMaterial = EnumHelper.addToolMaterial("ZIJING", TOOL_HARVEST_LEVEL, TOOL_MAX_USES, (float)TOOL_EFFICIENCY, TOOL_DAMAGE, TOOL_ENCHANTABILITY);
//		zijingArmorMaterial = EnumHelper.addArmorMaterial("ZIJING", Constant.MODID + ":zijing", ARMOR_DURABILITY, ARMOR_REDUCTION_AMOUNTS, ARMOR_ENCHANTABILITY, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, (float)ARMOR_TOUGHNESS);
	}
	
	public static Config getConfig(FMLPreInitializationEvent e) {
		if(null == config) {
			synchronized (Config.class){
				if(null == config) {
					config = new Config(e);
				}
			}
		}
		return config;
	}
	
	private void load(){
		this.MAX_COPY_TICK = configuration.get("Base", "MAX_COPY_TICK", 40, "The wait time required to duplicate an item.").getInt();
		this.TOOL_HARVEST_LEVEL = configuration.get("Tool", "TOOL_HARVEST_LEVEL", 3, "Tools level.").getInt();
		this.ARMOR_REDUCTION_AMOUNTS = configuration.get("Armor", "ARMOR_REDUCTION_AMOUNTS", new int[]{4, 7, 6, 3}, "Armor defense values, respectively, the head, body, legs, feet.").getIntList();
		
	}


	public int getMAX_COPY_TICK() {
		return MAX_COPY_TICK;
	}
	public void setMAX_COPY_TICK(int mAX_COPY_TICK) {
		MAX_COPY_TICK = mAX_COPY_TICK;
	}
	public int getTOOL_HARVEST_LEVEL() {
		return TOOL_HARVEST_LEVEL;
	}
	public int[] getARMOR_REDUCTION_AMOUNTS() {
		return ARMOR_REDUCTION_AMOUNTS;
	}
	public Item.ToolMaterial getZijingToolMaterial() {
		return zijingToolMaterial;
	}
	public ItemArmor.ArmorMaterial getZijingArmorMaterial() {
		return zijingArmorMaterial;
	}
}
