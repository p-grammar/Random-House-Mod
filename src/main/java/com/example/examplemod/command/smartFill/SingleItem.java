package com.example.examplemod.command.smartFill;

import net.minecraft.item.Item;

public class SingleItem extends ItemCategory {
	
	private Item single;
	
	public SingleItem(Item single) {
		super();
		
		this.single = single;
	}
	
	public SingleItem(CustomNBT customNBT, Item single) {
		super(customNBT);
		
		this.single = single;
	}
	
	public Item get() {
		return single;
	}
	
}
