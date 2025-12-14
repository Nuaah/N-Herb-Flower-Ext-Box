package com.Nuaah.NHerbFlowerExtBox.effect;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

import java.util.function.Consumer;

@SuppressWarnings("removal")
public class EffectRainbowVision extends MobEffect {

    private static final ResourceLocation ICON = new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "textures/gui/effect/rainbow_vision.png");

    public EffectRainbowVision() {
        super(MobEffectCategory.HARMFUL, 0x4B1A1A);
    }

    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new IClientMobEffectExtensions() {

            @Override
            public boolean renderInventoryIcon(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
                guiGraphics.blit(ICON, x, y + 7, 18, 18, 0, 0, 18, 18, 18, 18);
                return true; // trueでデフォルト描画を防ぐ
            }

            @Override
            public boolean renderGuiIcon(MobEffectInstance instance, Gui gui, GuiGraphics guiGraphics, int x, int y, float z, float alpha) {
                guiGraphics.blit(ICON, x + 3, y + 3, 18, 18, 0, 0, 18, 18, 18, 18);
                return true; // trueでデフォルト描画を防ぐ
            }
        });
    }
}
