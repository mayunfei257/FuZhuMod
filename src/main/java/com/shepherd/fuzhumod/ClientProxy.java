package com.shepherd.fuzhumod;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
	
	public void preInit(FMLPreInitializationEvent event){
		super.preInit(event);
		BaseControl.resourceLoad(event);
	}
	
	public void init(FMLInitializationEvent event){
		super.init(event);
		BaseControl.renderLoad(event);
	}
	
	public void postinit(FMLPostInitializationEvent event){
		super.postinit(event);
	}
	
	@Override
	public boolean isServerSider() {
		return false;
	}
}
