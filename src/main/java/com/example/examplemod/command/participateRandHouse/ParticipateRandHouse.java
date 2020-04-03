package com.example.examplemod.command.participateRandHouse;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.capability.randHouse.RandHouse;
import com.example.examplemod.capability.randHouse.RandHouseI;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.server.permission.PermissionAPI;

public class ParticipateRandHouse {
	
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> builder = Commands.literal("participateRandHouse");
		
		builder.requires(source -> source.hasPermissionLevel(2));
		
		builder.executes(ParticipateRandHouse::participate);
		
		LiteralCommandNode<CommandSource> participateRandHouseCommand = dispatcher.register(builder);
	}
	
	public static int participate(CommandContext<CommandSource> context) {
		try {
			ServerPlayerEntity player = context.getSource().asPlayer();
			
			RandHouseI randHouse = player.getCapability(RandHouse.RAND_HOUSE_CAPABILITY).orElse(null);
			
			if (randHouse != null)
				randHouse.setParticipating(!randHouse.getParticipating());
			
		} catch (Exception ex) {
			ExampleMod.LOGGER.info("PARTICIPATE COMMMAND ERROR: " + ex.getMessage());
		}
		
		return 1;
	}
	
}
