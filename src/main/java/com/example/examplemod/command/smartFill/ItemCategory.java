package com.example.examplemod.command.smartFill;

import com.example.examplemod.TextGenerator;
import com.mojang.datafixers.optics.Lens;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.util.List;

import static com.example.examplemod.Reference.randFromArray;
import static com.example.examplemod.Reference.random;
import static com.example.examplemod.command.smartFill.Util.addCustomName;
import static com.example.examplemod.command.smartFill.Util.getItemNbt;

abstract public class ItemCategory {
	
	public interface CustomNBT {
		CompoundNBT getCustomNBT(PlayerEntity player, ItemStack stack);
	}
	
	private CustomNBT customNBT;
	
	public ItemCategory() {
		this.customNBT = null;
	}
	
	public ItemCategory(CustomNBT customNBT) {
		this.customNBT = customNBT;
	}
	
	abstract Item get();
	
	public CompoundNBT getNBT(PlayerEntity player, ItemStack itemStack) {
		if (customNBT != null)
			return customNBT.getCustomNBT(player, itemStack);
		else
			return new CompoundNBT();
	}
	
	private static String[] entityNames = {
		EntityType.BAT.getRegistryName().getPath(),
		EntityType.field_226289_e_.getRegistryName().getPath(),
		EntityType.BLAZE.getRegistryName().getPath(),
		EntityType.CAVE_SPIDER.getRegistryName().getPath(),
		EntityType.CHICKEN.getRegistryName().getPath(),
		EntityType.COD.getRegistryName().getPath(),
		EntityType.COW.getRegistryName().getPath(),
		EntityType.CREEPER.getRegistryName().getPath(),
		EntityType.DONKEY.getRegistryName().getPath(),
		EntityType.DOLPHIN.getRegistryName().getPath(),
		EntityType.DROWNED.getRegistryName().getPath(),
		EntityType.ELDER_GUARDIAN.getRegistryName().getPath(),
		EntityType.ENDER_DRAGON.getRegistryName().getPath(),
		EntityType.ENDERMAN.getRegistryName().getPath(),
		EntityType.ENDERMITE.getRegistryName().getPath(),
		EntityType.EVOKER.getRegistryName().getPath(),
		EntityType.FOX.getRegistryName().getPath(),
		EntityType.GHAST.getRegistryName().getPath(),
		EntityType.GIANT.getRegistryName().getPath(),
		EntityType.GUARDIAN.getRegistryName().getPath(),
		EntityType.HORSE.getRegistryName().getPath(),
		EntityType.HUSK.getRegistryName().getPath(),
		EntityType.ILLUSIONER.getRegistryName().getPath(),
		EntityType.LLAMA.getRegistryName().getPath(),
		EntityType.MAGMA_CUBE.getRegistryName().getPath(),
		EntityType.MULE.getRegistryName().getPath(),
		EntityType.MOOSHROOM.getRegistryName().getPath(),
		EntityType.OCELOT.getRegistryName().getPath(),
		EntityType.PANDA.getRegistryName().getPath(),
		EntityType.PARROT.getRegistryName().getPath(),
		EntityType.PIG.getRegistryName().getPath(),
		EntityType.PUFFERFISH.getRegistryName().getPath(),
		EntityType.ZOMBIE_PIGMAN.getRegistryName().getPath(),
		EntityType.POLAR_BEAR.getRegistryName().getPath(),
		EntityType.RABBIT.getRegistryName().getPath(),
		EntityType.SALMON.getRegistryName().getPath(),
		EntityType.SHEEP.getRegistryName().getPath(),
		EntityType.SHULKER.getRegistryName().getPath(),
		EntityType.SILVERFISH.getRegistryName().getPath(),
		EntityType.SKELETON.getRegistryName().getPath(),
		EntityType.SKELETON_HORSE.getRegistryName().getPath(),
		EntityType.SLIME.getRegistryName().getPath(),
		EntityType.SNOW_GOLEM.getRegistryName().getPath(),
		EntityType.SPIDER.getRegistryName().getPath(),
		EntityType.SQUID.getRegistryName().getPath(),
		EntityType.STRAY.getRegistryName().getPath(),
		EntityType.TRADER_LLAMA.getRegistryName().getPath(),
		EntityType.TROPICAL_FISH.getRegistryName().getPath(),
		EntityType.TURTLE.getRegistryName().getPath(),
		EntityType.VEX.getRegistryName().getPath(),
		EntityType.VILLAGER.getRegistryName().getPath(),
		EntityType.IRON_GOLEM.getRegistryName().getPath(),
		EntityType.VINDICATOR.getRegistryName().getPath(),
		EntityType.PILLAGER.getRegistryName().getPath(),
		EntityType.WANDERING_TRADER.getRegistryName().getPath(),
		EntityType.WITCH.getRegistryName().getPath(),
		EntityType.WITHER.getRegistryName().getPath(),
		EntityType.WITHER_SKELETON.getRegistryName().getPath(),
		EntityType.WOLF.getRegistryName().getPath(),
		EntityType.ZOMBIE.getRegistryName().getPath(),
		EntityType.ZOMBIE_HORSE.getRegistryName().getPath(),
		EntityType.ZOMBIE_VILLAGER.getRegistryName().getPath(),
		EntityType.PHANTOM.getRegistryName().getPath(),
		EntityType.RAVAGER.getRegistryName().getPath(),
	};
	
