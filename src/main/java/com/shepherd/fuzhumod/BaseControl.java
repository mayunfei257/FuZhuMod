package com.shepherd.fuzhumod;

import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.base.FuZhuEvent;
import com.shepherd.fuzhumod.base.FuZhuGuiHandler;
import com.shepherd.fuzhumod.base.FuZhuTab;
import com.shepherd.fuzhumod.base.FuZhuWorldGenerator;
import com.shepherd.fuzhumod.block.BlockBaseSlab;
import com.shepherd.fuzhumod.block.BlockBaseStairs;
import com.shepherd.fuzhumod.block.BlockHunDunCao;
import com.shepherd.fuzhumod.block.BlockHunDunCrystal;
import com.shepherd.fuzhumod.block.BlockHunDunPortal;
import com.shepherd.fuzhumod.block.BlockHunDunStone;
import com.shepherd.fuzhumod.block.BlockHunDunTable;
import com.shepherd.fuzhumod.entity.TileEntityHunDunTable;
import com.shepherd.fuzhumod.item.ItemBlockBase;
import com.shepherd.fuzhumod.item.ItemBlockBaseMeta;
import com.shepherd.fuzhumod.item.ItemBlockBaseSlab;
import com.shepherd.fuzhumod.item.ItemFuZhiCrystal;
import com.shepherd.fuzhumod.item.ItemHuiMieCrystal;
import com.shepherd.fuzhumod.item.ItemHunDunCrystal;
import com.shepherd.fuzhumod.item.ItemShengMingCrystal;
import com.shepherd.fuzhumod.item.ItemToolHunDunEye;
import com.shepherd.fuzhumod.item.ItemToolShougeSickle;
import com.shepherd.fuzhumod.message.ClientToServerMessage;
import com.shepherd.fuzhumod.world.WorldProviderMod;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

