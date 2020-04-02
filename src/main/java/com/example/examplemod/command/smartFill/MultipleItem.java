package com.example.examplemod.command.smartFill;

import net.minecraft.item.Item;

public class MultipleItem implements ItemCategory {
	
	private Item[] multiple;
	
	public MultipleItem(Item... multiple) {
		this.multiple = multiple;
	}
	
	public Item get() {
		return multiple[(int)(Math.random() * multiple.length)];
	}
	
}
