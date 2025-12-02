package com.Nuaah.NHerbFlowerExtBox.mixin;

import com.Nuaah.NHerbFlowerExtBox.regi.NHerbFlowerExtBoxEffect;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(
            method = "heal",
            at = @At("HEAD"),
            cancellable = true
    )

    private void stopNaturalRegen(float amount, CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;

        // プレイヤーだけ自然回復禁止
        if (self instanceof Player player) {
            if (player.hasEffect(NHerbFlowerExtBoxEffect.BLEEDING.get())) {

                ci.cancel();
            }
        }
    }
}
