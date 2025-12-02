package com.Nuaah.NHerbFlowerExtBox.main;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxItems;
import com.Nuaah.NHerbFlowerExtBox.block.entity.NHerbFlowerExtBoxEntityTypes;
import com.Nuaah.NHerbFlowerExtBox.gui.container.NHerbFlowerExtBoxContainerTypes;
import com.Nuaah.NHerbFlowerExtBox.regi.NHerbFlowerExtBoxEffect;
import com.Nuaah.NHerbFlowerExtBox.regi.NHerbFlowerExtBoxRecipeSerializers;
import com.Nuaah.NHerbFlowerExtBox.regi.NHerbFlowerExtBoxRecipeType;
import com.Nuaah.NHerbFlowerExtBox.regi.tab.NHerbFlowerExtBoxTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("nherbflowerextbox")
@SuppressWarnings("removal")
public class NHerbFlowerExtBox {

    public static final String MOD_ID = "nherbflowerextbox";

    public NHerbFlowerExtBox(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        NHerbFlowerExtBoxBlocks.Blocks.BLOCKS.register(bus);
        NHerbFlowerExtBoxBlocks.BlockItems.BLOCK_ITEMS.register(bus);
        NHerbFlowerExtBoxItems.ITEMS.register(bus);
        NHerbFlowerExtBoxTabs.MOD_TABS.register(bus);
        NHerbFlowerExtBoxEntityTypes.BLOCK_ENTITY_TYPES.register(bus);
        NHerbFlowerExtBoxContainerTypes.MENU_TYPES.register(bus);
        NHerbFlowerExtBoxRecipeSerializers.SERIALIZERS.register(bus);
        NHerbFlowerExtBoxRecipeType.RECIPE_TYPES.register(bus);
        NHerbFlowerExtBoxEffect.EFFECTS.register(bus);



        bus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                    new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "bellflower"),
                    NHerbFlowerExtBoxBlocks.Blocks.POTTED_BELLFLOWER
            );
        });
    }
}
