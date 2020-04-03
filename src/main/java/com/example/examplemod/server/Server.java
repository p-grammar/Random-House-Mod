package com.example.examplemod.server;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.capability.randHouse.RandHouse;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;

import java.util.List;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Server {
	
	public static PlayerEntity player = null;
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
	
	@SubscribeEvent
	public static void onPlayerLogsIn(PlayerEvent.PlayerLoggedInEvent event) {
		String id = Minecraft.getInstance().getSession().getUsername();
		
		if (event.getPlayer().getName().getString().equals(id))
			player = event.getPlayer();
		
		List<ServerPlayerEntity> p = server.getPlayerList().getPlayers();
		
		ExampleMod.LOGGER.info("ENTITY: " + player);
		
		for (ServerPlayerEntity e : p) {
			ExampleMod.LOGGER.info("SERVER: " + e);
		}
	}
	
	@SubscribeEvent
	public static void onPlayerCloned(PlayerEvent.Clone event) {
		String id = Minecraft.getInstance().getSession().getUsername();
		
		if (event.getPlayer().getName().getString().equals(id))
			player = event.getPlayer();
	}
	
}
