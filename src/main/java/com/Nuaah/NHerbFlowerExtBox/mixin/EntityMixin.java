package com.Nuaah.NHerbFlowerExtBox.mixin;

import com.Nuaah.NHerbFlowerExtBox.regi.NHerbFlowerExtBoxEffect;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    private static int tickCounter = 0;

    @Inject(method = "isInWater", at = @At("HEAD"), cancellable = true)
    private void forceUnderWaterForHUD(CallbackInfoReturnable<Boolean> cir) {

        Entity entity = (Entity)(Object)this;

        // Player エンティティであり、かつサーバー側でのみ処理 (通常、クライアント側でも実行されますが、条件判定はサーバー側に限定した方が安全)
        if (!(entity instanceof Player player) || player.level().isClientSide) {
            return;
        }

        tickCounter++;
        if (((Player) entity).hasEffect(NHerbFlowerExtBoxEffect.SUFFOCATION.get())) {
            // 水中にいない場合のみ、強制的に true を返す
            cir.setReturnValue(true);
        }
    }
}
