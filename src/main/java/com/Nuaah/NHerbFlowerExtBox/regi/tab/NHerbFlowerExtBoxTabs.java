package com.Nuaah.NHerbFlowerExtBox.regi.tab;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxItems;
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

    public static final DeferredRegister<CreativeModeTab> MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NHerbFlowerExtBox.MOD_ID);

    public static final RegistryObject<CreativeModeTab> NHERBFLOWEREXTBOX_MAIN = MOD_TABS.register("nherbflowerextbox_main",
        () -> {return CreativeModeTab.builder()
            .icon(() -> new ItemStack(NHerbFlowerExtBoxBlocks.BlockItems.BELLFLOWER.get()))
            .title(Component.translatable("itemGroup.NHerbFlowerExtBoxMain"))
            .displayItems((param,output) -> {
                for(Item item : NHerbFlowerExtBoxMain.items){
                    output.accept(item);
                }
            })
            .build();
        });
}