	static ItemCategory[] itemCateogories;
	
	static ItemCategory signCategory;
	static SingleItem headCategory;
	static SingleItem spawnerCategory;
	static MultipleItem brewingCategory;
	static MultipleItem bannerCategory;
	static MultipleItem chestCategory;
	static MultipleItem enderTableCategory;
	static MultipleItem furnaceCategory;
	static MultipleItem musicBlockCategory;
	
	public static void init() {
		signCategory = new MultipleItem(
			(PlayerEntity player, ItemStack itemStack) -> {
				CompoundNBT nbt = new CompoundNBT();
				CompoundNBT blockNbt = new CompoundNBT();
				
				if (Math.random() < 0.5) {
					nbt.putString("Text1", "{\"text\":\"" + TextGenerator.getWords(1, 3, 90) + "\"}");
					nbt.putString("Text2", "{\"text\":\"" + TextGenerator.getWords(1, 3, 90) + "\"}");
					nbt.putString("Text3", "{\"text\":\"" + TextGenerator.getWords(1, 3, 90) + "\"}");
					nbt.putString("Text4", "{\"text\":\"" + TextGenerator.getWords(1, 3, 90) + "\"}");
					
					blockNbt.put("BlockEntityTag", nbt);
				}
				
				return blockNbt;
			},
			Items.OAK_SIGN, Items.BIRCH_SIGN, Items.SPRUCE_SIGN, Items.DARK_OAK_SIGN, Items.ACACIA_SIGN, Items.JUNGLE_SIGN
		);
		
		/* player head */
		headCategory = new SingleItem(
			(PlayerEntity player, ItemStack itemStack) -> {
				CompoundNBT nbt = new CompoundNBT();
				
				/* get a random player name from people on the server */
				List<ServerPlayerEntity> playerList = player.getServer().getPlayerList().getPlayers();
				String name = playerList.get((int) (Math.random() * playerList.size())).getName().getString();
				
				nbt.putString("SkullOwner", name);
				
				return nbt;
			}, Items.PLAYER_HEAD
		);
		
		spawnerCategory = new SingleItem(
			(PlayerEntity player, ItemStack itemStack) -> {
				CompoundNBT nbt = new CompoundNBT();
				
				nbt.putInt("SpawnCount", 0);
				
				CompoundNBT spawnData = new CompoundNBT();
				spawnData.putString("id", randFromArray(entityNames));
				
				nbt.put("SpawnData", spawnData);
				
				CompoundNBT blockTagNbt = new CompoundNBT();
				blockTagNbt.put("BlockEntityTag", nbt);
				
				return blockTagNbt;
			}, Items.SPAWNER
		);
		
		brewingCategory = new MultipleItem(
			(PlayerEntity player, ItemStack itemStack) -> {
				CompoundNBT nbt = new CompoundNBT();
				CompoundNBT blockTagNbt = new CompoundNBT();
				
				if (itemStack.getItem() == Items.BREWING_STAND) {
					if (Math.random() < 0.5)
						addCustomName(nbt);
					
					ListNBT items = new ListNBT();
					
					/* for each of three possible items */
					for (int i = 0; i < 5; ++i)
						if (Math.random() < 0.5)
							items.add(getItemNbt(i));
					
					nbt.put("Items", items);
					
					blockTagNbt.put("BlockEntityTag", nbt);
				}
				
				return blockTagNbt;
			}, Items.BREWING_STAND, Items.CAULDRON
		);
		
		String[] patternNames = new String[] {
			"bl", "bo", "br", "bri", "bs", "bt",
			"bts", "cbo", "cr", "cre", "cs", "dls",
			"drs", "flo", "glb", "gra", "gru", "hh",
			"hhb", "ld", "ls", "lud", "mc", "moj",
			"mr", "ms", "rd", "rs", "rud", "sc",
			"sku", "ss", "tl", "tr", "ts", "tt",
			"tts", "vh", "vhr"
		};
		
		bannerCategory = new MultipleItem(
			(PlayerEntity player, ItemStack itemStack) -> {
				CompoundNBT nbt = new CompoundNBT();
				CompoundNBT blockTagNbt = new CompoundNBT();
				
				ListNBT patternList = new ListNBT();
				
				int numPatterns = random(0, 20);
				for (int i = 0; i < numPatterns; ++i) {
					CompoundNBT pattern = new CompoundNBT();
					
					pattern.putInt("Color", random(0, 15));
					pattern.putString("Pattern", randFromArray(patternNames));
					
					patternList.add(pattern);
				}
				
				nbt.put("Patterns", patternList);
				blockTagNbt.put("BlockEntityTag", nbt);
				
				return blockTagNbt;
			},
			Items.WHITE_BANNER,
			Items.ORANGE_BANNER,
			Items.PINK_BANNER,
			Items.LIGHT_BLUE_BANNER,
			Items.YELLOW_BANNER,
			Items.LIME_BANNER,
			Items.MAGENTA_BANNER,
			Items.GRAY_BANNER,
			Items.LIGHT_GRAY_BANNER,
			Items.CYAN_BANNER,
			Items.PURPLE_BANNER,
			Items.BLUE_BANNER,
			Items.BROWN_BANNER,
			Items.GREEN_BANNER,
			Items.RED_BANNER,
			Items.BLACK_BANNER
		);
		
		chestCategory = new MultipleItem(
			(PlayerEntity player, ItemStack itemStack) -> {
				CompoundNBT stackNbt = new CompoundNBT();
				CompoundNBT blockTagNbt = new CompoundNBT();
				
				if (Math.random() < 0.5)
					addCustomName(blockTagNbt);
				
				ListNBT items = new ListNBT();
				
				for (int i = 0; i < 26; ++i)
					if (Math.random() < 0.15)
						items.add(getItemNbt(i));
				
				blockTagNbt.put("Items", items);
				
				stackNbt.put("BlockEntityTag", blockTagNbt);
				
				return stackNbt;
			},
			Items.CHEST, Items.TRAPPED_CHEST
		);
		
		enderTableCategory = new MultipleItem(
			(PlayerEntity player, ItemStack itemStack) -> {
				CompoundNBT stackNbt = new CompoundNBT();
				
				if (itemStack.getItem() == Items.ENCHANTING_TABLE) {
					CompoundNBT blockTagNbt = new CompoundNBT();
					
					if (Math.random() < 0.5)
						addCustomName(blockTagNbt);
					
					stackNbt.put("BlockEntityTag", blockTagNbt);
				}
				
				return stackNbt;
			},
			Items.ENDER_CHEST, Items.ENCHANTING_TABLE
		);
		
		furnaceCategory = new MultipleItem(
		(PlayerEntity player, ItemStack itemStack) -> {
			CompoundNBT stackNbt = new CompoundNBT();
			CompoundNBT blockTagNbt = new CompoundNBT();
			
			if (Math.random() < 0.5)
				addCustomName(blockTagNbt);
			
			ListNBT items = new ListNBT();
			
			for (int i = 0; i < 3; ++i)
				if (Math.random() < 0.5)
					items.add(getItemNbt(i));
			
			blockTagNbt.put("Items", items);
			
			stackNbt.put("BlockEntityTag", blockTagNbt);
			return stackNbt;
		}, Items.FURNACE, Items.SMOKER, Items.BLAST_FURNACE);
		
		musicBlockCategory = new MultipleItem(
			(PlayerEntity player, ItemStack itemStack) -> {
				CompoundNBT stackNbt = new CompoundNBT();
				
				if (itemStack.getItem() == Items.JUKEBOX) {
					CompoundNBT blockTagNbt = new CompoundNBT();
					
					CompoundNBT recordItem = getItemNbt(0);
					blockTagNbt.put("RecordItem", recordItem);
					
					stackNbt.put("BlockEntityTag", blockTagNbt);
				}
				
				return stackNbt;
			},
			Items.NOTE_BLOCK, Items.JUKEBOX
		);
		
		itemCateogories = new ItemCategory[] {
			signCategory,
			headCategory,
			spawnerCategory,
			brewingCategory,
			bannerCategory,
			chestCategory,
			enderTableCategory,
			furnaceCategory,
			musicBlockCategory,
			
			new MultipleItem(Items.DIRT, Items.COARSE_DIRT),
			new MultipleItem(Items.GRASS_BLOCK, Items.PODZOL, Items.MYCELIUM, Items.FARMLAND, Items.GRASS_PATH),
			new SingleItem(Items.GRAVEL),
			new MultipleItem(Items.SAND, Items.RED_SAND),
			new SingleItem(Items.CLAY),
			new MultipleItem(Items.STONE, Items.STONE_STAIRS, Items.STONE_SLAB, Items.STONE_BUTTON),
			new MultipleItem(Items.COBBLESTONE, Items.COBBLESTONE_WALL, Items.COBBLESTONE_STAIRS, Items.COBBLESTONE_SLAB),
			new MultipleItem(Items.SMOOTH_STONE, Items.SMOOTH_STONE_SLAB),
			
			new MultipleItem(Items.GRANITE, Items.GRANITE_SLAB, Items.GRANITE_STAIRS, Items.GRANITE_WALL),
			new MultipleItem(Items.POLISHED_GRANITE, Items.POLISHED_GRANITE_SLAB, Items.POLISHED_GRANITE_STAIRS),
			
			new MultipleItem(Items.ANDESITE, Items.ANDESITE_SLAB, Items.ANDESITE_STAIRS, Items.ANDESITE_WALL),
			new MultipleItem(Items.POLISHED_ANDESITE, Items.POLISHED_ANDESITE_SLAB, Items.POLISHED_ANDESITE_STAIRS),
			
			new MultipleItem(Items.DIORITE, Items.DIORITE_SLAB, Items.DIORITE_STAIRS, Items.DIORITE_WALL),
			new MultipleItem(Items.POLISHED_DIORITE, Items.POLISHED_DIORITE_SLAB, Items.POLISHED_DIORITE_STAIRS),
			
			new SingleItem(Items.BEDROCK),
			new SingleItem(Items.OBSIDIAN),
			new MultipleItem(Items.MOSSY_COBBLESTONE, Items.MOSSY_COBBLESTONE_SLAB, Items.MOSSY_COBBLESTONE_STAIRS, Items.MOSSY_COBBLESTONE_WALL),
			new MultipleItem(Items.BRICKS, Items.BRICK_SLAB, Items.BRICK_STAIRS, Items.BRICK_WALL),
			new SingleItem(Items.BONE_BLOCK),
			
			/* sandstone */
			new MultipleItem(Items.SANDSTONE, Items.SANDSTONE_SLAB, Items.SANDSTONE_STAIRS, Items.SANDSTONE_WALL),
			new MultipleItem(Items.CUT_SANDSTONE, Items.SMOOTH_SANDSTONE, Items.CHISELED_SANDSTONE, Items.SMOOTH_SANDSTONE_STAIRS, Items.SMOOTH_SANDSTONE_SLAB, Items.CUT_SANDSTONE_SLAB),
			
			/* red sandstone */
			new MultipleItem(Items.RED_SANDSTONE, Items.RED_SANDSTONE_SLAB, Items.RED_SANDSTONE_STAIRS, Items.RED_SANDSTONE_WALL),
			new MultipleItem(Items.CUT_RED_SANDSTONE, Items.SMOOTH_RED_SANDSTONE, Items.CHISELED_RED_SANDSTONE, Items.SMOOTH_RED_SANDSTONE_STAIRS, Items.SMOOTH_RED_SANDSTONE_SLAB, Items.CUT_RED_SANDSTONE_SLAB),
			
			new SingleItem(Items.SNOW_BLOCK),
			new MultipleItem(Items.PACKED_ICE, Items.BLUE_ICE),
			new MultipleItem(Items.SEA_LANTERN, Items.GLOWSTONE),
			new MultipleItem(Items.PRISMARINE, Items.PRISMARINE_WALL, Items.PRISMARINE_SLAB, Items.PRISMARINE_STAIRS),
			new MultipleItem(Items.PRISMARINE_BRICKS, Items.PRISMARINE_BRICK_SLAB, Items.PRISMARINE_BRICK_SLAB),
			new MultipleItem(Items.DARK_PRISMARINE, Items.DARK_PRISMARINE_SLAB, Items.DARK_PRISMARINE_STAIRS),
			
			/* ore materials */
			new MultipleItem(Items.EMERALD_ORE, Items.EMERALD_BLOCK),
			new MultipleItem(Items.DIAMOND_ORE, Items.DIAMOND_BLOCK),
			new MultipleItem(Items.COAL_ORE, Items.COAL_BLOCK),
			new MultipleItem(Items.REDSTONE_ORE, Items.REDSTONE_BLOCK),
			new MultipleItem(Items.GOLD_BLOCK, Items.GOLD_ORE),
			new MultipleItem(Items.LAPIS_ORE, Items.LAPIS_BLOCK),
			new MultipleItem(Items.IRON_BLOCK, Items.IRON_ORE),
			
			new SingleItem(Items.GLASS),
			new SingleItem(Items.GLASS_PANE),
			new SingleItem(Items.IRON_BARS),
			new SingleItem(Items.BOOKSHELF),
			
			new MultipleItem(Items.STONE_BRICKS, Items.STONE_BRICK_SLAB, Items.STONE_BRICK_WALL, Items.STONE_BRICK_STAIRS),
			new MultipleItem(Items.MOSSY_STONE_BRICKS, Items.CRACKED_STONE_BRICKS, Items.CHISELED_STONE_BRICKS),
			
			new MultipleItem(Items.SPONGE, Items.WET_SPONGE),
			new SingleItem(Items.SLIME_BLOCK),
			new MultipleItem(Items.PUMPKIN, Items.CARVED_PUMPKIN, Items.JACK_O_LANTERN),
			new SingleItem(Items.MELON),
			new SingleItem(Items.HAY_BLOCK),
			new SingleItem(Items.DRIED_KELP_BLOCK),
			
			/* mushroom */
			new MultipleItem(Items.RED_MUSHROOM_BLOCK, Items.BROWN_MUSHROOM_BLOCK, Items.MUSHROOM_STEM),
			
			new MultipleItem(Items.OAK_LEAVES, Items.SPRUCE_LEAVES, Items.BIRCH_LEAVES, Items.JUNGLE_LEAVES, Items.DARK_OAK_LEAVES, Items.ACACIA_LEAVES),
			
			new MultipleItem(Items.OAK_LOG, Items.SPRUCE_LOG, Items.JUNGLE_LOG, Items.BIRCH_LOG, Items.DARK_OAK_LOG, Items.ACACIA_LOG, Items.OAK_WOOD, Items.SPRUCE_WOOD, Items.JUNGLE_WOOD, Items.BIRCH_WOOD, Items.DARK_OAK_WOOD, Items.ACACIA_WOOD),
			new MultipleItem(Items.STRIPPED_OAK_LOG, Items.STRIPPED_SPRUCE_LOG, Items.STRIPPED_JUNGLE_LOG, Items.STRIPPED_BIRCH_LOG, Items.STRIPPED_DARK_OAK_LOG, Items.STRIPPED_ACACIA_LOG, Items.STRIPPED_OAK_WOOD, Items.STRIPPED_SPRUCE_WOOD, Items.STRIPPED_JUNGLE_WOOD, Items.STRIPPED_BIRCH_WOOD, Items.STRIPPED_DARK_OAK_WOOD, Items.STRIPPED_ACACIA_WOOD),
			
			new MultipleItem(Items.OAK_PLANKS, Items.OAK_STAIRS, Items.OAK_SLAB, Items.OAK_FENCE, Items.OAK_TRAPDOOR, Items.OAK_FENCE_GATE, Items.OAK_PRESSURE_PLATE, Items.OAK_BUTTON),
			new MultipleItem(Items.BIRCH_PLANKS, Items.BIRCH_STAIRS, Items.BIRCH_SLAB, Items.BIRCH_FENCE, Items.BIRCH_TRAPDOOR, Items.BIRCH_FENCE_GATE, Items.BIRCH_PRESSURE_PLATE, Items.BIRCH_BUTTON),
			new MultipleItem(Items.SPRUCE_PLANKS, Items.SPRUCE_STAIRS, Items.SPRUCE_SLAB, Items.SPRUCE_FENCE, Items.SPRUCE_TRAPDOOR, Items.SPRUCE_FENCE_GATE, Items.SPRUCE_PRESSURE_PLATE, Items.SPRUCE_BUTTON),
			new MultipleItem(Items.DARK_OAK_PLANKS, Items.DARK_OAK_STAIRS, Items.DARK_OAK_SLAB, Items.DARK_OAK_FENCE, Items.DARK_OAK_TRAPDOOR, Items.DARK_OAK_FENCE_GATE, Items.DARK_OAK_PRESSURE_PLATE, Items.DARK_OAK_BUTTON),
			new MultipleItem(Items.ACACIA_PLANKS, Items.ACACIA_STAIRS, Items.ACACIA_SLAB, Items.ACACIA_FENCE, Items.ACACIA_TRAPDOOR, Items.ACACIA_FENCE_GATE, Items.ACACIA_PRESSURE_PLATE, Items.ACACIA_BUTTON),
			new MultipleItem(Items.JUNGLE_PLANKS, Items.JUNGLE_STAIRS, Items.JUNGLE_SLAB, Items.JUNGLE_FENCE, Items.JUNGLE_TRAPDOOR, Items.JUNGLE_FENCE_GATE, Items.JUNGLE_PRESSURE_PLATE, Items.JUNGLE_BUTTON),
			
			new MultipleItem(Items.OAK_DOOR, Items.ACACIA_DOOR, Items.DARK_OAK_DOOR, Items.JUNGLE_DOOR, Items.SPRUCE_DOOR, Items.BIRCH_DOOR, Items.IRON_DOOR),
			
			new MultipleItem(Items.IRON_TRAPDOOR, Items.HEAVY_WEIGHTED_PRESSURE_PLATE, Items.LIGHT_WEIGHTED_PRESSURE_PLATE),
			new MultipleItem(Items.RAIL, Items.POWERED_RAIL, Items.DETECTOR_RAIL, Items.ACTIVATOR_RAIL),
			new MultipleItem(Items.END_STONE, Items.END_PORTAL_FRAME),
			new MultipleItem(Items.END_STONE_BRICK_SLAB, Items.END_STONE_BRICKS, Items.END_STONE_BRICK_STAIRS, Items.END_STONE_BRICK_WALL),
			
			new MultipleItem(Items.PURPUR_BLOCK, Items.PURPUR_PILLAR, Items.PURPUR_SLAB, Items.PURPUR_STAIRS),
			
			new MultipleItem(Items.QUARTZ_BLOCK, Items.SMOOTH_QUARTZ, Items.QUARTZ_STAIRS, Items.SMOOTH_QUARTZ_STAIRS, Items.QUARTZ_SLAB, Items.SMOOTH_QUARTZ_SLAB),
			new MultipleItem(Items.CHISELED_QUARTZ_BLOCK, Items.QUARTZ_PILLAR),
			
			new MultipleItem(Items.NETHER_QUARTZ_ORE, Items.NETHERRACK),
			
			new SingleItem(Items.SOUL_SAND),
			new SingleItem(Items.NETHER_WART_BLOCK),
			new SingleItem(Items.MAGMA_BLOCK),
			
			new MultipleItem(Items.NETHER_BRICKS, Items.NETHER_BRICK_WALL, Items.NETHER_BRICK_SLAB, Items.NETHER_BRICK_STAIRS, Items.NETHER_BRICK_FENCE),
			new MultipleItem(Items.RED_NETHER_BRICKS, Items.RED_NETHER_BRICK_WALL, Items.RED_NETHER_BRICK_SLAB, Items.RED_NETHER_BRICK_STAIRS),
			
			new MultipleItem(Items.ANVIL, Items.CHIPPED_ANVIL, Items.DAMAGED_ANVIL, Items.GRINDSTONE, Items.GRINDSTONE, Items.GRINDSTONE),
			new MultipleItem(Items.BEACON, Items.CONDUIT),
			
			/* cimbing materials */
			new SingleItem(Items.SCAFFOLDING),
			new SingleItem(Items.LADDER),
			
			new MultipleItem(Items.PISTON, Items.STICKY_PISTON),
			new MultipleItem(Items.DISPENSER, Items.DROPPER),
			
			/* light sources */
			new SingleItem(Items.REDSTONE_LAMP),
			new MultipleItem(Items.TORCH, Items.REDSTONE_TORCH),
			new MultipleItem(Items.END_ROD, Items.LANTERN),
			
			/* redstone parts */
			new MultipleItem(Items.REPEATER, Items.COMPARATOR, Items.TRIPWIRE_HOOK, Items.LEVER, Items.REDSTONE),
			
			new MultipleItem(Items.DAYLIGHT_DETECTOR, Items.HOPPER),
			new MultipleItem(Items.BELL, Items.LECTERN),
			new MultipleItem(Items.CAKE, Items.COBWEB),
			new MultipleItem(Items.SEA_PICKLE, Items.TURTLE_EGG),
			
			/* skulls */
			new MultipleItem(Items.SKELETON_SKULL, Items.WITHER_SKELETON_SKULL, Items.ZOMBIE_HEAD, Items.CREEPER_HEAD, Items.DRAGON_HEAD),
			
			/* command blocks */
			new MultipleItem(Items.COMMAND_BLOCK, Items.CHAIN_COMMAND_BLOCK, Items.REPEATING_COMMAND_BLOCK),
			
			/* corals */
			new MultipleItem(Items.TUBE_CORAL_BLOCK, Items.BRAIN_CORAL_BLOCK, Items.FIRE_CORAL_BLOCK, Items.BUBBLE_CORAL_BLOCK, Items.HORN_CORAL_BLOCK),
			new MultipleItem(Items.TUBE_CORAL, Items.BRAIN_CORAL, Items.FIRE_CORAL, Items.BUBBLE_CORAL, Items.HORN_CORAL, Items.TUBE_CORAL_FAN, Items.BRAIN_CORAL_FAN, Items.FIRE_CORAL_FAN, Items.BUBBLE_CORAL_FAN, Items.HORN_CORAL_FAN),
			
			/* various benches */
			new MultipleItem(Items.CRAFTING_TABLE, Items.LOOM),
			new MultipleItem(Items.FLETCHING_TABLE, Items.CARTOGRAPHY_TABLE),
			new MultipleItem(Items.STONECUTTER, Items.CAMPFIRE),
			new MultipleItem(Items.BARREL, Items.COMPOSTER),
			new MultipleItem(Items.SMITHING_TABLE, Items.OBSERVER),
			
			/* misc plants */
			new MultipleItem(Items.GRASS, Items.TALL_GRASS, Items.FERN, Items.LARGE_FERN, Items.CACTUS),
			new MultipleItem(Items.VINE, Items.BAMBOO, Items.DEAD_BUSH, Items.BROWN_MUSHROOM, Items.RED_MUSHROOM),
			new MultipleItem(Items.OAK_SAPLING, Items.BIRCH_SAPLING, Items.SPRUCE_SAPLING, Items.JUNGLE_SAPLING, Items.ACACIA_SAPLING, Items.DARK_OAK_SAPLING),
			
			new MultipleItem(Items.PAINTING, Items.ITEM_FRAME),
			
			/* bee blocks */
			new MultipleItem(Items.field_226636_pV_, Items.field_226637_pW_, Items.field_226639_pY_, Items.field_226640_pZ_),
			
			/* short and tall flowers */
			new MultipleItem(Items.DANDELION, Items.POPPY, Items.BLUE_ORCHID, Items.ALLIUM, Items.AZURE_BLUET, Items.RED_TULIP, Items.ORANGE_TULIP, Items.WHITE_TULIP, Items.PINK_TULIP, Items.OXEYE_DAISY, Items.CORNFLOWER, Items.LILY_OF_THE_VALLEY, Items.WITHER_ROSE),
			new MultipleItem(Items.SUNFLOWER, Items.LILAC, Items.ROSE_BUSH, Items.PEONY),
			
			new MultipleItem(
				Items.SHULKER_BOX,
				Items.WHITE_SHULKER_BOX,
				Items.ORANGE_SHULKER_BOX,
				Items.PINK_SHULKER_BOX,
				Items.LIGHT_BLUE_SHULKER_BOX,
				Items.YELLOW_SHULKER_BOX,
				Items.LIME_SHULKER_BOX,
				Items.MAGENTA_SHULKER_BOX,
				Items.GRAY_SHULKER_BOX,
				Items.LIGHT_GRAY_SHULKER_BOX,
				Items.CYAN_SHULKER_BOX,
				Items.PURPLE_SHULKER_BOX,
				Items.BLUE_SHULKER_BOX,
				Items.BROWN_SHULKER_BOX,
				Items.GREEN_SHULKER_BOX,
				Items.RED_SHULKER_BOX,
				Items.BLACK_SHULKER_BOX
			),
			new MultipleItem(
				Items.WHITE_CONCRETE,
				Items.ORANGE_CONCRETE,
				Items.PINK_CONCRETE,
				Items.LIGHT_BLUE_CONCRETE,
				Items.YELLOW_CONCRETE,
				Items.LIME_CONCRETE,
				Items.MAGENTA_CONCRETE,
				Items.GRAY_CONCRETE,
				Items.LIGHT_GRAY_CONCRETE,
				Items.CYAN_CONCRETE,
				Items.PURPLE_CONCRETE,
				Items.BLUE_CONCRETE,
				Items.BROWN_CONCRETE,
				Items.GREEN_CONCRETE,
				Items.RED_CONCRETE,
				Items.BLACK_CONCRETE
			),
			new MultipleItem(
				Items.WHITE_CONCRETE_POWDER,
				Items.ORANGE_CONCRETE_POWDER,
				Items.PINK_CONCRETE_POWDER,
				Items.LIGHT_BLUE_CONCRETE_POWDER,
				Items.YELLOW_CONCRETE_POWDER,
				Items.LIME_CONCRETE_POWDER,
				Items.MAGENTA_CONCRETE_POWDER,
				Items.GRAY_CONCRETE_POWDER,
				Items.LIGHT_GRAY_CONCRETE_POWDER,
				Items.CYAN_CONCRETE_POWDER,
				Items.PURPLE_CONCRETE_POWDER,
				Items.BLUE_CONCRETE_POWDER,
				Items.BROWN_CONCRETE_POWDER,
				Items.GREEN_CONCRETE_POWDER,
				Items.RED_CONCRETE_POWDER,
				Items.BLACK_CONCRETE_POWDER
			),
			new MultipleItem(
				Items.WHITE_GLAZED_TERRACOTTA,
				Items.ORANGE_GLAZED_TERRACOTTA,
				Items.PINK_GLAZED_TERRACOTTA,
				Items.LIGHT_BLUE_GLAZED_TERRACOTTA,
				Items.YELLOW_GLAZED_TERRACOTTA,
				Items.LIME_GLAZED_TERRACOTTA,
				Items.MAGENTA_GLAZED_TERRACOTTA,
				Items.GRAY_GLAZED_TERRACOTTA,
				Items.LIGHT_GRAY_GLAZED_TERRACOTTA,
				Items.CYAN_GLAZED_TERRACOTTA,
				Items.PURPLE_GLAZED_TERRACOTTA,
				Items.BLUE_GLAZED_TERRACOTTA,
				Items.BROWN_GLAZED_TERRACOTTA,
				Items.GREEN_GLAZED_TERRACOTTA,
				Items.RED_GLAZED_TERRACOTTA,
				Items.BLACK_GLAZED_TERRACOTTA
			),
			new MultipleItem(
				Items.TERRACOTTA,
				Items.WHITE_TERRACOTTA,
				Items.ORANGE_TERRACOTTA,
				Items.PINK_TERRACOTTA,
				Items.LIGHT_BLUE_TERRACOTTA,
				Items.YELLOW_TERRACOTTA,
				Items.LIME_TERRACOTTA,
				Items.MAGENTA_TERRACOTTA,
				Items.GRAY_TERRACOTTA,
				Items.LIGHT_GRAY_TERRACOTTA,
				Items.CYAN_TERRACOTTA,
				Items.PURPLE_TERRACOTTA,
				Items.BLUE_TERRACOTTA,
				Items.BROWN_TERRACOTTA,
				Items.GREEN_TERRACOTTA,
				Items.RED_TERRACOTTA,
				Items.BLACK_TERRACOTTA
			),
			new MultipleItem(
				Items.WHITE_WOOL,
				Items.ORANGE_WOOL,
				Items.PINK_WOOL,
				Items.LIGHT_BLUE_WOOL,
				Items.YELLOW_WOOL,
				Items.LIME_WOOL,
				Items.MAGENTA_WOOL,
				Items.GRAY_WOOL,
				Items.LIGHT_GRAY_WOOL,
				Items.CYAN_WOOL,
				Items.PURPLE_WOOL,
				Items.BLUE_WOOL,
				Items.BROWN_WOOL,
				Items.GREEN_WOOL,
				Items.RED_WOOL,
				Items.BLACK_WOOL
			),
			new MultipleItem(
				Items.WHITE_STAINED_GLASS,
				Items.ORANGE_STAINED_GLASS,
				Items.PINK_STAINED_GLASS,
				Items.LIGHT_BLUE_STAINED_GLASS,
				Items.YELLOW_STAINED_GLASS,
				Items.LIME_STAINED_GLASS,
				Items.MAGENTA_STAINED_GLASS,
				Items.GRAY_STAINED_GLASS,
				Items.LIGHT_GRAY_STAINED_GLASS,
				Items.CYAN_STAINED_GLASS,
				Items.PURPLE_STAINED_GLASS,
				Items.BLUE_STAINED_GLASS,
				Items.BROWN_STAINED_GLASS,
				Items.GREEN_STAINED_GLASS,
				Items.RED_STAINED_GLASS,
				Items.BLACK_STAINED_GLASS
			),
			new MultipleItem(
				Items.WHITE_STAINED_GLASS_PANE,
				Items.ORANGE_STAINED_GLASS_PANE,
				Items.PINK_STAINED_GLASS_PANE,
				Items.LIGHT_BLUE_STAINED_GLASS_PANE,
				Items.YELLOW_STAINED_GLASS_PANE,
				Items.LIME_STAINED_GLASS_PANE,
				Items.MAGENTA_STAINED_GLASS_PANE,
				Items.GRAY_STAINED_GLASS_PANE,
				Items.LIGHT_GRAY_STAINED_GLASS_PANE,
				Items.CYAN_STAINED_GLASS_PANE,
				Items.PURPLE_STAINED_GLASS_PANE,
				Items.BLUE_STAINED_GLASS_PANE,
				Items.BROWN_STAINED_GLASS_PANE,
				Items.GREEN_STAINED_GLASS_PANE,
				Items.RED_STAINED_GLASS_PANE,
				Items.BLACK_STAINED_GLASS_PANE
			),
			new MultipleItem(
				Items.WHITE_CARPET,
				Items.ORANGE_CARPET,
				Items.PINK_CARPET,
				Items.LIGHT_BLUE_CARPET,
				Items.YELLOW_CARPET,
				Items.LIME_CARPET,
				Items.MAGENTA_CARPET,
				Items.GRAY_CARPET,
				Items.LIGHT_GRAY_CARPET,
				Items.CYAN_CARPET,
				Items.PURPLE_CARPET,
				Items.BLUE_CARPET,
				Items.BROWN_CARPET,
				Items.GREEN_CARPET,
				Items.RED_CARPET,
				Items.BLACK_CARPET
			),
			new MultipleItem(
				Items.WHITE_BED,
				Items.ORANGE_BED,
				Items.PINK_BED,
				Items.LIGHT_BLUE_BED,
				Items.YELLOW_BED,
				Items.LIME_BED,
				Items.MAGENTA_BED,
				Items.GRAY_BED,
				Items.LIGHT_GRAY_BED,
				Items.CYAN_BED,
				Items.PURPLE_BED,
				Items.BLUE_BED,
				Items.BROWN_BED,
				Items.GREEN_BED,
				Items.RED_BED,
				Items.BLACK_BED
			)
		};
	}
}
