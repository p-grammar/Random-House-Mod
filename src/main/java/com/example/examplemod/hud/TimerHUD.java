package com.example.examplemod.hud;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Reference;
import com.example.examplemod.capability.randHouse.RandHouse;
import com.example.examplemod.capability.randHouse.RandHouseI;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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

import java.util.concurrent.Executor;

import static net.minecraftforge.fml.client.gui.GuiUtils.drawTexturedModalRect;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class TimerHUD {
	
	private static PlayerEntity player;
	
	public static final ResourceLocation barResource = new ResourceLocation("minecraft:textures/gui/icons.png");
	public static final ResourceLocation customBarResource = new ResourceLocation("randomhousemod:textures/timerbar.png");
	
	/**
	 * registers the timer HUD
	 */
	public static void register() {
		MinecraftForge.EVENT_BUS.register(TimerHUD.class);
		
		Minecraft.getInstance().getRenderManager().textureManager.loadAsync(customBarResource, new Executor() {
			@Override
			public void execute(Runnable command) {
			
			}
		});
	}
	
	@SubscribeEvent
	public static void onLogIn(PlayerEvent.PlayerLoggedInEvent event) {
		PlayerEntity loggedPlayer = event.getPlayer();
		
		//if (loggedPlayer.isUser())
		//if (loggedPlayer.getName().equals(Minecraft.getInstance().getName())) {
			player = loggedPlayer;
		//}
		ExampleMod.LOGGER.info("RESOURCE YAYA: " + barResource.getPath());
	}
	
	/* events */
	
	@SubscribeEvent
	public static void onRenderOverlay(RenderGameOverlayEvent event) {
		if (player == null)
			return;
		
		RandHouseI randHouse = player.getCapability(RandHouse.RAND_HOUSE_CAPABILITY).orElse(null);
		
		/* if somehow this player doesn't have random house capability, exit */
		if (randHouse == null)
			return;
		
		EntityRendererManager renderManager = Minecraft.getInstance().getRenderManager();
		TextureManager textureManager = renderManager.textureManager;
		FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
		
		RenderSystem.enableAlphaTest();
		RenderSystem.enableBlend();
		
		/* render bar */
		textureManager.bindTexture(barResource);
		
		double scale = event.getWindow().getGuiScaleFactor();
		
		int width = event.getWindow().getScaledWidth();
		int height = event.getWindow().getScaledHeight();
		
		int barWidth = 182;
		int barHeight = 5;
		
		int barX = (int)((width / 2.0f) - (barWidth / 2.0f));
		int barY = height - 22 - barHeight;
		
		/* draw back of bar */
		drawTexturedModalRect(barX, barY, 0, 69, barWidth, barHeight, 0);
		
		/* draw active bar */
		float barFill = (float)randHouse.getTimeLeft() / randHouse.getMaxTime();
		int newBarWidth = (int)(barWidth * barFill);
		int newBarX = (int)((width / 2.0f) - (newBarWidth / 2.0f));
		
		drawTexturedModalRect(newBarX, barY, 0, 64, newBarWidth, barHeight, 0);
		
		/* render points */
		int points = randHouse.getPoints();
		
		String pointString = "Points: " + String.valueOf(points);
		fontRenderer.drawString(pointString, barX, barY - 16, 0xfc6203);
		
		RenderSystem.enableAlphaTest();
		RenderSystem.enableBlend();
	}
	
}
