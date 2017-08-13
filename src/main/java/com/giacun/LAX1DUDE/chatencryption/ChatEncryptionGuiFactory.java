package com.giacun.LAX1DUDE.chatencryption;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ChatEncryptionGuiFactory implements IModGuiFactory {

	@Override public void initialize(Minecraft minecraftInstance) {}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}
	
	public static class ChatEncryptionGui extends GuiConfig{

		public ChatEncryptionGui(GuiScreen parentScreen) {
			super(parentScreen, ChatEncryption.cfgList, "chatencryption", false, false, "ChatEncryption Config", ChatEncryption.cfg.getConfigFile().getAbsolutePath());
		}
		
		public void onGuiClosed(){
			super.onGuiClosed();
			if(ChatEncryption.cfg.hasChanged()){
				ChatEncryption.reloadCfg();
				ChatEncryption.cfg.save();
			}
		}
		
	}

	@Override
	public boolean hasConfigGui() {
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		return new ChatEncryptionGui(parentScreen);
	}

}
