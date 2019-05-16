package com.shepherd.fuzhumod.base;

import java.io.File;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;

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
    
    //Config
    private static Config config;
	private static Configuration configuration;

    //private static
	private int max_High_HDC;
	private int hunDun_Dimension_ID;
	private int chain_Drop_Amount;
	
    private double nbttag_Type_Attack_K;
    private double nbttag_Type_Attack_CK;
    private double nbttag_Type_Attack_CB;
    private double nbttag_Type_Defense_K;
    private double nbttag_Type_Defense_CK;
    private double nbttag_Type_Defense_CB;

	private int hunDun_Tool_Harvest_Level;
	private int hunDun_Tool_Max_Uses;
	private double hunDun_Tool_Efficiency;
	private double hunDun_Tool_Damage;
	private int hunDun_Tool_Enchantability;
	
	private int hunDun_Armor_Durability;
	private int[] hunDun_Armor_Reduction_Amounts;
	private int hunDun_Armor_Enchantability;
	
	private Item.ToolMaterial hunDunToolMaterial;
	private ItemArmor.ArmorMaterial hunDunArmorMaterial;
	
	private Config(FMLPreInitializationEvent e){
		configuration = new Configuration(new File(e.getModConfigurationDirectory(), "/shepherd/fuzhu.cfg"));
		configuration.load();
		load();
		configuration.save();
		hunDunToolMaterial = EnumHelper.addToolMaterial("HUNDUN", hunDun_Tool_Harvest_Level, hunDun_Tool_Max_Uses, (float)hunDun_Tool_Efficiency, (float)hunDun_Tool_Damage, hunDun_Tool_Enchantability);
		hunDunArmorMaterial = EnumHelper.addArmorMaterial("HUNDUN", hunDun_Armor_Durability, hunDun_Armor_Reduction_Amounts, hunDun_Armor_Enchantability);
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
		this.max_High_HDC = configuration.get("Base", "Max_High_HDC", 3, "Hun Dun Cao max high.").getInt();
		this.hunDun_Dimension_ID = configuration.get("Base", "HUNDUN_DIMENSION_ID", 3, "Hun Dun dimension ID.").getInt();
		this.chain_Drop_Amount = configuration.get("Tool", "CHAIN_DROP_AMOUNT", 64, "The number of chain reactions.").getInt();
		
		this.nbttag_Type_Attack_K = configuration.get("Intensify", "NBTTAG_TYPE_ATTACK_K", 2D, "Tool intensify strength K.").getDouble();
		this.nbttag_Type_Attack_CK = configuration.get("Intensify", "NBTTAG_TYPE_ATTACK_CK", 1D, "Tool intensify need crystal amount K. (amount = K * level + B)").getDouble();
		this.nbttag_Type_Attack_CB = configuration.get("Intensify", "NBTTAG_TYPE_ATTACK_CB", 0D, "Tool intensify need crystal amount B. (amount = K * level + B)").getDouble();
		this.nbttag_Type_Defense_K = configuration.get("Intensify", "NBTTAG_TYPE_DEFENSE_K", 0.5D, "Armor intensify strength K.").getDouble();
		this.nbttag_Type_Defense_CK = configuration.get("Intensify", "NBTTAG_TYPE_DEFENSE_CK", 1D, "Armor intensify need crystal amount K. (amount = K * level + B)").getDouble();
		this.nbttag_Type_Defense_CB = configuration.get("Intensify", "NBTTAG_TYPE_DEFENSE_CB", 0D, "Armor intensify need crystal amount K. (amount = K * level + B)").getDouble();

		this.hunDun_Tool_Harvest_Level = configuration.get("Tool", "HUNDUN_TOOL_HARVEST_LEVEL", 4, "Hundun tools level.").getInt();
		this.hunDun_Tool_Max_Uses = configuration.get("Tool", "HUNDUN_TOOL_MAX_USES", 4096, "The maximum number of Hundun tools to use.").getInt();
		this.hunDun_Tool_Efficiency = configuration.get("Tool", "HUNDUN_TOOL_EFFICIENCY", 16D, "The efficiency of Hundun tools.").getDouble();
		this.hunDun_Tool_Damage = configuration.get("Tool", "HUNDUN_TOOL_DAMAGE", 16D, "Hundun tools damage value.").getDouble();
		this.hunDun_Tool_Enchantability = configuration.get("Tool", "HUNDUN_TOOL_ENCHANTABILITY", 100, "Hundun tools enchant probability.").getInt();

		this.hunDun_Armor_Durability = configuration.get("Tool", "HUNDUN_ARMOR_DURABILITY", 4096, "Hundun armor the maximum number of use of the ratio.").getInt();
		this.hunDun_Armor_Reduction_Amounts = configuration.get("Armor", "HUNDUN_ARMOR_REDUCTION_AMOUNTS", new int[]{4, 7, 6, 3}, "Hundun armor defense values, respectively, the head, body, legs, feet.").getIntList();
		this.hunDun_Armor_Enchantability = configuration.get("Tool", "HUNDUN_ARMOR_ENCHANTABILITY", 100, "Hundun armor enchant probability.").getInt();
	}

	public int getMax_High_HDC() {
		return max_High_HDC;
	}

	public int getHunDun_Dimension_ID() {
		return hunDun_Dimension_ID;
	}

	public int getChain_Drop_Amount() {
		return chain_Drop_Amount;
	}

	public double getNbttag_Type_Attack_K() {
		return nbttag_Type_Attack_K;
	}

	public double getNbttag_Type_Attack_CK() {
		return nbttag_Type_Attack_CK;
	}

	public double getNbttag_Type_Attack_CB() {
		return nbttag_Type_Attack_CB;
	}

	public double getNbttag_Type_Defense_K() {
		return nbttag_Type_Defense_K;
	}

	public double getNbttag_Type_Defense_CK() {
		return nbttag_Type_Defense_CK;
	}

	public double getNbttag_Type_Defense_CB() {
		return nbttag_Type_Defense_CB;
	}

	public int getHunDun_Tool_Harvest_Level() {
		return hunDun_Tool_Harvest_Level;
	}

	public int getHunDun_Tool_Max_Uses() {
		return hunDun_Tool_Max_Uses;
	}

	public double getHunDun_Tool_Efficiency() {
		return hunDun_Tool_Efficiency;
	}

	public double getHunDun_Tool_Damage() {
		return hunDun_Tool_Damage;
	}

	public int getHunDun_Tool_Enchantability() {
		return hunDun_Tool_Enchantability;
	}

	public int getHunDun_Armor_Durability() {
		return hunDun_Armor_Durability;
	}

	public int[] getHunDun_Armor_Reduction_Amounts() {
		return hunDun_Armor_Reduction_Amounts;
	}

	public int getHunDun_Armor_Enchantability() {
		return hunDun_Armor_Enchantability;
	}

	public Item.ToolMaterial getHunDunToolMaterial() {
		return hunDunToolMaterial;
	}

	public ItemArmor.ArmorMaterial getHunDunArmorMaterial() {
		return hunDunArmorMaterial;
	}
}
