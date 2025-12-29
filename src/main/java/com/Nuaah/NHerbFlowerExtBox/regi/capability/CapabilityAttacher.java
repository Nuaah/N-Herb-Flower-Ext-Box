package com.Nuaah.NHerbFlowerExtBox.regi.capability;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NHerbFlowerExtBox.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
@SuppressWarnings("removal")
public class CapabilityAttacher {

    @SubscribeEvent
    public static void attachCapabilityItem(AttachCapabilitiesEvent<ItemStack> event){
        ItemStack stack = event.getObject();

        if (stack.is(Tags.Items.ARMORS)
                || stack.is(ItemTags.SWORDS)
                || stack.is(ItemTags.PICKAXES)
                || stack.is(ItemTags.AXES)
                || stack.is(ItemTags.SHOVELS)){

            event.addCapability(
                    new ResourceLocation(NHerbFlowerExtBox.MOD_ID,"potion_cap"),
                    new PotionCapabilityProvider(stack)
            );
        }
    }
}
