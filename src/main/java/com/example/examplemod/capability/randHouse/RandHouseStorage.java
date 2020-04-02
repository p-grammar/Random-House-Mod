package com.example.examplemod.capability.randHouse;

import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.NBTTypes;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class RandHouseStorage implements Capability.IStorage<RandHouseI> {
	
	public static final String PARTICIPATING_TAG = "p";
	public static final String MAXTIME_TAG = "m";
	public static final String TIMELEFT_TAG = "t";
	public static final String POINTS_TAG = "s";
	
	@Nullable
	@Override
	public INBT writeNBT(Capability<RandHouseI> capability, RandHouseI instance, Direction side) {
		CompoundNBT nbt = new CompoundNBT();
		
		nbt.putBoolean(PARTICIPATING_TAG, instance.getParticipating());
		nbt.putInt(MAXTIME_TAG, instance.getMaxTime());
		nbt.putInt(TIMELEFT_TAG, instance.getTimeLeft());
		nbt.putInt(POINTS_TAG, instance.getPoints());
		
		return nbt;
	}
	
	@Override
	public void readNBT(Capability<RandHouseI> capability, RandHouseI instance, Direction side, INBT nbt) {
		CompoundNBT comp = (CompoundNBT)nbt;
		
		instance.setParticipating(comp.getBoolean(PARTICIPATING_TAG));
		instance.setMaxTime(comp.getInt(MAXTIME_TAG));
		instance.setTimeLeft(comp.getInt(TIMELEFT_TAG));
		instance.setPoints(comp.getInt(POINTS_TAG));
	}
}
