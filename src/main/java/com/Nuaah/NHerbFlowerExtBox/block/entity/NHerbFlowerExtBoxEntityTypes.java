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
    public static final RegistryObject<BlockEntityType<SapExtractorEntity>> SAP_EXTRACTOR = BLOCK_ENTITY_TYPES.register("sap_extractor",() -> set(SapExtractorEntity::new, NHerbFlowerExtBoxBlocks.Blocks.SAP_EXTRACTOR.get()));
    public static final RegistryObject<BlockEntityType<FirefliesMushroomEntity>> FIREFLIES_MUSHROOM = BLOCK_ENTITY_TYPES.register("fireflies_mushroom",() -> set(FirefliesMushroomEntity::new, NHerbFlowerExtBoxBlocks.Blocks.FIREFLIES_MUSHROOM.get()));
    public static final RegistryObject<BlockEntityType<AuroraMushroomEntity>> AURORA_MUSHROOM = BLOCK_ENTITY_TYPES.register("aurora_mushroom",() -> set(AuroraMushroomEntity::new, NHerbFlowerExtBoxBlocks.Blocks.AURORA_MUSHROOM.get()));

    private static <T extends BlockEntity> BlockEntityType<T> set (BlockEntityType.BlockEntitySupplier<T> entity, Block block){
        return BlockEntityType.Builder.of(entity,block).build(null);
    }
}
