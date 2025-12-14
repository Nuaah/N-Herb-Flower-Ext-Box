package com.Nuaah.NHerbFlowerExtBox;

import com.Nuaah.NHerbFlowerExtBox.block.*;
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
        public static final RegistryObject<Block> SHEPHERDS_PURSE = BLOCKS.register("shepherds_purse", BlockShepherdsPurse::new);
        public static final RegistryObject<Block> LAVENDER = BLOCKS.register("lavender", BlockLavender::new);
        public static final RegistryObject<Block> GINSENG = BLOCKS.register("ginseng", BlockGinseng::new);
        public static final RegistryObject<Block> PEPPERMINT = BLOCKS.register("peppermint", BlockPeppermint::new);
        public static final RegistryObject<Block> MOONFLOWER = BLOCKS.register("moonflower", BlockMoonflower::new);
        public static final RegistryObject<Block> PRICKLY_PEAR = BLOCKS.register("prickly_pear", BlockPricklyPear::new);
        public static final RegistryObject<Block> PEYOTE = BLOCKS.register("peyote", BlockPeyote::new);
        
        public static final RegistryObject<Block> POTTED_BELLFLOWER =
            BLOCKS.register("potted_bellflower",
                () -> new FlowerPotBlock(() -> POT, BELLFLOWER, POT_PROPERTIES));
        public static final RegistryObject<Block> POTTED_GARDEN_MARIGOLD =
                BLOCKS.register("potted_garden_marigold",
                        () -> new FlowerPotBlock(() -> POT, GARDEN_MARIGOLD, POT_PROPERTIES));
        public static final RegistryObject<Block> POTTED_SHEPHERDS_PURSE =
                BLOCKS.register("potted_shepherds_purse",
                        () -> new FlowerPotBlock(() -> POT, SHEPHERDS_PURSE, POT_PROPERTIES));
        public static final RegistryObject<Block> POTTED_LAVENDER =
                BLOCKS.register("potted_lavender",
                        () -> new FlowerPotBlock(() -> POT, LAVENDER, POT_PROPERTIES));
        public static final RegistryObject<Block> POTTED_GINSENG =
                BLOCKS.register("potted_ginseng",
                        () -> new FlowerPotBlock(() -> POT, GINSENG, POT_PROPERTIES));
        public static final RegistryObject<Block> POTTED_PEPPERMINT =
                BLOCKS.register("potted_peppermint",
                        () -> new FlowerPotBlock(() -> POT, PEPPERMINT, POT_PROPERTIES));
        public static final RegistryObject<Block> POTTED_MOONFLOWER =
                BLOCKS.register("potted_moonflower",
                        () -> new FlowerPotBlock(() -> POT, MOONFLOWER, POT_PROPERTIES));
        public static final RegistryObject<Block> POTTED_PRICKLY_PEAR =
                BLOCKS.register("potted_prickly_pear",
                        () -> new FlowerPotBlock(() -> POT, PRICKLY_PEAR, POT_PROPERTIES));
        public static final RegistryObject<Block> POTTED_PEYOTE =
                BLOCKS.register("potted_peyote",
                        () -> new FlowerPotBlock(() -> POT, PEYOTE, POT_PROPERTIES));
    }
    
    private static final FlowerPotBlock POT = (FlowerPotBlock) net.minecraft.world.level.block.Blocks.FLOWER_POT;
    private static final BlockBehaviour.Properties POT_PROPERTIES = BlockBehaviour.Properties.of()
            .noOcclusion()
            .instabreak()
            .pushReaction(PushReaction.DESTROY);

    public static class BlockItems{
        public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NHerbFlowerExtBox.MOD_ID);
        public static final RegistryObject<Item> BELLFLOWER = BLOCK_ITEMS.register("bellflower",
                () -> new BlockItem(Blocks.BELLFLOWER.get(),new Item.Properties()));
        public static final RegistryObject<Item> GARDEN_MARIGOLD = BLOCK_ITEMS.register("garden_marigold",
                () -> new BlockItem(Blocks.GARDEN_MARIGOLD.get(),new Item.Properties()));
        public static final RegistryObject<Item> SHEPHERDS_PURSE = BLOCK_ITEMS.register("shepherds_purse",
                () -> new BlockItem(Blocks.SHEPHERDS_PURSE.get(),new Item.Properties()));
        public static final RegistryObject<Item> LAVENDER = BLOCK_ITEMS.register("lavender",
                () -> new BlockItem(Blocks.LAVENDER.get(),new Item.Properties()));
        public static final RegistryObject<Item> GINSENG = BLOCK_ITEMS.register("ginseng",
                () -> new BlockItem(Blocks.GINSENG.get(),new Item.Properties()));
        public static final RegistryObject<Item> PEPPERMINT = BLOCK_ITEMS.register("peppermint",
                () -> new BlockItem(Blocks.PEPPERMINT.get(),new Item.Properties()));
        public static final RegistryObject<Item> MOONFLOWER = BLOCK_ITEMS.register("moonflower",
                () -> new BlockItem(Blocks.MOONFLOWER.get(),new Item.Properties()));
        public static final RegistryObject<Item> PRICKLY_PEAR = BLOCK_ITEMS.register("prickly_pear",
                () -> new BlockItem(Blocks.PRICKLY_PEAR.get(),new Item.Properties()));
        public static final RegistryObject<Item> PEYOTE = BLOCK_ITEMS.register("peyote",
                () -> new BlockItem(Blocks.PEYOTE.get(),new Item.Properties()));

        public static final RegistryObject<Item> MILLSTONE = BLOCK_ITEMS.register("millstone",
                () -> new BlockItem(Blocks.MILLSTONE.get(),new Item.Properties()));

        public static final RegistryObject<Item> CLAY_CAULDRON = BLOCK_ITEMS.register("clay_cauldron",
                () -> new BlockItem(Blocks.CLAY_CAULDRON.get(),new Item.Properties()));
    }
}
