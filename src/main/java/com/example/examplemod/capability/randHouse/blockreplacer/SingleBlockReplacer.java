package com.example.examplemod.capability.randHouse.blockreplacer;

import net.minecraft.block.*;
import net.minecraft.state.properties.BambooLeaves;

import static com.example.examplemod.Reference.randFromArray;
import static com.example.examplemod.Reference.random;

public class SingleBlockReplacer extends BlockReplacer {
	
	protected SingleBlockReplacer(Block key, Replace replace) {
		super(key, replace);
	}
	
	@Override
	protected BlockState get(BlockState state) {
		return replace.replace(state);
	}
	
	public static void init() {
		SingleBlockReplacer furnaceReplacer = new SingleBlockReplacer(Blocks.FURNACE,
			state -> state.with(FurnaceBlock.LIT, half())
		);
		
		SingleBlockReplacer blastFurnaceReplacer = new SingleBlockReplacer(Blocks.BLAST_FURNACE,
			state -> state.with(BlastFurnaceBlock.LIT, half())
		);
		
		SingleBlockReplacer smokerReplacer = new SingleBlockReplacer(Blocks.SMOKER,
			state -> state.with(SmokerBlock.LIT, half())
		);
		
		SingleBlockReplacer endPortalFrameReplacer = new SingleBlockReplacer(Blocks.END_PORTAL_FRAME,
			state -> state.with(EndPortalFrameBlock.EYE, half())
		);
		
		Replace commandBlockStateModify = state -> state.with(CommandBlockBlock.CONDITIONAL, half());
		
		BlockReplacer commandBlockReplacer = new SingleBlockReplacer(Blocks.COMMAND_BLOCK,
			commandBlockStateModify
		);
		
		BlockReplacer repeatingCommandBlockReplacer = new SingleBlockReplacer(Blocks.REPEATING_COMMAND_BLOCK,
			commandBlockStateModify
		);
		
		SingleBlockReplacer chainCommandBlockReplacer = new SingleBlockReplacer(Blocks.CHAIN_COMMAND_BLOCK,
			commandBlockStateModify
		);
		
		SingleBlockReplacer cauldronReplacer = new SingleBlockReplacer(Blocks.CAULDRON,
			state -> state.with(CauldronBlock.LEVEL, random(0, 3))
		);
		
		Replace mushroomStateModify = state -> state.with(HugeMushroomBlock.DOWN, half())
			                                                      .with(HugeMushroomBlock.EAST, half())
			                                                      .with(HugeMushroomBlock.WEST, half())
			                                                      .with(HugeMushroomBlock.NORTH, half())
			                                                      .with(HugeMushroomBlock.SOUTH, half())
			                                                      .with(HugeMushroomBlock.UP, half());
		
		SingleBlockReplacer redMushroomBlockReplacer = new SingleBlockReplacer(Blocks.RED_MUSHROOM_BLOCK,
			mushroomStateModify
		);
		
		SingleBlockReplacer brownMushroomBlockReplacer = new SingleBlockReplacer(Blocks.BROWN_MUSHROOM_BLOCK,
			mushroomStateModify
		);
		
		SingleBlockReplacer mushroomStemBlockReplacer = new SingleBlockReplacer(Blocks.MUSHROOM_STEM,
			mushroomStateModify
		);
	}
}
