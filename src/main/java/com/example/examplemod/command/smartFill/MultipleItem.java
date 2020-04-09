package com.example.examplemod.command.smartFill;

import net.minecraft.item.Item;

public class MultipleItem extends ItemCategory {
	
	private Item[] multiple;
	
	public MultipleItem(Item... multiple) {
		super();
		
		this.multiple = multiple;
	}
	
	public MultipleItem(CustomNBT customNBT, Item... multiple) {
		super(customNBT);
		
		this.multiple = multiple;
	}
	
	public Item get() {
		return multiple[(int)(Math.random() * multiple.length)];
	}
	
}
