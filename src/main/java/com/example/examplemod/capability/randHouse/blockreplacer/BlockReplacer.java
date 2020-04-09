package com.example.examplemod.capability.randHouse.blockreplacer;

import com.example.examplemod.ExampleMod;
import net.minecraft.block.*;
import net.minecraft.state.properties.BambooLeaves;

import java.util.HashMap;

import static com.example.examplemod.Reference.randFromArray;
import static com.example.examplemod.Reference.random;

abstract public class BlockReplacer {
	
	/* static */
	
	private static HashMap<Block, BlockReplacer> map = new HashMap<>();
	
	protected static boolean half() {
		return Math.random() < 0.5;
	}
	
	interface Replace {
		BlockState replace(BlockState state);
	}
	
	public static BlockState replaceBlock(BlockState placed) {
		BlockReplacer replacer = map.get(placed.getBlock());
		
		if (replacer == null)
			return null;
		else
			return replacer.get(placed);
	}
	
	/* members */
	
	protected Block key;
	protected Replace replace;
	
	protected BlockReplacer(Block key, Replace replace) {
		this.key = key;
		this.replace = replace;
		
		map.put(key, this);
	}
	
	abstract protected BlockState get(BlockState state);
	
}
