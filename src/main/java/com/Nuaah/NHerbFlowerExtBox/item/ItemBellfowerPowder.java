package com.Nuaah.NHerbFlowerExtBox.item;

import com.Nuaah.NHerbFlowerExtBox.regi.NHerbFlowerExtBoxEffect;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemBellfowerPowder extends Item {
    public ItemBellfowerPowder() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        p_41433_.addEffect(new MobEffectInstance(NHerbFlowerExtBoxEffect.RAINBOW_VISION.get(),200,0));
        return super.use(p_41432_, p_41433_, p_41434_);
    }
}
