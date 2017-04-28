package com.giacun.LAX1DUDE.chatencryption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModClassLoader;
import net.minecraftforge.fml.common.discovery.ModCandidate;
import net.minecraftforge.fml.common.discovery.ModDiscoverer;
import net.minecraftforge.fml.relauncher.FMLRelaunchLog;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.11.2")
public class ChatEncryptionASMPlugin implements IFMLLoadingPlugin {
	
	public ChatEncryptionASMPlugin(){
		try{
			Field f = Loader.class.getDeclaredField("minecraftDir");
			f.setAccessible(true);
			File mcd = (File) f.get(null);
			File out = new File(mcd, "mods\\ChatEncryption-1.0-DATA.jar");
			if(!out.exists()){
				ChatEncryptionASMTransformer.TransformLogger.info("Extracting Actual Mod...");
				InputStream stream = null;
		        OutputStream resStreamOut = null;
		        String jarFolder;
		        try {
		            stream = ChatEncryptionASMPlugin.class.getResourceAsStream("/ChatEncryption-1.0-DATA.jar");
	
		            int readBytes;
		            byte[] buffer = new byte[4096];
		            resStreamOut = new FileOutputStream(out);
		            while ((readBytes = stream.read(buffer)) > 0) {
		                resStreamOut.write(buffer, 0, readBytes);
		            }
		            stream.close();
		            resStreamOut.close();
		        } catch (IOException ex) {
		            throw ex;
		        } finally {
		            stream.close();
		            resStreamOut.close();
		        }
			}
			
			Field f43553445 = Loader.class.getDeclaredField("modClassLoader");
            f43553445.setAccessible(true);
            ((ModClassLoader)f43553445.get(Loader.instance())).addFile(out);
		}catch(Exception e){
			e.printStackTrace();
			FMLCommonHandler.instance().exitJava(-1, false);
		}
	}
	
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
