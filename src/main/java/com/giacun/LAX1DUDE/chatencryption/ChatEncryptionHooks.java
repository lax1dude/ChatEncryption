package com.giacun.LAX1DUDE.chatencryption;

import java.lang.reflect.Field;

import org.apache.commons.lang3.ArrayUtils;

import com.giacun.LAX1DUDE.chatencryption.util.EncryptUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.util.text.event.HoverEvent.Action;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class ChatEncryptionHooks {
	public static String key = "uuvler";
	public static boolean show = true;
	public static boolean top = true;
	public static boolean left = true;
	public static float scale = 1.0F;
	
	@SubscribeEvent
	public void keypressed(KeyInputEvent ev){
		if(Minecraft.getMinecraft().currentScreen == null && ChatEncryption.key.isKeyDown()){
			GuiChat chat = new GuiChat("enc:;");
			Minecraft.getMinecraft().displayGuiScreen(chat);
			
			Field chatField;
			try {
				try{
					chatField = GuiChat.class.getDeclaredField("inputField");
				}catch(Throwable t){
					chatField = GuiChat.class.getDeclaredField("field_146415_a");
				}
				chatField.setAccessible(true);
				((GuiTextField)chatField.get(chat)).setCursorPosition(4);
			}catch(Throwable t){t.printStackTrace();}
		}
	}

	public static String encrypt(String str) {
		String orig = str;
		if(str.contains("enc:")){
			try{
				int index = 0;
				while((index = str.indexOf("enc:")) != -1){
					String str2 = str.substring(index+4, str.indexOf(";", index));
					String str3;
					try {
						str3 = EncryptUtils.encryptAES(str2.concat(":>"), key);
					} catch (Throwable t){
						t.printStackTrace();
						Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("Encryption failed: "+t.toString()+". Check your console for more info"));
						return "hiiii guys!";
					}
					str = str.substring(0, index).concat(str.substring(index).replaceFirst("enc:"+str2+";", "dnc:"+str3+";"));
				}
				if(str.length() > 256){
					Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("The encrypted string is above 256 characters. Please shorten your message"));
					return "what the";
				}
				return str;
			}
			catch(Throwable t){
				t.printStackTrace();
				Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("Encryption failed: Invalid format caused a "+t.toString()+". Check your console for more info"));
				return "oops";
			}
		}
		return str;
	}

	@SubscribeEvent
	public void processIncoming(ClientChatReceivedEvent c) {
		String str = c.getMessage().getFormattedText().trim();
		String orig = c.getMessage().getUnformattedText().trim();
		if(str.contains("dnc:")){
			try{
				int index = 0;
				while((index = str.indexOf("dnc:")) != -1){
					String str2 = str.substring(str.indexOf("dnc:", index)+4, str.indexOf(";", index));
					String str3;
					try {
						str3 = EncryptUtils.decryptAES(str2, key);
						if(!str3.endsWith(":>")){
							return;
						}
						str3 = str3.substring(0, str3.length() - 2);
					} catch (Throwable e) {
						e.printStackTrace();
						Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("Decryption failed: "+e.toString()+". Check your console for more info"));
						return;
					}
					System.out.println(str3);
					str = str.substring(0, index).concat(str.substring(index).replace("dnc:"+str2+";", str3));
				}
				c.setMessage(new TextComponentString(str+" ").appendSibling(new TextComponentString("[Decrypted]")
						.setStyle(new Style().setColor(TextFormatting.GOLD)
						.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new TextComponentString("Original Text: "+orig.substring(0, orig.length() >= 39 ? 40 : orig.length())+"...\n")
								.setStyle(new Style().setColor(TextFormatting.DARK_GREEN))
								.appendSibling(new TextComponentString("The decryption may have interfered with\n")
										.setStyle(new Style().setItalic(Boolean.TRUE).setColor(TextFormatting.BLUE)).appendSibling(new TextComponentString("certain aspects of the text formatting"))))))));
			}
			catch(Throwable t){
				t.printStackTrace();
				Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("Decryption failed: Invalid format caused a "+t.toString()+". Check your console for more info"));
			}
		}
	}
	
	@SubscribeEvent
	public void renderOverlay(RenderTickEvent event){
		Minecraft mc = Minecraft.getMinecraft();
		if(show && event.phase.equals(Phase.END) && !mc.skipRenderWorld && mc.world != null && !mc.gameSettings.hideGUI && (mc.currentScreen == null || !mc.currentScreen.doesGuiPauseGame())){
			ScaledResolution res = new ScaledResolution(mc);
			mc.mcProfiler.startSection("gameRenderer");
			mc.mcProfiler.startSection("gui");
			
			GlStateManager.pushMatrix();
			
			String text1 = "ChatEncryption Key: "+key;
			String text2 = "Go to 'Mod Options > ChatEncryption' in the escape menu to change";
			int t1 = mc.fontRenderer.getStringWidth(text1);
			int t2 = (int) (mc.fontRenderer.getStringWidth(text2) * 0.5F) + 1;
			float scale = 0.75F * ChatEncryptionHooks.scale;
			
			GlStateManager.translate(left ? 0 : res.getScaledWidth() - (t2 * scale) - 1, top ? 0 : res.getScaledHeight() - (16 * scale) - 1, 0.0F);

			GlStateManager.pushMatrix();
			GlStateManager.scale(scale, scale, 1.0F);
			mc.fontRenderer.drawString(text1, left ? 1 : t2 - t1 + 1, 1, 0xEEDD00);
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.5F, 0.5F, 1.0F);
			mc.fontRenderer.drawString(text2, 2, 20, 0xEEDD00);
			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
			
			GlStateManager.popMatrix();
			
			mc.mcProfiler.endSection();
			mc.mcProfiler.endSection();
		}
	}
}
