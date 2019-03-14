package com.shepherd.fuzhumod;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event){
		BaseControl.init(event);
		BaseControl.register(event);
	}
	
	public void init(FMLInitializationEvent event){
		BaseControl.registerRecipe(event);
//		NetworkRegistry.INSTANCE.registerGuiHandler(ZijingMod.instance, new ZijingGuiHandler());
	}
	
	public void postinit(FMLPostInitializationEvent event){
        
    }
	
	public boolean isServerSider() {
		return true;
	}

}
