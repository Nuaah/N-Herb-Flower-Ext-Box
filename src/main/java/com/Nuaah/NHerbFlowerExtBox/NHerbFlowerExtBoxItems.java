package com.Nuaah.NHerbFlowerExtBox;

import com.Nuaah.NHerbFlowerExtBox.item.*;
import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NHerbFlowerExtBoxItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NHerbFlowerExtBox.MOD_ID);

    public static final RegistryObject<Item> CUSTOM_POTION = ITEMS.register("custom_potion", ItemCustomPotion::new);
    public static final RegistryObject<Item> CUSTOM_SPLASH_POTION = ITEMS.register("custom_splash_potion", ItemCustomSplashPotion::new);
    public static final RegistryObject<Item> ETHANOL_POTION = ITEMS.register("ethanol_potion", ItemEthanolPotion::new);

    public static final RegistryObject<Item> DRIED_BELLFLOWER = ITEMS.register("dried_bellflower", ItemDriedBellfower::new);
    public static final RegistryObject<Item> BELLFLOWER_POWDER = ITEMS.register("bellflower_powder", ItemBellfowerPowder::new);
    public static final RegistryObject<Item> DRIED_GARDEN_MARIGOLD = ITEMS.register("dried_garden_marigold", ItemDriedGardenMarigold::new);
    public static final RegistryObject<Item> GARDEN_MARIGOLD_POWDER = ITEMS.register("garden_marigold_powder", ItemGardenMarigoldPowder::new);
    public static final RegistryObject<Item> DRIED_LAVENDER = ITEMS.register("dried_lavender", ItemDriedLavender::new);
    public static final RegistryObject<Item> LAVENDER_POWDER = ITEMS.register("lavender_powder", ItemLavenderPowder::new);
    public static final RegistryObject<Item> DRIED_GINSENG = ITEMS.register("dried_ginseng", ItemDriedGinseng::new);
    public static final RegistryObject<Item> GINSENG_POWDER = ITEMS.register("ginseng_powder", ItemGinsengPowder::new);
    public static final RegistryObject<Item> DRIED_MOONFLOWER = ITEMS.register("dried_moonflower", ItemDriedMoonflower::new);
    public static final RegistryObject<Item> MOONFLOWER_POWDER = ITEMS.register("moonflower_powder", ItemMoonflowerPowder::new);
    public static final RegistryObject<Item> DRIED_SHEPHERDS_PURSE = ITEMS.register("dried_shepherds_purse", ItemDriedShepherdsPurse::new);
    public static final RegistryObject<Item> SHEPHERDS_PURSE_POWDER = ITEMS.register("shepherds_purse_powder", ItemShepherdsPursePowder::new);
    public static final RegistryObject<Item> DRIED_PEPPERMINT = ITEMS.register("dried_peppermint", ItemDriedPeppermint::new);
    public static final RegistryObject<Item> PEPPERMINT_POWDER = ITEMS.register("peppermint_powder", ItemPeppermintPowder::new);
    public static final RegistryObject<Item> DRIED_PEYOTE = ITEMS.register("dried_peyote", ItemDriedPeyote::new);
    public static final RegistryObject<Item> PEYOTE_POWDER = ITEMS.register("peyote_powder", ItemPeyotePowder::new);
    public static final RegistryObject<Item> DRIED_PRICKLY_PEAR = ITEMS.register("dried_prickly_pear", ItemDriedPricklyPear::new);
    public static final RegistryObject<Item> PRICKLY_PEAR_POWDER = ITEMS.register("prickly_pear_powder", ItemPricklyPearPowder::new);
    public static final RegistryObject<Item> POMIUM_POWDER = ITEMS.register("pomium_powder", ItemPomiumPowder::new);
    public static final RegistryObject<Item> DRIED_POMIUM = ITEMS.register("dried_pomium", ItemDriedPomium::new);
    public static final RegistryObject<Item> FIREFLIES_MUSHROOM_POWDER = ITEMS.register("fireflies_mushroom_powder", ItemFirefliesMushroomPowder::new);
    public static final RegistryObject<Item> DRIED_FIREFLIES_MUSHROOM = ITEMS.register("dried_fireflies_mushroom", ItemDriedFirefliesMushroom::new);
    public static final RegistryObject<Item> AURORA_MUSHROOM_POWDER = ITEMS.register("aurora_mushroom_powder", ItemAuroraMushroomPowder::new);
    public static final RegistryObject<Item> DRIED_AURORA_MUSHROOM = ITEMS.register("dried_aurora_mushroom", ItemDriedAuroraMushroom::new);
    public static final RegistryObject<Item> ECLATY_POWDER = ITEMS.register("eclaty_powder", ItemEclatyPowder::new);
    public static final RegistryObject<Item> DRIED_ECLATY = ITEMS.register("dried_eclaty", ItemDriedEclaty::new);
    public static final RegistryObject<Item> PALGANT_POWDER = ITEMS.register("palgant_powder", ItemPalgantPowder::new);
    public static final RegistryObject<Item> DRIED_PALGANT = ITEMS.register("dried_palgant", ItemDriedPalgant::new);

    public static final RegistryObject<Item> DRIED_WITHER_ROSE = ITEMS.register("dried_wither_rose", ItemDriedWitherRose::new);
    public static final RegistryObject<Item> WITHER_ROSE_POWDER = ITEMS.register("wither_rose_powder", ItemWitherRosePowder::new);
    public static final RegistryObject<Item> DRIED_CORNFLOWER = ITEMS.register("dried_cornflower", ItemDriedCornflower::new);
    public static final RegistryObject<Item> CORNFLOWER_POWDER = ITEMS.register("cornflower_powder", ItemCornflowerPowder::new);
    public static final RegistryObject<Item> DRIED_LILY_OF_THE_VALLEY = ITEMS.register("dried_lily_of_the_valley", ItemDriedLilyOfTheValley::new);
    public static final RegistryObject<Item> LILY_OF_THE_VALLEY_POWDER = ITEMS.register("lily_of_the_valley_powder", ItemLilyOfTheValleyPowder::new);
    public static final RegistryObject<Item> DRIED_DANDELION = ITEMS.register("dried_dandelion", ItemDriedDandelion::new);
    public static final RegistryObject<Item> DANDELION_POWDER = ITEMS.register("dandelion_powder", ItemDandelionPowder::new);
    public static final RegistryObject<Item> DRIED_POPPY = ITEMS.register("dried_poppy", ItemDriedPoppy::new);
    public static final RegistryObject<Item> POPPY_POWDER = ITEMS.register("poppy_powder", ItemPoppyPowder::new);
    public static final RegistryObject<Item> DRIED_ALLIUM = ITEMS.register("dried_allium", ItemDriedAllium::new);
    public static final RegistryObject<Item> ALLIUM_POWDER = ITEMS.register("allium_powder", ItemAlliumPowder::new);
}
