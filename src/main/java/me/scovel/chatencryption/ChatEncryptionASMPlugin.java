package me.scovel.chatencryption;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class ChatEncryptionASMPlugin implements IFMLLoadingPlugin {
	
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"me.scovel.chatencryption.ChatEncryptionASMTransformer"};
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
