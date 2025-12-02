package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxItems;
import com.Nuaah.NHerbFlowerExtBox.block.entity.ClayCauldronRenderer;
import com.Nuaah.NHerbFlowerExtBox.block.entity.MillstoneRenderer;
import com.Nuaah.NHerbFlowerExtBox.block.entity.NHerbFlowerExtBoxEntityTypes;
import com.Nuaah.NHerbFlowerExtBox.gui.container.NHerbFlowerExtBoxContainerTypes;
import com.Nuaah.NHerbFlowerExtBox.gui.screen.ClayCauldronScreen;
import com.Nuaah.NHerbFlowerExtBox.gui.screen.MillstoneScreen;
import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
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
        ItemBlockRenderTypes.setRenderLayer(NHerbFlowerExtBoxBlocks.Blocks.POTTED_BELLFLOWER.get(), RenderType.cutout());
        blockScreenRegister();
        Minecraft.getInstance().execute(() -> {
            MonochromeShaderManager.tick(); // 最初に有効化する場合
        });
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
    }

    private static void blockScreenRegister(){
        MenuScreens.register(NHerbFlowerExtBoxContainerTypes.MILLSTONE.get(), MillstoneScreen::new);
        MenuScreens.register(NHerbFlowerExtBoxContainerTypes.CLAY_CAULDRON.get(), ClayCauldronScreen::new);
    }



//    @SubscribeEvent
//    public static void registerIcons(FMLClientSetupEvent event) {
//        Minecraft mc = Minecraft.getInstance();
//        event.enqueueWork(() -> {
//            NHerbFlowerExtBoxEffect.BLEEDING.get().setFactorDataFactory(YourMod.MOD_ID, "textures/mob_effect/my_custom_effect.png");
//        });
//    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {

        // Layer1 の色を NBT から取得する
        event.register((stack, tintIndex) -> {
            // tintIndex == 1 が layer1
            if (tintIndex == 0) {

                List<Float> waterColor = new ArrayList<>(Arrays.asList(0.2f, 0.4f, 1.0f, 1.0f));
                CompoundTag tag = stack.getTag();
                CompoundTag mapTag = tag.getCompound("WaterColors");
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

            // layer0 はそのままの色
            return 0xFFFFFFFF;
        }, NHerbFlowerExtBoxItems.CUSTOM_POTION.get());
    }
}
