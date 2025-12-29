package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.Nuaah.NHerbFlowerExtBox.regi.capability.NHerbFlowerExtBoxCapabilities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NHerbFlowerExtBox.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerModEventBusSubscriber {

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        NHerbFlowerExtBoxCapabilities.register(event);
    }
}