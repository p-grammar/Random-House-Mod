package com.example.examplemod.command.participateRandHouse;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.capability.randHouse.RandHouse;
import com.example.examplemod.capability.randHouse.RandHouseI;
import com.example.examplemod.command.smartFill.SmartFill;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
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
			
			if (randHouse != null) {
				if (randHouse.getParticipating()) {
					/* clear when you're out */
					for (int i = 0; i < 9; ++i) {
						player.inventory.removeStackFromSlot(i);
					}
					player.setItemStackToSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
					
					randHouse.setParticipating(false);
					
				} else {
					/* initial fill */
					SmartFill.smartFillExec(player);
					
					randHouse.setParticipating(true);
				}
			}
			
		} catch (Exception ex) {
			ExampleMod.LOGGER.info("PARTICIPATE COMMMAND ERROR: " + ex.getMessage());
		}
		
		return 1;
	}
	
}