public class BaseControl {
    private static int nextID = 0;
	public static SimpleNetworkWrapper netWorkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Config.MODID);
	
	//Tab
	public static final CreativeTabs fuZhuTab = new FuZhuTab();
	//Dimension
	public static int dimensionID;
	//Block
	public static Block blockHunDunPyknoticCrystal;
	public static Block blockHunDunCrystal;
	public static Block blockHunDunTable;
	public static Block blockHunDunCao;
	public static Block blockHunDunPortal;
	public static Block blockHunDunStone;
	public static Block blockHunDunStoneStairs;
	public static Block blockHunDunStoneSlab;
	public static Block blockHunDunStoneDoubleSlab;
	//Item
	public static Item itemHunDunCrystal;
	public static Item itemHuiMieCrystal;
	public static Item itemShengMingCrystal;
	public static Item itemFuZhiCrystal;
	public static ItemTool itemToolHunDunEye;
	public static ItemTool itemToolShougeSickle;
	//FakePlayer
	
	//ServerSide
	public static void init(FMLPreInitializationEvent event){
		//Dimension
		dimensionID = FuZhuMod.config.getHunDun_Dimension_ID();
		//Block
		blockHunDunPyknoticCrystal = new BlockHunDunCrystal().setHardness(100f).setResistance(10000.0f).setLightLevel(1.0f).setBlockName("blockHunDunPyknoticCrystal").setBlockTextureName(Config.MODID + ":blockhundunpyknoticcrystal");
		blockHunDunCrystal = new BlockHunDunCrystal();
		blockHunDunTable = new BlockHunDunTable();
		blockHunDunCao = new BlockHunDunCao();
		blockHunDunPortal = new BlockHunDunPortal();
		blockHunDunStone = new BlockHunDunStone();
		blockHunDunStoneStairs = new BlockBaseStairs(blockHunDunStone, 0).setBlockName("blockHunDunStoneStairs");
		blockHunDunStoneSlab = new BlockBaseSlab(blockHunDunStone, 0, false).setBlockName("blockHunDunStoneSlab");
		blockHunDunStoneDoubleSlab = new BlockBaseSlab(blockHunDunStone, 0, true).setBlockName("blockHunDunStoneSlab");
		//Item
		itemHunDunCrystal = new ItemHunDunCrystal();
		itemHuiMieCrystal = new ItemHuiMieCrystal();
		itemShengMingCrystal = new ItemShengMingCrystal();
		itemFuZhiCrystal = new ItemFuZhiCrystal();
		itemToolHunDunEye = new ItemToolHunDunEye();
		itemToolShougeSickle = new ItemToolShougeSickle();
	}
	
	//ServerSide
	public static void register(FMLPreInitializationEvent event){
		//Dimension
		DimensionManager.registerProviderType(dimensionID, WorldProviderMod.class, false);
		DimensionManager.registerDimension(dimensionID, dimensionID);
		//Block
		GameRegistry.registerBlock(blockHunDunPyknoticCrystal, ItemBlockBase.class, blockHunDunPyknoticCrystal.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunCrystal, ItemBlockBase.class, blockHunDunCrystal.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunTable, ItemBlockBase.class, blockHunDunTable.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunCao, ItemBlockBase.class, blockHunDunCao.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunPortal, ItemBlockBase.class, blockHunDunPortal.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunStone, ItemBlockBaseMeta.class, blockHunDunStone.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunStoneStairs, ItemBlockBase.class, blockHunDunStoneStairs.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunStoneSlab, ItemBlockBaseSlab.class, blockHunDunStoneSlab.getUnlocalizedName(), blockHunDunStoneSlab, blockHunDunStoneDoubleSlab, false);
//		GameRegistry.registerBlock(blockHunDunStoneDoubleSlab, ItemBlockSlabBase.class, blockHunDunStoneDoubleSlab.getUnlocalizedName() + "_Double", blockHunDunStoneSlab, blockHunDunStoneDoubleSlab, true);
		//Item
		GameRegistry.registerItem(itemHunDunCrystal, itemHunDunCrystal.getUnlocalizedName());
		GameRegistry.registerItem(itemHuiMieCrystal, itemHuiMieCrystal.getUnlocalizedName());
		GameRegistry.registerItem(itemShengMingCrystal, itemShengMingCrystal.getUnlocalizedName());
		GameRegistry.registerItem(itemFuZhiCrystal, itemFuZhiCrystal.getUnlocalizedName());
		GameRegistry.registerItem(itemToolHunDunEye, itemToolHunDunEye.getUnlocalizedName());
		GameRegistry.registerItem(itemToolShougeSickle, itemToolShougeSickle.getUnlocalizedName());
		//Entity
//		EntityRegistry.registerModEntity(new ResourceLocation(Config.MODID + ":entitysupersnowman"), EntitySuperSnowman.class, "entitySuperSnowman", 263, ZijingMod.instance,64, 1, true, (204 << 16) + (0 << 8) + 204, (255 << 16) + (102 << 8) + 255);
		//Message
    	netWorkWrapper.registerMessage(ClientToServerMessage.Handler.class, ClientToServerMessage.class, nextID++, Side.SERVER);
//    	netWorkWrapper.registerMessage(ShepherdToClientMessage.Handler.class, ShepherdToClientMessage.class, nextID++, Side.CLIENT);
		//Event
		MinecraftForge.EVENT_BUS.register(new FuZhuEvent());
		//Other
		Item.ToolMaterial hunDunToolMaterial = FuZhuMod.config.getHunDunToolMaterial();
		if(null != hunDunToolMaterial && hunDunToolMaterial.getRepairItemStack() == null) hunDunToolMaterial.setRepairItem(new ItemStack(itemHunDunCrystal, 1));
		ItemArmor.ArmorMaterial hunDunArmorMaterial = FuZhuMod.config.getHunDunArmorMaterial();
		if(null != hunDunArmorMaterial && hunDunArmorMaterial.customCraftingMaterial == null) hunDunArmorMaterial.customCraftingMaterial = itemHunDunCrystal;
	}
    
	//ClientSide
    public static void resourceLoad(FMLPreInitializationEvent event){
    	//Block
		//Item
    }
	
	//ServerSide
	public static void registerRecipe(FMLInitializationEvent event){
		//Recipe and Smelting
		GameRegistry.addShapelessRecipe(new ItemStack(itemHunDunCrystal, 9), blockHunDunCrystal);
		GameRegistry.addShapedRecipe(new ItemStack(blockHunDunCrystal, 1), "AAA", "AAA", "AAA", 'A', itemHunDunCrystal);
		GameRegistry.addShapelessRecipe(new ItemStack(blockHunDunCrystal, 9), blockHunDunPyknoticCrystal);
		GameRegistry.addShapedRecipe(new ItemStack(blockHunDunPyknoticCrystal, 1), "AAA", "AAA", "AAA", 'A', blockHunDunCrystal);
		GameRegistry.addShapedRecipe(new ItemStack(blockHunDunTable, 1), "AAA", "BCB", "BDB", 'A', itemHunDunCrystal, 'B', Blocks.obsidian, 'C', Blocks.crafting_table, 'D',  blockHunDunCrystal);
		GameRegistry.addShapedRecipe(new ItemStack(blockHunDunStone, 1, 1), "A", "A", 'A', new ItemStack(blockHunDunStoneSlab, 1));
		GameRegistry.addShapedRecipe(new ItemStack(blockHunDunStone, 4, 2), "AA", "AA", 'A', new ItemStack(blockHunDunStone, 1, 0));
		GameRegistry.addShapedRecipe(new ItemStack(blockHunDunStoneStairs, 6), "A##", "AA#", "AAA", 'A', new ItemStack(blockHunDunStone, 1, 0));
		GameRegistry.addShapedRecipe(new ItemStack(blockHunDunStoneSlab, 6), "AAA", 'A', new ItemStack(blockHunDunStone, 1, 0));

//		GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder, 3), new ItemStack(Items.flint, 1), new ItemStack(Items.dye, 1, 15), new ItemStack(Items.coal, 1));
//		GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder, 3), new ItemStack(Items.flint, 1), new ItemStack(Items.dye, 1, 15),new ItemStack(Items.coal, 1, 1));
//		GameRegistry.addShapelessRecipe(new ItemStack(Items.clay_ball, 4), Blocks.clay);
//		GameRegistry.addShapelessRecipe(new ItemStack(Items.quartz, 4), Blocks.quartz_block);
//		GameRegistry.addShapelessRecipe(new ItemStack(Items.string, 4), Blocks.wool);
//		GameRegistry.addShapelessRecipe(new ItemStack(Items.glowstone_dust, 4), Blocks.glowstone);
//		GameRegistry.addShapelessRecipe(new ItemStack(Items.melon, 9), Blocks.melon_block);
//		GameRegistry.addShapelessRecipe(new ItemStack(Items.snowball, 4), Blocks.snow);
//
		GameRegistry.addSmelting(blockHunDunCao, new ItemStack(itemHunDunCrystal, 1), 1);
	}

	//ServerSide
	public static void registerGui(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator(new FuZhuWorldGenerator(), 1);
		NetworkRegistry.INSTANCE.registerGuiHandler(FuZhuMod.instance, new FuZhuGuiHandler());
	}
	
	//ClientSide
	public static void renderLoad(FMLInitializationEvent event){
//		RenderingRegistry.registerEntityRenderingHandler(EntitySuperSnowman.class, new RenderSuperSnowman(Minecraft.getMinecraft().getRenderManager()));
//		ClientRegistry.registerKeyBinding(ZijingEvent.key1);
	}
	
	public static void tileEntityAddMapping(FMLPostInitializationEvent event) {
		TileEntity.addMapping(TileEntityHunDunTable.class, Config.MODID + ":tileEntityFuzhiTable");
	}
}
