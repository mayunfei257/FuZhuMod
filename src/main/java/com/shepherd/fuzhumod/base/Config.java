package com.shepherd.fuzhumod.base;

import java.io.File;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.config.Configuration;

public class Config {
	//public static
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
    
    //private static
	private static Configuration configuration;
	private static Config config;
	
    private double Nbttag_Type_Attack_K;
    private double Nbttag_Type_Attack_CK;
    private double Nbttag_Type_Attack_CB;
    private double Nbttag_Type_Defense_K;
    private double Nbttag_Type_Defense_CK;
    private double Nbttag_Type_Defense_CB;
	
//	private int TOOL_HARVEST_LEVEL;
//	private int[] ARMOR_REDUCTION_AMOUNTS;
	
//	private Item.ToolMaterial zijingToolMaterial;
//	private ItemArmor.ArmorMaterial zijingArmorMaterial;
	
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
		this.Nbttag_Type_Attack_K = configuration.get("Tool", "NBTTAG_TYPE_ATTACK_K", 2D, "Tools level.").getDouble();
		this.Nbttag_Type_Attack_CK = configuration.get("Tool", "NBTTAG_TYPE_ATTACK_CK", 1D, "Tools level.").getDouble();
		this.Nbttag_Type_Attack_CB = configuration.get("Tool", "NBTTAG_TYPE_ATTACK_CB", 0D, "Tools level.").getDouble();
		this.Nbttag_Type_Defense_K = configuration.get("Tool", "NBTTAG_TYPE_DEFENSE_K", 0.5D, "Tools level.").getDouble();
		this.Nbttag_Type_Defense_CK = configuration.get("Tool", "NBTTAG_TYPE_DEFENSE_CK", 1D, "Tools level.").getDouble();
		this.Nbttag_Type_Defense_CB = configuration.get("Tool", "NBTTAG_TYPE_DEFENSE_CB", 0D, "Tools level.").getDouble();
//		this.TOOL_HARVEST_LEVEL = configuration.get("Tool", "TOOL_HARVEST_LEVEL", 3, "Tools level.").getInt();
//		this.ARMOR_REDUCTION_AMOUNTS = configuration.get("Armor", "ARMOR_REDUCTION_AMOUNTS", new int[]{4, 7, 6, 3}, "Armor defense values, respectively, the head, body, legs, feet.").getIntList();
	}

	public double getNbttag_Type_Attack_K() {
		return Nbttag_Type_Attack_K;
	}
	public void setNbttag_Type_Attack_K(double nbttag_Type_Attack_K) {
		Nbttag_Type_Attack_K = nbttag_Type_Attack_K;
	}
	public double getNbttag_Type_Attack_CK() {
		return Nbttag_Type_Attack_CK;
	}
	public void setNbttag_Type_Attack_CK(double nbttag_Type_Attack_CK) {
		Nbttag_Type_Attack_CK = nbttag_Type_Attack_CK;
	}
	public double getNbttag_Type_Attack_CB() {
		return Nbttag_Type_Attack_CB;
	}
	public void setNbttag_Type_Attack_CB(double nbttag_Type_Attack_CB) {
		Nbttag_Type_Attack_CB = nbttag_Type_Attack_CB;
	}
	public double getNbttag_Type_Defense_K() {
		return Nbttag_Type_Defense_K;
	}
	public void setNbttag_Type_Defense_K(double nbttag_Type_Defense_K) {
		Nbttag_Type_Defense_K = nbttag_Type_Defense_K;
	}
	public double getNbttag_Type_Defense_CK() {
		return Nbttag_Type_Defense_CK;
	}
	public void setNbttag_Type_Defense_CK(double nbttag_Type_Defense_CK) {
		Nbttag_Type_Defense_CK = nbttag_Type_Defense_CK;
	}
	public double getNbttag_Type_Defense_CB() {
		return Nbttag_Type_Defense_CB;
	}
	public void setNbttag_Type_Defense_CB(double nbttag_Type_Defense_CB) {
		Nbttag_Type_Defense_CB = nbttag_Type_Defense_CB;
	}


//	public int getTOOL_HARVEST_LEVEL() {
//		return TOOL_HARVEST_LEVEL;
//	}
//	public int[] getARMOR_REDUCTION_AMOUNTS() {
//		return ARMOR_REDUCTION_AMOUNTS;
//	}
//	public Item.ToolMaterial getZijingToolMaterial() {
//		return zijingToolMaterial;
//	}
//	public ItemArmor.ArmorMaterial getZijingArmorMaterial() {
//		return zijingArmorMaterial;
//	}
}
