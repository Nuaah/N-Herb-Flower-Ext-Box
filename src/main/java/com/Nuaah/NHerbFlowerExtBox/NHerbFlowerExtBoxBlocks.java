package com.Nuaah.NHerbFlowerExtBox;

import com.Nuaah.NHerbFlowerExtBox.block.BlockBellflower;
import com.Nuaah.NHerbFlowerExtBox.block.BlockClayCauldron;
import com.Nuaah.NHerbFlowerExtBox.block.BlockGardenMarigold;
import com.Nuaah.NHerbFlowerExtBox.block.BlockMillstone;
import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NHerbFlowerExtBoxBlocks {
    public static class Blocks{
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NHerbFlowerExtBox.MOD_ID);
        public static final RegistryObject<Block> MILLSTONE = BLOCKS.register("millstone", BlockMillstone::new);
        public static final RegistryObject<Block> MILLSTONE_TOP = BLOCKS.register("millstone_up", BlockMillstone::new);
        public static final RegistryObject<Block> CLAY_CAULDRON = BLOCKS.register("clay_cauldron", BlockClayCauldron::new);

        public static final RegistryObject<Block> BELLFLOWER = BLOCKS.register("bellflower", BlockBellflower::new);
        public static final RegistryObject<Block> GARDEN_MARIGOLD = BLOCKS.register("garden_marigold", BlockGardenMarigold::new);
        public static final RegistryObject<Block> POTTED_BELLFLOWER =
                BLOCKS.register("potted_bellflower",
                        () -> new FlowerPotBlock(
                                () -> (FlowerPotBlock) net.minecraft.world.level.block.Blocks.FLOWER_POT,
                                BELLFLOWER,
                                BlockBehaviour.Properties.of()
                                        .noOcclusion()
                                        .instabreak()
                                        .pushReaction(PushReaction.DESTROY)
                        ));
    }

    public static class BlockItems{
        public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NHerbFlowerExtBox.MOD_ID);
        public static final RegistryObject<Item> BELLFLOWER = BLOCK_ITEMS.register("bellflower",
                () -> new BlockItem(Blocks.BELLFLOWER.get(),new Item.Properties()));
        public static final RegistryObject<Item> GARDEN_MARIGOLD = BLOCK_ITEMS.register("garden_marigold",
                () -> new BlockItem(Blocks.GARDEN_MARIGOLD.get(),new Item.Properties()));

        public static final RegistryObject<Item> MILLSTONE = BLOCK_ITEMS.register("millstone",
                () -> new BlockItem(Blocks.MILLSTONE.get(),new Item.Properties()));

        public static final RegistryObject<Item> CLAY_CAULDRON = BLOCK_ITEMS.register("clay_cauldron",
                () -> new BlockItem(Blocks.CLAY_CAULDRON.get(),new Item.Properties()));
    }
}
