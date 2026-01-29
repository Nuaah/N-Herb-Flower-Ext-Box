package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxItems;
import com.Nuaah.NHerbFlowerExtBox.block.entity.*;
import com.Nuaah.NHerbFlowerExtBox.gui.container.NHerbFlowerExtBoxContainerTypes;
import com.Nuaah.NHerbFlowerExtBox.gui.screen.ClayCauldronScreen;
import com.Nuaah.NHerbFlowerExtBox.gui.screen.MillstoneScreen;
import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = NHerbFlowerExtBox.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)

@SuppressWarnings("removal")
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void slientSetup(FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.BELLFLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.GARDEN_MARIGOLD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.SHEPHERDS_PURSE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.LAVENDER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.GINSENG.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.PEPPERMINT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.MOONFLOWER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.PRICKLY_PEAR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.PEYOTE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.PALGANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.ECLATY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.FIREFLIES_MUSHROOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.AURORA_MUSHROOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POMIUM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_BELLFLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_GARDEN_MARIGOLD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_SHEPHERDS_PURSE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_LAVENDER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_GINSENG.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_PEPPERMINT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_MOONFLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_PRICKLY_PEAR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_PEYOTE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_PALGANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_ECLATY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_FIREFLIES_MUSHROOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_AURORA_MUSHROOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_POMIUM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.JEWELED_BRANCH_LEAVE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.JEWELED_BRANCH_SAPLING.get(), RenderType.cutout());
        blockScreenRegister();
    }

    @SubscribeEvent
    public static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        // ここでローダーを登録！
        event.registerReloadListener(ConstituentsJsonLoader.INSTANCE);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {

        // ← ここで BlockEntityType と Renderer を紐づける！
        event.registerBlockEntityRenderer(
            NHerbFlowerExtBoxEntityTypes.MILLSTONE.get(),
            MillstoneRenderer::new
        );

        event.registerBlockEntityRenderer(
            NHerbFlowerExtBoxEntityTypes.CLAY_CAULDRON.get(),
            ClayCauldronRenderer::new
        );

        event.registerBlockEntityRenderer(
                NHerbFlowerExtBoxEntityTypes.FIREFLIES_MUSHROOM.get(),
                FirefliesMushroomRenderer::new
        );

        event.registerBlockEntityRenderer(
                NHerbFlowerExtBoxEntityTypes.AURORA_MUSHROOM.get(),
                AuroraMushroomRenderer::new
        );
    }

    private static void blockScreenRegister(){
        MenuScreens.register(NHerbFlowerExtBoxContainerTypes.MILLSTONE.get(), MillstoneScreen::new);
        MenuScreens.register(NHerbFlowerExtBoxContainerTypes.CLAY_CAULDRON.get(), ClayCauldronScreen::new);
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {

        // Layer1 の色を NBT から取得する
        event.register((stack, tintIndex) -> {

            if (tintIndex == 0) {
                List<Float> waterColor = new ArrayList<>(Arrays.asList(0.2f, 0.4f, 1.0f, 1.0f));
                CompoundTag tag = stack.getTag();
                CompoundTag mapTag = tag.getCompound("WaterColors");

                if(!mapTag.isEmpty()){
                    for (int i = 0; i < 3; i++) {
                        waterColor.set(i,mapTag.getFloat("WaterColor" + i));
                    }

                    float rF = waterColor.get(0);
                    float gF = waterColor.get(1);
                    float bF = waterColor.get(2);

                    int r = (int)(rF * 255);
                    int g = (int)(gF * 255);
                    int b = (int)(bF * 255);

                    int color = (r << 16) | (g << 8) | b;
                    return color;
                }
            }

            return 0xFFFFFFFF;
        }, NHerbFlowerExtBoxItems.CUSTOM_POTION.get());

        event.register((stack, tintIndex) -> {

            if (tintIndex == 0) {
                List<Float> waterColor = new ArrayList<>(Arrays.asList(0.2f, 0.4f, 1.0f, 1.0f));
                CompoundTag tag = stack.getTag();
                CompoundTag mapTag = tag.getCompound("WaterColors");

                if(!mapTag.isEmpty()){
                    for (int i = 0; i < 3; i++) {
                        waterColor.set(i,mapTag.getFloat("WaterColor" + i));
                    }

                    float rF = waterColor.get(0);
                    float gF = waterColor.get(1);
                    float bF = waterColor.get(2);

                    int r = (int)(rF * 255);
                    int g = (int)(gF * 255);
                    int b = (int)(bF * 255);

                    int color = (r << 16) | (g << 8) | b;
                    return color;
                }
            }

            return 0xFFFFFFFF;
        }, NHerbFlowerExtBoxItems.CUSTOM_SPLASH_POTION.get());

        event.register((stack, tintIndex) -> {

            if (tintIndex == 0) {
                return 0x6699FFFF;
            }

            // layer0 はそのままの色
            return 0xFFFFFFFF;
        }, NHerbFlowerExtBoxItems.ETHANOL_POTION.get());
    }
}
