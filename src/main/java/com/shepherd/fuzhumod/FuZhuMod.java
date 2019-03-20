package com.shepherd.fuzhumod;

import com.shepherd.fuzhumod.base.Config;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Config.MODID, version = Config.VERSION)
public class FuZhuMod {

	@SidedProxy(clientSide = Config.CLIENTSIDE, serverSide = Config.SERVERSIDE)
	public static CommonProxy proxy;
	
	@Instance(Config.MODID)
	public static FuZhuMod instance;

    public static Config config;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	config = Config.getConfig(event);
        proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	proxy.init(event);
    }
    
    @EventHandler
    public void Postinit(FMLPostInitializationEvent event){
    	proxy.postinit(event);
    }
}
