package com.example.examplemod.command.smartFill;

import net.minecraft.item.Item;

public class SingleItem implements ItemCategory {
	
	private Item single;
	
	public SingleItem(Item single) {
		this.single = single;
	}
	
	public Item get() {
		return single;
	}
	
}
