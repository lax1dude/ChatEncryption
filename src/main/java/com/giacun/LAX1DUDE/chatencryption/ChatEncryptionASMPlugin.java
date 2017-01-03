package com.giacun.LAX1DUDE.chatencryption;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.11.2")
@IFMLLoadingPlugin.TransformerExclusions({"com.giacun.LAX1DUDE.chatencryption"})
public class ChatEncryptionASMPlugin implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"com.giacun.LAX1DUDE.chatencryption.ChatEncryptionASMTransformer"};
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
