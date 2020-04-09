package com.example.examplemod.capability.randHouse.blockreplacer;

import com.example.examplemod.ExampleMod;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BambooLeaves;

import java.util.logging.Logger;

import static com.example.examplemod.Reference.randFromArray;

public class MultipleBlockReplacer extends BlockReplacer {
	
	private Block[] blockList;
	
	protected MultipleBlockReplacer(Block key, Replace replace, Block... blockList) {
		super(key, replace);
		
		this.blockList = blockList;
	}
	
	@Override
	protected BlockState get (BlockState state) {
		Block grabbed = randFromArray(blockList);
		
		if (grabbed == key) {
			return replace.replace(state);
			
		} else {
			return replace.replace(grabbed.getDefaultState());
			
		}
	}
	
	public static void init() {
		MultipleBlockReplacer potReplacer = new MultipleBlockReplacer(Blocks.FLOWER_POT,
			state -> state,
			Blocks.FLOWER_POT,
			Blocks.POTTED_OAK_SAPLING,
			Blocks.POTTED_SPRUCE_SAPLING,
			Blocks.POTTED_BIRCH_SAPLING,
			Blocks.POTTED_JUNGLE_SAPLING,
			Blocks.POTTED_ACACIA_SAPLING,
			Blocks.POTTED_DARK_OAK_SAPLING,
			Blocks.POTTED_FERN,
			Blocks.POTTED_DANDELION,
			Blocks.POTTED_POPPY,
			Blocks.POTTED_BLUE_ORCHID,
			Blocks.POTTED_ALLIUM,
			Blocks.POTTED_AZURE_BLUET,
			Blocks.POTTED_RED_TULIP,
			Blocks.POTTED_ORANGE_TULIP,
			Blocks.POTTED_WHITE_TULIP,
			Blocks.POTTED_PINK_TULIP,
			Blocks.POTTED_OXEYE_DAISY,
			Blocks.POTTED_CORNFLOWER,
			Blocks.POTTED_LILY_OF_THE_VALLEY,
			Blocks.POTTED_WITHER_ROSE,
			Blocks.POTTED_RED_MUSHROOM,
			Blocks.POTTED_BROWN_MUSHROOM,
			Blocks.POTTED_DEAD_BUSH,
			Blocks.POTTED_CACTUS,
			Blocks.POTTED_BAMBOO
		);
		
		BambooLeaves[] bambooLeavesStates = {
			BambooLeaves.SMALL, BambooLeaves.NONE, BambooLeaves.LARGE
		};
		
		MultipleBlockReplacer bambooReplacer = new MultipleBlockReplacer(Blocks.BAMBOO,
			state -> {
				if (state.getBlock() == Blocks.BAMBOO)
					return state.with(BambooBlock.PROPERTY_BAMBOO_LEAVES, randFromArray(bambooLeavesStates));
				else
					return state;
			},
			/* twice as likely to get full grown bamboo */
			Blocks.BAMBOO,
			Blocks.BAMBOO,
			Blocks.BAMBOO_SAPLING
		);
	}
	
}
