package com.example.examplemod.hud;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Reference;
import com.example.examplemod.capability.randHouse.RandHouse;
import com.example.examplemod.capability.randHouse.RandHouseI;
import com.example.examplemod.server.Server;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.permission.context.PlayerContext;
import org.lwjgl.opengl.GL11;
import sun.java2d.pipe.RenderingEngine;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

import static net.minecraftforge.fml.client.gui.GuiUtils.drawTexturedModalRect;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class TimerHUD {
	
	public static final ResourceLocation barResource = new ResourceLocation("minecraft:textures/gui/icons.png");
	public static final ResourceLocation customBarResource = new ResourceLocation("randomhousemod:textures/timerbar.png");
	public static AtlasTexture texture = null;
	
	/**
	 * registers the timer HUD
	 */
	public static void register() {
		MinecraftForge.EVENT_BUS.register(TimerHUD.class);
	}
	
	/* events */
	
	@SubscribeEvent
	public static void onRenderOverlay(RenderGameOverlayEvent event) {
		if(texture == null) {
			ExampleMod.LOGGER.info("CREATING DA FIN TEXTURO");
			
			texture = new AtlasTexture(customBarResource);
			try {
				texture.loadTexture(Minecraft.getInstance().getResourceManager());
				texture.upload(new AtlasTexture.SheetData(new HashSet<ResourceLocation>(), 256, 256, 0, new ArrayList<TextureAtlasSprite>()));
				ExampleMod.LOGGER.info("HMHMHMMHMHMHMM  GEH SH");
			} catch (Exception ex) {
				ExampleMod.LOGGER.info(ex.getMessage());
			}
			
			ExampleMod.LOGGER.info("OHYAOHYA: " + texture.getGlTextureId());
		}
		
		if (Server.player == null)
			return;
		
		RandHouseI randHouse = Server.player.getCapability(RandHouse.RAND_HOUSE_CAPABILITY).orElse(null);
		
		if (randHouse == null)
			return;
		
		EntityRendererManager renderManager = Minecraft.getInstance().getRenderManager();
		TextureManager textureManager = renderManager.textureManager;
		FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
		
		RenderSystem.enableAlphaTest();
		RenderSystem.enableBlend();
		
		/* render bar */
		//textureManager.bindTexture(customBarResource);
		textureManager.bindTexture(customBarResource);
		
		double scale = event.getWindow().getGuiScaleFactor();
		
		int width = event.getWindow().getScaledWidth();
		int height = event.getWindow().getScaledHeight();
		
		int barWidth = 182;
		int barHeight = 5;
		
		int barX = (int)((width / 2.0f) - (barWidth / 2.0f));
		int barY = height - 22 - barHeight;
		
		/* draw back of bar */
		drawTexturedModalRect(barX, barY, 0, 0, barWidth, barHeight, 0);
		
		/* draw active bar */
		float barFill = (float)randHouse.getTimeLeft() / randHouse.getMaxTime();
		int newBarWidth = (int)(barWidth * barFill);
		int newBarX = (int)((width / 2.0f) - (newBarWidth / 2.0f));
		
		drawTexturedModalRect(newBarX, barY, 0, 5, newBarWidth, barHeight, 0);
		
		/* render points */
		int points = randHouse.getPoints();
		
		String pointString = "Points: " + String.valueOf(points);
		fontRenderer.drawString(pointString, barX, barY - 16, 0xfc6203);
		
		RenderSystem.enableAlphaTest();
		RenderSystem.enableBlend();
	}
	
}
