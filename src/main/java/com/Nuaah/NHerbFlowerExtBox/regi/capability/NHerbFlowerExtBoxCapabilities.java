package com.Nuaah.NHerbFlowerExtBox.regi.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class NHerbFlowerExtBoxCapabilities {
    public static Capability<CombinePotion> POTION_CAP = CapabilityManager.get(new CapabilityToken<CombinePotion>(){});

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(CombinePotion.class);
    }
}
