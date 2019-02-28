package com.shepherd.fuzhumod;

import com.shepherd.fuzhumod.block.BlockGuhuaNiunaiKuai;
import com.shepherd.fuzhumod.item.ItemGuhuaNiunai;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BaseControl {

    private static int nextID = 0;
	public static SimpleNetworkWrapper netWorkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Config.MODID);
	
	//TODO Instantiate mod item ---
	public static Block blockGuhuaNiunaiKuai;
	
	//item
	public static Item itemGuhuaNiunai;

	public static void init(FMLPreInitializationEvent event){
    	
		//TODO Instantiate mod item ---
		blockGuhuaNiunaiKuai = new BlockGuhuaNiunaiKuai();
		//item
		itemGuhuaNiunai = new ItemGuhuaNiunai();
	}
	
	public static void register(FMLPreInitializationEvent event){
		//TODO In this registration items and blocks ---
//    	netWorkWrapper.registerMessage(ChuansongCardToServerMessage.Handler.class, ChuansongCardToServerMessage.class, nextID++, Side.SERVER);
//    	netWorkWrapper.registerMessage(ShepherdToClientMessage.Handler.class, ShepherdToClientMessage.class, nextID++, Side.CLIENT);
    	
//		ForgeRegistries.BLOCKS.register(blockGuhuaNiunaiKuai);
//		ForgeRegistries.ITEMS.register(new ItemBlock(blockGuhuaNiunaiKuai).setRegistryName(blockGuhuaNiunaiKuai.getRegistryName()));
//		GameData.register_impl(blockGuhuaNiunaiKuai);
//		GameData.register_impl(new ItemBlock(blockGuhuaNiunaiKuai).setRegistryName(blockGuhuaNiunaiKuai.getRegistryName()));

//		ForgeRegistries.ITEMS.register(itemGuhuaNiunai);
		//item
//		GameData.register_impl(itemGuhuaNiunai);

		
//		EntityRegistry.registerModEntity(new ResourceLocation(Config.MODID + ":entitysupersnowman"), EntitySuperSnowman.class, "entitySuperSnowman", 263, ZijingMod.instance,64, 1, true, (204 << 16) + (0 << 8) + 204, (255 << 16) + (102 << 8) + 255);
	}
    
    public static void resourceLoad(FMLPreInitializationEvent event){
    	//TODO Register Rendering ---
		bolckResourceLoad(blockGuhuaNiunaiKuai);
		//item
		itemResourceLoad(itemGuhuaNiunai);
    }
	
	public static void registerRecipe(FMLInitializationEvent event){
		//TODO Register synthetic methods and burn
		addShapelessRecipe(Config.MODID + ":HC_itemGuhuaNiunai", Config.MODID, new ItemStack(itemGuhuaNiunai, 1), Items.milk_bucket);
		addShapelessRecipe(Config.MODID + ":FJ_blockGuhuaNiunaiKuai", Config.MODID, new ItemStack(itemGuhuaNiunai, 9), blockGuhuaNiunaiKuai);
		addShapelessRecipe(Config.MODID + ":FJ_itemGuhuaNiunai", Config.MODID, new ItemStack(Items.milk_bucket, 1), itemGuhuaNiunai, Items.bucket);
		addRecipe(Config.MODID + ":HC_blockGuhuaNiunaiKuai", Config.MODID, new ItemStack(blockGuhuaNiunaiKuai, 1), itemGuhuaNiunai, itemGuhuaNiunai, itemGuhuaNiunai, itemGuhuaNiunai, itemGuhuaNiunai, itemGuhuaNiunai, itemGuhuaNiunai, itemGuhuaNiunai, itemGuhuaNiunai);
		
		addSmelting(Blocks.gravel, new ItemStack(Items.flint, 1), 1);
		addShapelessRecipe(Config.MODID + ":HC_GUNPOWDER1", "custom", new ItemStack(Items.gunpowder, 3), new ItemStack(Items.flint, 1), new ItemStack(Items.dye, 1, 15), new ItemStack(Items.coal, 1));
		addShapelessRecipe(Config.MODID + ":HC_GUNPOWDER2", "custom", new ItemStack(Items.gunpowder, 3), new ItemStack(Items.flint, 1), new ItemStack(Items.dye, 1, 15),new ItemStack(Items.coal, 1, 1));
		addShapelessRecipe(Config.MODID + ":FJ_CLAY", "custom", new ItemStack(Items.clay_ball, 4), Blocks.clay);
		addShapelessRecipe(Config.MODID + ":FJ_QUARTZ_BLOCK", "custom", new ItemStack(Items.quartz, 4), Blocks.quartz_block);
		addShapelessRecipe(Config.MODID + ":FJ_WOOL", "custom", new ItemStack(Items.string, 4), Blocks.wool);
		addShapelessRecipe(Config.MODID + ":FJ_GLOWSTONE", "custom", new ItemStack(Items.glowstone_dust, 4), Blocks.glowstone);
		addShapelessRecipe(Config.MODID + ":FJ_MELON_BLOCK", "custom", new ItemStack(Items.melon, 9), Blocks.melon_block);
		addShapelessRecipe(Config.MODID + ":FJ_SNOW_BLOCK", "custom", new ItemStack(Items.snowball, 4), Blocks.snow);
		
	}
	
	public static void renderLoad(FMLInitializationEvent event){
//		RenderingRegistry.registerEntityRenderingHandler(EntitySuperSnowman.class, new RenderSuperSnowman(Minecraft.getMinecraft().getRenderManager()));
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
  	//Unordered synthetic method
  	private static void addShapelessRecipe(String nameStr, String groupStr, ItemStack itemStack, Object... items){
//  		Ingredient[] ingredient = new Ingredient[items.length];
//  		for (int i = 0; i < items.length; ++i){
//  			if(items[i] instanceof Block) {
//  	  			ingredient[i] = Ingredient.fromStacks(new ItemStack(Item.getItemFromBlock((Block)items[i]), 1));
//  			}else if(items[i] instanceof Item){
//  	  			ingredient[i] = Ingredient.fromStacks(new ItemStack((Item)items[i], 1));
//  			}else if(items[i] instanceof ItemStack){
//  	  			ingredient[i] = Ingredient.fromStacks((ItemStack)items[i]);
//  			}
//        }
//  		GameRegistry.addShapelessRecipe(new ResourceLocation(nameStr), new ResourceLocation(groupStr), itemStack, ingredient);
  	}
	//Orderly synthesis method
	private static void addRecipe(String nameStr, String groupStr, ItemStack itemStack, Object item1,Object item2,Object item3,Object item4,Object item5,Object item6,Object item7,Object item8,Object item9){
//		Object[] object = new Object[]{"012", "345", "678",
//			Character.valueOf('0'), null == item1 ? Items.AIR : item1, Character.valueOf('1'), null == item2 ? Items.AIR : item2,
//			Character.valueOf('2'), null == item3 ? Items.AIR : item3, Character.valueOf('3'), null == item4 ? Items.AIR : item4,
//			Character.valueOf('4'), null == item5 ? Items.AIR : item5, Character.valueOf('5'), null == item6 ? Items.AIR : item6,
//			Character.valueOf('6'), null == item7 ? Items.AIR : item7, Character.valueOf('7'), null == item8 ? Items.AIR : item8,
//			Character.valueOf('8'), null == item9 ? Items.AIR : item9
//		};
//		GameRegistry.addShapedRecipe(new ResourceLocation(nameStr), new ResourceLocation(groupStr), itemStack, object);
	}
	//Burned
	private static void addSmelting(Object item, ItemStack itemStack, float xp){
		if(item instanceof Item)
			GameRegistry.addSmelting((Item)item, itemStack, xp);
		else if(item instanceof Block)
			GameRegistry.addSmelting((Block)item, itemStack, xp);
	}
}
