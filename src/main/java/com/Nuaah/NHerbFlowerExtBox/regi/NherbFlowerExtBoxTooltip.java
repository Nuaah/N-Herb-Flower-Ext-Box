package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class NherbFlowerExtBoxTooltip {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

//        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString(); // 例: bellflower
        String id = stack.getItem().toString(); // 例: bellflower
        ConstituentsData data = ConstituentsJsonLoader.CONSTITUENTS_DATA.get(id);
        System.out.println(ConstituentsJsonLoader.CONSTITUENTS_DATA);
        System.out.println(id);
        System.out.println(data);
        if (data != null) {
            tooltip.add(Component.translatable("tooltip.constituent." + NHerbFlowerExtBox.MOD_ID + ".effect").withStyle(ChatFormatting.GREEN));
            for (ConstituentsData.ComponentData c : data.components){

                Component partLine = Component.translatable("tooltip.part." + NHerbFlowerExtBox.MOD_ID + "." + c.part);
                Component constituentLine = Component.translatable("effect." + c.id.replace(':','.')).withStyle(ChatFormatting.BOLD);
                Component other = Component.literal(" - Lv." + c.amount + " " + c.duration / 20.0F + "s");

                Component result;
                if(c.part != null){
                    result = Component.empty().append(partLine).append(constituentLine).append(other);
                } else {
                    result = Component.empty().append(constituentLine).append(other);
                }

                if(c.soluble.equals("water")){
                    result = result.copy().withStyle(ChatFormatting.BLUE);
                } else if (c.soluble.equals("fat")){
                    result = result.copy().withStyle(ChatFormatting.GOLD);
                }

                tooltip.add(result);
            }
        }
    }
}
