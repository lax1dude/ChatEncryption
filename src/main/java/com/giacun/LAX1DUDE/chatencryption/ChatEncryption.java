package com.giacun.LAX1DUDE.chatencryption;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.config.Property.Type;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "chatencryption", name="ChatEncryption", version="1.0", clientSideOnly=true, guiFactory="com.giacun.LAX1DUDE.chatencryption.ChatEncryptionGuiFactory")
public class ChatEncryption {
	
	public static final Logger LOGGER = LogManager.getLogger("ChatEncryption");
	
	@Mod.Instance(value = "chatencryption")
	public static ChatEncryption chatEncryption;
	
	public static Configuration cfg;

	public static ArrayList<IConfigElement> cfgList = new ArrayList();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		cfg = new Configuration(event.getSuggestedConfigurationFile());
		cfg.load();
		ConfigCategory cat = cfg.getCategory("general");
		cat.setComment("Basic Settings For ChatEncryption");
		cfgList.add(new ConfigElement(cat));
		reloadCfg();
    }
	
	public static void reloadCfg(){
		ChatEncryptionHooks.key = cfg.getString("Encryption Key", "general", "uuvler", "Change this to whatever you and your friends want to use");
		ChatEncryptionHooks.show = cfg.getBoolean("Show Key", "general", true, "Show the current encryption key while ingame");
		ChatEncryptionHooks.top = cfg.getBoolean("Display key at top of screen", "general", true, "");
		ChatEncryptionHooks.left = cfg.getBoolean("Display key on the left side of screen", "general", true, "");
		ChatEncryptionHooks.scale = cfg.getFloat("Key scale", "general", 1.0F, 0.1F, 10.0F, "");
	}
	
	@EventHandler
    public void load(FMLInitializationEvent event)
    {
		MinecraftForge.EVENT_BUS.register(new ChatEncryptionHooks());
		LOGGER.info("Thanks for using ChatEncryption!");
    }
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
		if(cfg.hasChanged()){
			cfg.save();
		}
    }
}
