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

    public static final RegistryObject<Item> DRIED_BELLFLOWER = ITEMS.register("dried_bellflower", ItemDriedBellfower::new);
    public static final RegistryObject<Item> BELLFLOWER_POWDER = ITEMS.register("bellflower_powder", ItemBellfowerPowder::new);
    public static final RegistryObject<Item> DRIED_GARDEN_MARIGOLD = ITEMS.register("dried_garden_marigold", ItemDriedGardenMarigold::new);
    public static final RegistryObject<Item> GARDEN_MARIGOLD_POWDER = ITEMS.register("garden_marigold_powder", ItemGardenMarigoldPowder::new);
}
