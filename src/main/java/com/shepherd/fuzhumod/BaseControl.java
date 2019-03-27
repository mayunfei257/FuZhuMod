package com.shepherd.fuzhumod;

import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.base.FuZhuEvent;
import com.shepherd.fuzhumod.base.FuZhuGuiHandler;
import com.shepherd.fuzhumod.base.FuZhuTab;
import com.shepherd.fuzhumod.base.FuZhuWorldGenerator;
import com.shepherd.fuzhumod.block.BlockHunDunCao;
import com.shepherd.fuzhumod.block.BlockHunDunCrystal;
import com.shepherd.fuzhumod.block.BlockHunDunPortal;
import com.shepherd.fuzhumod.block.BlockHunDunStone;
import com.shepherd.fuzhumod.block.BlockHunDunTable;
import com.shepherd.fuzhumod.block.BlockSlabBase;
import com.shepherd.fuzhumod.block.BlockStairsBase;
import com.shepherd.fuzhumod.entity.TileEntityHunDunTable;
import com.shepherd.fuzhumod.item.ItemBlockBase;
import com.shepherd.fuzhumod.item.ItemBlockMetaBase;
import com.shepherd.fuzhumod.item.ItemBlockSlabBase;
import com.shepherd.fuzhumod.item.ItemFuZhiCrystal;
import com.shepherd.fuzhumod.item.ItemHuiMieCrystal;
import com.shepherd.fuzhumod.item.ItemHunDunCrystal;
import com.shepherd.fuzhumod.item.ItemHunDunEye;
import com.shepherd.fuzhumod.item.ItemShengMingCrystal;
import com.shepherd.fuzhumod.message.ClientToServerMessage;
import com.shepherd.fuzhumod.world.Test;

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
import net.minecraft.item.ItemStack;
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
	public static Block blockHunDunCrystal;
	public static Block blockHunDunTable;
	public static Block blockHunDunCao;
	public static Block blockHunDunPortal;
	public static Block blockHunDunStone;
	public static Block blockHunDunStoneStairs;
	public static Block blockHunDunStoneSlab;
	//Item
	public static Item itemHunDunCrystal;
	public static Item itemHuiMieCrystal;
	public static Item itemShengMingCrystal;
	public static Item itemFuZhiCrystal;
	public static Item itemHunDunEye;
	//FakePlayer
	
	//ServerSide
	public static void init(FMLPreInitializationEvent event){
		//Dimension
		dimensionID = FuZhuMod.config.getHunDun_Dimension_ID();
		//Block
		blockHunDunCrystal = new BlockHunDunCrystal();
		blockHunDunTable = new BlockHunDunTable();
		blockHunDunCao = new BlockHunDunCao();
		blockHunDunPortal = new BlockHunDunPortal();
		blockHunDunStone = new BlockHunDunStone();
		blockHunDunStoneStairs = new BlockStairsBase(blockHunDunStone, 0).setBlockName("blockHunDunStoneStairs");
		blockHunDunStoneSlab = new BlockSlabBase(blockHunDunStone, 0, false).setBlockName("blockHunDunStoneSlab");
		//Item
		itemHunDunCrystal = new ItemHunDunCrystal();
		itemHuiMieCrystal = new ItemHuiMieCrystal();
		itemShengMingCrystal = new ItemShengMingCrystal();
		itemFuZhiCrystal = new ItemFuZhiCrystal();
		itemHunDunEye = new ItemHunDunEye();
	}
	
	//ServerSide
	public static void register(FMLPreInitializationEvent event){
		//Dimension
		DimensionManager.registerProviderType(dimensionID, Test.WorldProviderMod.class, false);
		DimensionManager.registerDimension(dimensionID, dimensionID);
		//Block
		GameRegistry.registerBlock(blockHunDunCrystal, ItemBlockBase.class, blockHunDunCrystal.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunTable, ItemBlockBase.class, blockHunDunTable.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunCao, ItemBlockBase.class, blockHunDunCao.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunPortal, ItemBlockBase.class, blockHunDunPortal.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunStone, ItemBlockMetaBase.class, blockHunDunStone.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunStoneStairs, ItemBlockBase.class, blockHunDunStoneStairs.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunStoneSlab, ItemBlockSlabBase.class, blockHunDunStoneSlab.getUnlocalizedName());
		//Item
		GameRegistry.registerItem(itemHunDunCrystal, itemHunDunCrystal.getUnlocalizedName());
		GameRegistry.registerItem(itemHuiMieCrystal, itemHuiMieCrystal.getUnlocalizedName());
		GameRegistry.registerItem(itemShengMingCrystal, itemShengMingCrystal.getUnlocalizedName());
		GameRegistry.registerItem(itemFuZhiCrystal, itemFuZhiCrystal.getUnlocalizedName());
		GameRegistry.registerItem(itemHunDunEye, itemHunDunEye.getUnlocalizedName());
		//Entity
//		EntityRegistry.registerModEntity(new ResourceLocation(Config.MODID + ":entitysupersnowman"), EntitySuperSnowman.class, "entitySuperSnowman", 263, ZijingMod.instance,64, 1, true, (204 << 16) + (0 << 8) + 204, (255 << 16) + (102 << 8) + 255);
		//Message
    	netWorkWrapper.registerMessage(ClientToServerMessage.Handler.class, ClientToServerMessage.class, nextID++, Side.SERVER);
//    	netWorkWrapper.registerMessage(ShepherdToClientMessage.Handler.class, ShepherdToClientMessage.class, nextID++, Side.CLIENT);
		//Event
		MinecraftForge.EVENT_BUS.register(new FuZhuEvent());
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
		addRecipe(new ItemStack(blockHunDunCrystal, 1), itemHunDunCrystal, itemHunDunCrystal, itemHunDunCrystal, itemHunDunCrystal, itemHunDunCrystal, itemHunDunCrystal, itemHunDunCrystal, itemHunDunCrystal, itemHunDunCrystal);
		addRecipe(new ItemStack(blockHunDunTable, 1), itemHunDunCrystal, itemHunDunCrystal, itemHunDunCrystal, Blocks.obsidian, Blocks.crafting_table, Blocks.obsidian, Blocks.obsidian, blockHunDunCrystal, Blocks.obsidian);

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

	//*****************************************************************************************************************************************************//
	
	//Orderly synthesis method
	private static void addRecipe(ItemStack itemStack, Object item1,Object item2,Object item3,Object item4,Object item5,Object item6,Object item7,Object item8,Object item9){
		Object[] object = new Object[]{"012", "345", "678",
			Character.valueOf('0'), item1, Character.valueOf('1'), item2,
			Character.valueOf('2'), item3, Character.valueOf('3'), item4,
			Character.valueOf('4'), item5, Character.valueOf('5'), item6,
			Character.valueOf('6'), item7, Character.valueOf('7'), item8,
			Character.valueOf('8'), item9
		};
		GameRegistry.addShapedRecipe(itemStack, object);
	}
}
