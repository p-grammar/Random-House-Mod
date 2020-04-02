package com.example.examplemod.capability;

import com.example.examplemod.Reference;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Provider<T> implements ICapabilityProvider, INBTSerializable {
	Capability<T> capability;
	T instance;
	
	public Provider(Capability<T> capability, T instance) {
		this.capability = capability;
		this.instance = instance;
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == capability)
			return LazyOptional.of(() -> (T)instance);
		else
			return null;
	}
	
	@Override
	public INBT serializeNBT() {
		return capability.writeNBT(instance, null);
	}
	
	@Override
	public void deserializeNBT(INBT nbt) {
		capability.readNBT(instance, null, nbt);
	}
	
}