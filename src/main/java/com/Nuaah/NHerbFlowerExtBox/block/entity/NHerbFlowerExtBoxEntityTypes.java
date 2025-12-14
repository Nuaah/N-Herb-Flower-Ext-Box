package com.Nuaah.NHerbFlowerExtBox.block.entity;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NHerbFlowerExtBoxEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, NHerbFlowerExtBox.MOD_ID);

    public static final RegistryObject<BlockEntityType<MillstoneEntity>> MILLSTONE = BLOCK_ENTITY_TYPES.register("millstone",() -> set(MillstoneEntity::new, NHerbFlowerExtBoxBlocks.Blocks.MILLSTONE.get()));
    public static final RegistryObject<BlockEntityType<ClayCauldronEntity>> CLAY_CAULDRON = BLOCK_ENTITY_TYPES.register("clay_cauldron",() -> set(ClayCauldronEntity::new, NHerbFlowerExtBoxBlocks.Blocks.CLAY_CAULDRON.get()));
    public static final RegistryObject<BlockEntityType<MoonflowerEntity>> MOONFLOWER = BLOCK_ENTITY_TYPES.register("moonflower",() -> set(MoonflowerEntity::new, NHerbFlowerExtBoxBlocks.Blocks.MOONFLOWER.get()));

    private static <T extends BlockEntity> BlockEntityType<T> set (BlockEntityType.BlockEntitySupplier<T> entity, Block block){
        return BlockEntityType.Builder.of(entity,block).build(null);
    }
}
