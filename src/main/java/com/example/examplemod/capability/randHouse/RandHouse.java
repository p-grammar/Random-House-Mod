package com.example.examplemod.capability.randHouse;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Reference;
import com.example.examplemod.capability.Provider;
import jdk.nashorn.internal.ir.BlockStatement;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.TextComponentMessageFormatHandler;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.InfoCmp;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RandHouse {
	
	/* storage */
	
	public static final String RESOURCE_STRING = "randhouse";
	
	@CapabilityInject(RandHouseI.class)
	public static Capability<RandHouseI> RAND_HOUSE_CAPABILITY = null;
	
	public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, RESOURCE_STRING);
	
	/* funcitonality */
	
	/**
	 * registers the rand house capability
	 */
	public static void register() {
		/* register capability */
		CapabilityManager.INSTANCE.register(RandHouseI.class, new RandHouseStorage(), RandHouseImpl::new);
		
		/* register events */
		MinecraftForge.EVENT_BUS.register(RandHouse.class);
	}
	
	@Nullable
	public static RandHouseI getRandHouse(LivingEntity entity){
		return entity.getCapability(RAND_HOUSE_CAPABILITY, null).orElse(null);
	}
	
	/* events */
	
	@SubscribeEvent
	public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof PlayerEntity)
			event.addCapability(ID, new Provider<>(RAND_HOUSE_CAPABILITY, new RandHouseImpl()));
	}
	
	@SubscribeEvent
	public static void playerClone(final PlayerEvent.Clone event) {
		final RandHouseI oldRandHouse = getRandHouse(event.getOriginal());
		final RandHouseI newRandHouse = getRandHouse(event.getPlayer());
		
		if (oldRandHouse != null && newRandHouse != null) {
			newRandHouse.setPoints(oldRandHouse.getPoints());
			newRandHouse.setTimeLeft(oldRandHouse.getTimeLeft());
			newRandHouse.setParticipating(oldRandHouse.getParticipating());
		}
	}
	
	@SubscribeEvent
	public static void onPlayerPlacesBlock(BlockEvent.EntityPlaceEvent event) {
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntity();
			
			RandHouseI randHouse = getRandHouse(player);
			
			//BlockState block = event.getPlacedBlock();
			
			randHouse.changePoints(1);
		}
	}
	
	@SubscribeEvent
	public static void onPlayerDestroyBlock(BlockEvent.BreakEvent event) {
		PlayerEntity player = event.getPlayer();
		
		RandHouseI randHouse = getRandHouse(player);
		
		randHouse.changePoints(-1);
		
		/* points can't go less than 0 */
		if (randHouse.getPoints() < 0)
			randHouse.setPoints(0);
	}
	
	@SubscribeEvent
	public static void onTick(TickEvent.ServerTickEvent event) {
		if(event.phase == TickEvent.Phase.END) {
			if (Minecraft.getInstance().world != null && Minecraft.getInstance().player != null && Minecraft.getInstance().player.getServer() != null) {
				List<ServerPlayerEntity> playerList = Minecraft.getInstance().player.getServer().getPlayerList().getPlayers();
				
				for (ServerPlayerEntity player : playerList) {
					RandHouseI randHouse = player.getCapability(RAND_HOUSE_CAPABILITY).orElse(null);
					
					if (randHouse != null) {
						randHouse.changeTimeLeft(-1);
						
						if (randHouse.getTimeLeft() <= 0) {
							randHouse.setTimeLeft(randHouse.getMaxTime());
						}
					}
				}
			}
		}
	}
	
}
