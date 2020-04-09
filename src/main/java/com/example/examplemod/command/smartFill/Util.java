package com.example.examplemod.command.smartFill;

import com.example.examplemod.TextGenerator;
import net.minecraft.nbt.CompoundNBT;

import static com.example.examplemod.Reference.randFromArray;
import static com.example.examplemod.Reference.random;
import static com.example.examplemod.command.smartFill.ItemCategory.itemCateogories;

public class Util {
	/**
	 * makes a string formatted for nbt
	 */
	static String createTextEntry(String text) {
		return "{\"text\":\"" + text + "\"}";
	}
	
	/**
	 * gives a custom name tag to a container
	 *
	 * nbt passed in should be the blockEntityTag
	 */
	static void addCustomName(CompoundNBT nbt) {
		nbt.putString("CustomName", createTextEntry(TextGenerator.getWords(1, 3, 250)));
	}
	
	/**
	 * gives a custom name to the item
	 *
	 * should go on the base tag
	 */
	static void addCustomDisplay(CompoundNBT nbt) {
		CompoundNBT display = new CompoundNBT();
		
		display.putString("Name", createTextEntry(TextGenerator.getWords(1, 3, 250)));
		
		nbt.put("display", display);
	}
	
	static CompoundNBT getItemNbt(int slot) {
		CompoundNBT item = new CompoundNBT();
		
		item.putInt("Slot", slot);
		item.putString("id", randFromArray(itemCateogories).get().getRegistryName().getPath());
		item.putInt("Count", random(1, 64));
		
		return item;
	}
	
}
