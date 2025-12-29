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
        public static final RegistryObject<Block> SAP_EXTRACTOR = BLOCKS.register("sap_extractor", BlockSapExtractor::new);

        public static final RegistryObject<Block> BELLFLOWER = BLOCKS.register("bellflower", BlockBellflower::new);
        public static final RegistryObject<Block> GARDEN_MARIGOLD = BLOCKS.register("garden_marigold", BlockGardenMarigold::new);
        public static final RegistryObject<Block> SHEPHERDS_PURSE = BLOCKS.register("shepherds_purse", BlockShepherdsPurse::new);
        public static final RegistryObject<Block> LAVENDER = BLOCKS.register("lavender", BlockLavender::new);
        public static final RegistryObject<Block> GINSENG = BLOCKS.register("ginseng", BlockGinseng::new);
        public static final RegistryObject<Block> PEPPERMINT = BLOCKS.register("peppermint", BlockPeppermint::new);
        public static final RegistryObject<Block> MOONFLOWER = BLOCKS.register("moonflower", BlockMoonflower::new);
        public static final RegistryObject<Block> PRICKLY_PEAR = BLOCKS.register("prickly_pear", BlockPricklyPear::new);
        public static final RegistryObject<Block> PEYOTE = BLOCKS.register("peyote", BlockPeyote::new);
        public static final RegistryObject<Block> PALGANT = BLOCKS.register("palgant", BlockPalgant::new);
        public static final RegistryObject<Block> ECLATY = BLOCKS.register("eclaty", BlockEclaty::new);
        public static final RegistryObject<Block> FIREFLIES_MUSHROOM = BLOCKS.register("fireflies_mushroom", BlockFirefliesMushroom::new);
        public static final RegistryObject<Block> FIREFLIES_MUSHROOM_TOP = BLOCKS.register("fireflies_mushroom_top", BlockFirefliesMushroom::new);
        public static final RegistryObject<Block> AURORA_MUSHROOM = BLOCKS.register("aurora_mushroom", BlockAuroraMushroom::new);
        public static final RegistryObject<Block> AURORA_MUSHROOM_TOP = BLOCKS.register("aurora_mushroom_top", BlockAuroraMushroom::new);
        public static final RegistryObject<Block> POMIUM = BLOCKS.register("pomium", BlockPomium::new);

        public static final RegistryObject<Block> STRIPPED_JEWELED_BRANCH_LOG = BLOCKS.register("stripped_jeweled_branch_log", BlockStrippedJeweledBranchLog::new);
        public static final RegistryObject<Block> JEWELED_BRANCH_LOG = BLOCKS.register("jeweled_branch_log", BlockJeweledBranchLog::new);
        public static final RegistryObject<Block> JEWELED_BRANCH_LEAVE = BLOCKS.register("jeweled_branch_leave", () -> new BlockJeweledBranchLeave(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.OAK_LEAVES)));
        public static final RegistryObject<Block> JEWELED_BRANCH_SAPLING = BLOCKS.register("jeweled_branch_sapling", BlockJeweledBranchSapling::new);
        public static final RegistryObject<Block> JEWELED_BRANCH_PLANKS = BLOCKS.register("jeweled_branch_planks", () -> new IntroductionLogBlock(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.OAK_PLANKS)));

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
        public static final RegistryObject<Block> POTTED_PALGANT =
                BLOCKS.register("potted_palgant",
                        () -> new FlowerPotBlock(() -> POT, PALGANT, POT_PROPERTIES));
        public static final RegistryObject<Block> POTTED_ECLATY =
                BLOCKS.register("potted_eclaty",
                        () -> new FlowerPotBlock(() -> POT, ECLATY, POT_PROPERTIES));
        public static final RegistryObject<Block> POTTED_FIREFLIES_MUSHROOM =
                BLOCKS.register("potted_fireflies_mushroom",
                        () -> new FlowerPotBlock(() -> POT, FIREFLIES_MUSHROOM, POT_PROPERTIES));
        public static final RegistryObject<Block> POTTED_AURORA_MUSHROOM =
                BLOCKS.register("potted_aurora_mushroom",
                        () -> new FlowerPotBlock(() -> POT, AURORA_MUSHROOM, POT_PROPERTIES));
        public static final RegistryObject<Block> POTTED_POMIUM =
                BLOCKS.register("potted_pomium",
                        () -> new FlowerPotBlock(() -> POT, POMIUM, POT_PROPERTIES));
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
        public static final RegistryObject<Item> PALGANT = BLOCK_ITEMS.register("palgant",
                () -> new BlockItem(Blocks.PALGANT.get(),new Item.Properties()));
        public static final RegistryObject<Item> ECLATY = BLOCK_ITEMS.register("eclaty",
                () -> new BlockItem(Blocks.ECLATY.get(),new Item.Properties()));
        public static final RegistryObject<Item> FIREFLIES_MUSHROOM = BLOCK_ITEMS.register("fireflies_mushroom",
                () -> new BlockItem(Blocks.FIREFLIES_MUSHROOM.get(),new Item.Properties()));
        public static final RegistryObject<Item> AURORA_MUSHROOM = BLOCK_ITEMS.register("aurora_mushroom",
                () -> new BlockItem(Blocks.AURORA_MUSHROOM.get(),new Item.Properties()));
        public static final RegistryObject<Item> POMIUM = BLOCK_ITEMS.register("pomium",
                () -> new BlockItem(Blocks.POMIUM.get(),new Item.Properties()));

        public static final RegistryObject<Item> STRIPPED_JEWELED_BRANCH_LOG = BLOCK_ITEMS.register("stripped_jeweled_branch_log",
                () -> new BlockItem(Blocks.STRIPPED_JEWELED_BRANCH_LOG.get(),new Item.Properties()));
        public static final RegistryObject<Item> JEWELED_BRANCH_LOG = BLOCK_ITEMS.register("jeweled_branch_log",
                () -> new BlockItem(Blocks.JEWELED_BRANCH_LOG.get(),new Item.Properties()));
        public static final RegistryObject<Item> JEWELED_BRANCH_LEAVE = BLOCK_ITEMS.register("jeweled_branch_leave",
                () -> new BlockItem(Blocks.JEWELED_BRANCH_LEAVE.get(),new Item.Properties()));
        public static final RegistryObject<Item> JEWELED_BRANCH_SAPLING = BLOCK_ITEMS.register("jeweled_branch_sapling",
                () -> new BlockItem(Blocks.JEWELED_BRANCH_SAPLING.get(),new Item.Properties()));
        public static final RegistryObject<Item> JEWELED_BRANCH_PLANKS = BLOCK_ITEMS.register("jeweled_branch_planks",
                () -> new BlockItem(Blocks.JEWELED_BRANCH_PLANKS.get(),new Item.Properties()));

        public static final RegistryObject<Item> MILLSTONE = BLOCK_ITEMS.register("millstone",
                () -> new BlockItem(Blocks.MILLSTONE.get(),new Item.Properties()));

        public static final RegistryObject<Item> CLAY_CAULDRON = BLOCK_ITEMS.register("clay_cauldron",
                () -> new BlockItem(Blocks.CLAY_CAULDRON.get(),new Item.Properties()));

        public static final RegistryObject<Item> SAP_EXTRACTOR = BLOCK_ITEMS.register("sap_extractor",
                () -> new BlockItem(Blocks.SAP_EXTRACTOR.get(),new Item.Properties()));
    }
}
