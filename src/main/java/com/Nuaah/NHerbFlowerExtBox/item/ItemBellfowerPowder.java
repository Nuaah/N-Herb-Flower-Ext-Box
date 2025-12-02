package com.Nuaah.NHerbFlowerExtBox.item;

import com.Nuaah.NHerbFlowerExtBox.regi.NHerbFlowerExtBoxEffect;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemBellfowerPowder extends Item {
    public ItemBellfowerPowder() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        player.addEffect(new MobEffectInstance(
            NHerbFlowerExtBoxEffect.SUFFOCATION.get(),
            20 * 30,  // 30秒
            0         // amplifier
        ));
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
}
