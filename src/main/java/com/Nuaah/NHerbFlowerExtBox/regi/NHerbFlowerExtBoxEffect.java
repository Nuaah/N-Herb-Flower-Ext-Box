package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.effect.*;
import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NHerbFlowerExtBoxEffect {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, NHerbFlowerExtBox.MOD_ID);

    public static final RegistryObject<MobEffect> BLEEDING =
            EFFECTS.register("bleeding", EffectBleeding::new);
    public static final RegistryObject<MobEffect> CONFUSION =
            EFFECTS.register("confusion", EffectConfusion::new);
    public static final RegistryObject<MobEffect> PARALYSIS =
            EFFECTS.register("paralysis", EffectParalysis::new);
    public static final RegistryObject<MobEffect> STAGGER =
            EFFECTS.register("stagger", EffectStagger::new);
    public static final RegistryObject<MobEffect> ANOREXIA =
            EFFECTS.register("anorexia", EffectAnorexia::new);
    public static final RegistryObject<MobEffect> SUFFOCATION =
            EFFECTS.register("suffocation", EffectSuffocation::new);
    public static final RegistryObject<MobEffect> COLORBLIND =
            EFFECTS.register("colorblind", EffectColorblind::new);
    public static final RegistryObject<MobEffect> BLOODSHOT =
            EFFECTS.register("bloodshot", EffectBloodshot::new);
    public static final RegistryObject<MobEffect> SATIETY =
            EFFECTS.register("satiety", EffectSatiety::new);
    public static final RegistryObject<MobEffect> RAINBOW_VISION =
            EFFECTS.register("rainbow_vision", EffectRainbowVision::new);

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
