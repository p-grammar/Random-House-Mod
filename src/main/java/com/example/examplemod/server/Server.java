package com.example.examplemod.server;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.capability.randHouse.RandHouse;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;

import java.util.List;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
public class Server {
	
	public static MinecraftServer server = null;
	
	public static void register() {
		MinecraftForge.EVENT_BUS.register(Server.class);
	}
	
	@SubscribeEvent
	public static void serverStart(FMLServerStartedEvent event) {
		server = event.getServer();
	}
	
	@SubscribeEvent
	public static void serverStart(FMLServerStoppedEvent event) {
		server = null;
	}
	
}
