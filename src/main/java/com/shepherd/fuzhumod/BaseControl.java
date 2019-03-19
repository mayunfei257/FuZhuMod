package com.shepherd.fuzhumod;

import com.shepherd.fuzhumod.base.Config;
import com.shepherd.fuzhumod.base.FuZhuTab;
import com.shepherd.fuzhumod.base.FuZhuEvent;
import com.shepherd.fuzhumod.base.ZijingGuiHandler;
import com.shepherd.fuzhumod.block.BlockHunDunTable;
import com.shepherd.fuzhumod.block.BlockHunDunCrystal;
import com.shepherd.fuzhumod.block.BlockHunDunCao;
import com.shepherd.fuzhumod.block.BlockHunDunCrystal.ItemBlockHunDunCrystal;
import com.shepherd.fuzhumod.entity.TileEntityHunDunTable;
import com.shepherd.fuzhumod.item.ItemFuZhiCrystal;
import com.shepherd.fuzhumod.item.ItemHuiMieCrystal;
import com.shepherd.fuzhumod.item.ItemHunDunCrystal;
import com.shepherd.fuzhumod.item.ItemShengMingCrystal;
import com.shepherd.fuzhumod.message.ClientToServerMessage;

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
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public class BaseControl {

    private static int nextID = 0;
	public static SimpleNetworkWrapper netWorkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Config.MODID);
	//Tab
	public static final CreativeTabs fuZhuTab = new FuZhuTab();
	
	//Block
	public static Block blockHunDunCrystal;
	public static Block blockHunDunTable;
	public static Block blockHunDunCao;
	//FakePlayer
	//Item
	public static Item itemHunDunCrystal;
	public static Item itemHuiMieCrystal;
	public static Item itemShengMingCrystal;
	public static Item itemFuZhiCrystal;

	//ServerSide
	public static void init(FMLPreInitializationEvent event){
		//Block
		blockHunDunCrystal = new BlockHunDunCrystal();
		blockHunDunTable = new BlockHunDunTable();
		blockHunDunCao = new BlockHunDunCao();
		//Item
		itemHunDunCrystal = new ItemHunDunCrystal();
		itemHuiMieCrystal = new ItemHuiMieCrystal();
		itemShengMingCrystal = new ItemShengMingCrystal();
		itemFuZhiCrystal = new ItemFuZhiCrystal();
	}
	
	//ServerSide
	public static void register(FMLPreInitializationEvent event){
		//Block
		GameRegistry.registerBlock(blockHunDunCrystal, BlockHunDunCrystal.ItemBlockHunDunCrystal.class, blockHunDunCrystal.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunTable, BlockHunDunTable.ItemBlockHunDunTable.class, blockHunDunTable.getUnlocalizedName());
		GameRegistry.registerBlock(blockHunDunCao, BlockHunDunCao.ItemBlocHunDunCao.class, blockHunDunCao.getUnlocalizedName());
		//Item
		GameRegistry.registerItem(itemHunDunCrystal, itemHunDunCrystal.getUnlocalizedName());
		GameRegistry.registerItem(itemHuiMieCrystal, itemHuiMieCrystal.getUnlocalizedName());
		GameRegistry.registerItem(itemShengMingCrystal, itemShengMingCrystal.getUnlocalizedName());
		GameRegistry.registerItem(itemFuZhiCrystal, itemFuZhiCrystal.getUnlocalizedName());
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
		bolckResourceLoad(blockHunDunCrystal);
		bolckResourceLoad(blockHunDunTable);
		bolckResourceLoad(blockHunDunCao);
		//Item
		itemResourceLoad(itemHunDunCrystal);
		itemResourceLoad(itemHuiMieCrystal);
		itemResourceLoad(itemShengMingCrystal);
		itemResourceLoad(itemFuZhiCrystal);
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
		NetworkRegistry.INSTANCE.registerGuiHandler(FuZhuMod.instance, new ZijingGuiHandler());
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
	//Render items
  	private static void itemResourceLoad(Item item){
//  		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName() , "inventory"));
  	}
  	//Render block
  	private static void bolckResourceLoad(Block block){
//  		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName() , "inventory"));
  	}
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
