package com.example.examplemod.command.smartFill;

import com.example.examplemod.AllItems;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.TextGenerator;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.datafixers.kinds.IdF;
import com.mojang.datafixers.optics.Lens;
import com.sun.org.apache.xpath.internal.operations.Mult;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SignItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.datafix.fixes.SpawnerEntityTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import static com.example.examplemod.command.smartFill.ItemCategory.*;
import static com.example.examplemod.command.smartFill.Util.*;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.lang.reflect.Field;
import java.util.*;

import static com.example.examplemod.Reference.randFromArray;
import static com.example.examplemod.command.smartFill.Util.addCustomDisplay;

public class SmartFill {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> builder = Commands.literal("smartFill");
		
		builder.requires(source -> source.hasPermissionLevel(2));
		
		
		builder.then(Commands.argument("players", EntityArgument.players()).executes(SmartFill::smartFill))
			.executes(SmartFill::smartFillNoArgs);
		
		LiteralCommandNode<CommandSource> smartFillCommand = dispatcher.register(builder);
		
		ItemCategory.init();
	}
	
	private static int smartFill(CommandContext<CommandSource> context) {
		try {
			Collection<ServerPlayerEntity> players = EntityArgument.getPlayers(context, "players");
			
			for (ServerPlayerEntity player : players) {
				smartFillExec(player);
			}
			
		} catch (Exception ex) {
			ExampleMod.LOGGER.info("EXCEPTION " + ex.getMessage());
			
			ex.printStackTrace();
		}
		
		return 1;
	}
	
	private static int smartFillNoArgs(CommandContext<CommandSource> context) {
		try {
			ServerPlayerEntity player = context.getSource().asPlayer();
			
			smartFillExec(player);
			
		} catch (Exception ex) {
			ExampleMod.LOGGER.info("EXCEPTION " + ex.getMessage());
			
			ex.printStackTrace();
		}
		
		return 1;
	}
	
	/**
	 * sees if we already have this item in our inventory,
	 * or have already added it in this smart fill
	 *
	 * returning true means that the current item should be discarded
	 */
	private static boolean itemAlready(Item item, Item[] oldItems, Item[] newItems, int current) {
		for (int j = 0; j < 10; ++j) {
			if (item == oldItems[j])
				return true;
		}
		
		/* make sure we haven't seen it yet */
		for (int j = 0; j < current; ++j) {
			if (item == newItems[j])
				return true;
		}
		
		return false;
	}
	
	public static void smartFillExec(ServerPlayerEntity player) {
		Item[] oldItems = new Item[10];
		
		/* fill oldItems with current hotbar and offhand */
		for (int i = 0; i < 9; ++i) {
			oldItems[i] = player.inventory.getStackInSlot(i).getItem();
		}
		oldItems[9] = player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem();
		
		/* keep track of what we have found so far */
		Item[] newItems = new Item[10];
		
		/* fill each inventory slot */
		for (int i = 0; i < 10; ++i) {
			Item selectedItem;
			ItemCategory selectedCategory;
			
			//do {
				/* grab a random item */
			//	selectedCategory = randFromArray(ItemCategory.itemCateogories);
			//	selectedItem = selectedCategory.get();
//
			//} while (itemAlready(selectedItem, oldItems, newItems, i));
			
			ItemCategory[] cats = new ItemCategory[] {
				chestCategory,
				enderTableCategory,
				furnaceCategory,
				brewingCategory,
				musicBlockCategory
			};
			
			// DEBUG TEST
			selectedCategory = randFromArray(cats);
			//selectedCategory = signCategory;
			selectedItem = selectedCategory.get();
			
			/* store which item we got */
			newItems[i] = selectedItem;
			
			ItemStack stack = new ItemStack(selectedItem);
			
			CompoundNBT nbt = selectedCategory.getNBT(player, stack);
			
			if (Math.random() < 0.05)
				addCustomDisplay(nbt);
			
			stack.setTag(nbt);
			
			if (i == 9) {
				/* place stack in offhand */
				//if(player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).isEmpty())
				
				player.setItemStackToSlot(EquipmentSlotType.OFFHAND, stack);
				
			} else {
				/* place stack in inventory slot */
				if(!player.inventory.getStackInSlot(i).isEmpty())
					player.inventory.removeStackFromSlot(i);
				
				player.inventory.add(i, stack);
			}
		}
	}
	
}
