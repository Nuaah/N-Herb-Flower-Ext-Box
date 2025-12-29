package com.Nuaah.NHerbFlowerExtBox.regi.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PotionCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    private final PotionData backend;
    private final ItemStack stack;
    private final LazyOptional<PotionData> optional;

    public PotionCapabilityProvider(ItemStack stack){
        this.backend = new PotionData();
        this.optional = LazyOptional.of(() -> backend);
        this.stack = stack;

        if (stack.hasTag() && stack.getTag().contains("PotionData", 10)) {
            backend.deserializeNBT(stack.getTag().getCompound("PotionData"));
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == NHerbFlowerExtBoxCapabilities.POTION_CAP) {
            if (stack.getOrCreateTag().getBoolean("HasPotionCap")) {
                return optional.cast();
            }
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return backend.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        backend.deserializeNBT(nbt);
    }
}
