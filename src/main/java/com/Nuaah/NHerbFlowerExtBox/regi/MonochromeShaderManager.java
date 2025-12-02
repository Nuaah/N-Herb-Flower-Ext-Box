package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("removal")
public class MonochromeShaderManager {
    private static boolean enabled = false;

    private static final ResourceLocation SHADER =
            new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "shaders/post/grayscale.json");

    public static void tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        boolean shouldEnable = true;
        System.out.println("TICK");
        if (shouldEnable) {
            enable();
        } else if (!shouldEnable && enabled) {
            disable();
        }
    }

    private static void enable() {
        System.out.println("YEs");
        try {
            Minecraft.getInstance().gameRenderer.loadEffect(SHADER);
            enabled = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void disable() {
        System.out.println("No");
        try {
            Minecraft.getInstance().gameRenderer.shutdownEffect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            enabled = false;
        }
    }
}
