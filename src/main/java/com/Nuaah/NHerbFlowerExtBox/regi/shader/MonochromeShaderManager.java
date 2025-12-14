package com.Nuaah.NHerbFlowerExtBox.regi.shader;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.Nuaah.NHerbFlowerExtBox.regi.NHerbFlowerExtBoxEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("removal")
public class MonochromeShaderManager {
    private static boolean enabled = false;

    public static final ResourceLocation SHADER =
            new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "shaders/post/grayscale.json");

    public static void tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        boolean shouldEnable = true;
        if (mc.player.hasEffect(NHerbFlowerExtBoxEffect.COLORBLIND.get())) {
            enable();
        } else {
            disable();
        }
    }

    private static void enable() {
        try {
            Minecraft.getInstance().gameRenderer.loadEffect(SHADER);
            enabled = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void disable() {
        try {
            Minecraft.getInstance().gameRenderer.shutdownEffect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            enabled = false;
        }
    }
}
