package com.example.examplemod.command.resetRandHouse;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.capability.randHouse.RandHouse;
import com.example.examplemod.capability.randHouse.RandHouseI;
import com.example.examplemod.capability.randHouse.RandHouseImpl;
import com.example.examplemod.command.smartFill.ItemCategory;
import com.example.examplemod.command.smartFill.MultipleItem;
import com.example.examplemod.command.smartFill.SingleItem;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;

import java.util.Collection;
import java.util.List;

public class ResetRandHouse {
	
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> builder = Commands.literal("resetRandHouse");
		
		builder.requires(source -> source.hasPermissionLevel(2));
		
		builder.executes(ResetRandHouse::reset);
		
		LiteralCommandNode<CommandSource> resetRandHouseCommand = dispatcher.register(builder);
	}
	
	public static int reset(CommandContext<CommandSource> context) {
		try {
			ServerPlayerEntity player = context.getSource().asPlayer();
			
			RandHouseI randHouse = player.getCapability(RandHouse.RAND_HOUSE_CAPABILITY).orElse(null);
			
			if (randHouse != null)
				randHouse.reset();
				
		} catch (Exception ex) {
			ExampleMod.LOGGER.info("RESET COMMMAND ERROR: " + ex.getMessage());
		}
		
		return 1;
	}
	
}
