package com.Nuaah.NHerbFlowerExtBox.regi.tab;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class NHerbFlowerExtBoxTabs {

    public static final DeferredRegister<CreativeModeTab> MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NHerbFlowerExtBox    .MOD_ID);

    public static final RegistryObject<CreativeModeTab> NGEMEXTBOX_MAIN = MOD_TABS.register("ngemextbox_main",
            () -> {return CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.MAGMA_BLOCK))
                    .title(Component.translatable("itemGroup.NGemExtBoxMain"))
                    .displayItems((param,output) -> {
                        for(Item item : NHerbFlowerExtBoxMain.items){
                            output.accept(item);
                        }
                    })
                    .build();
            });
}
