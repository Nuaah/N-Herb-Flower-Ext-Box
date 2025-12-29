package com.Nuaah.NHerbFlowerExtBox.item;

import com.Nuaah.NHerbFlowerExtBox.regi.CustomPotionData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("removal")
public class ItemCustomSplashPotion extends Item {
    public ItemCustomSplashPotion()  {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            ThrownPotion potion = new CustomThrownPotion(level, player, stack);
            potion.shootFromRotation(
                    player,
                    player.getXRot(),
                    player.getYRot(),
                    -20.0F,
                    0.5F,
                    1.0F
            );
            level.addFreshEntity(potion);
        }

        player.getCooldowns().addCooldown(this, 20);

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    // ===== ツールチップ =====
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<net.minecraft.network.chat.Component> tooltip,
                                TooltipFlag flag) {
        ListTag list = stack.getOrCreateTag().getList("CustomEffects", Tag.TAG_COMPOUND);

        for (Tag t : list) {
            CompoundTag tag = (CompoundTag) t;

            String name = tag.getString("Name");
            int levelInt = (int) Math.floor(tag.getFloat("Level"));
            int duration = tag.getInt("Duration");

            if (levelInt <= 0) continue;

            var comp = net.minecraft.network.chat.Component
                    .translatable("effect." + name.replace(':', '.'))
                    .append(" Lv." + levelInt + " (" + duration / 20 + "s)").withStyle(ChatFormatting.AQUA);

            tooltip.add(comp);
        }
    }

    // ===== 内部クラス：投擲体 =====
    public static class CustomThrownPotion extends ThrownPotion {

        public CustomThrownPotion(Level level, LivingEntity owner, ItemStack stack) {
            super(level, owner);
            this.setItem(stack);
        }

        @Override
        protected void onHit(net.minecraft.world.phys.HitResult result) {
            if (!level().isClientSide) {

                applyCustomEffects();

                ItemStack stack = this.getItem();
                CompoundTag tag = stack.getTag();

                List<Float> waterColor = new ArrayList<>(Arrays.asList(0.2f, 0.4f, 1.0f, 1.0f));

                CompoundTag mapTag = tag.getCompound("WaterColors");
                for (int i = 0; i < 3; i++) {
                    waterColor.set(i,mapTag.getFloat("WaterColor" + i));
                }

                float rF = waterColor.get(0);
                float gF = waterColor.get(1);
                float bF = waterColor.get(2);

                int r = (int)(rF * 255);
                int g = (int)(gF * 255);
                int b = (int)(bF * 255);

                int color = (r << 16) | (g << 8) | b;

                level().levelEvent(
                        2002,
                        this.blockPosition(),
                        color
                );

                this.discard();

//                applyCustomEffects();
//                this.discard();
            }
        }

        private void applyCustomEffects() {
            ItemStack stack = this.getItem();
            CompoundTag tag = stack.getTag();
            if (tag == null) return;

            ListTag list = tag.getList("CustomEffects", Tag.TAG_COMPOUND);

            BlockPos pos = this.blockPosition();
            double radius = 4.0;

            AABB area = new AABB(
                    pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
                    pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius
            );

            List<LivingEntity> entities =
                    level().getEntitiesOfClass(LivingEntity.class, area);

            for (LivingEntity entity : entities) {
                double dist = this.distanceTo(entity);
                if (dist > radius) continue;

                double scale = 1.0 - (dist / radius);

                for (Tag t : list) {
                    CompoundTag eTag = (CompoundTag) t;

                    String name = eTag.getString("Name");
                    int amp = (int) Math.floor(eTag.getFloat("Level")) - 1;
                    int duration = (int) (eTag.getInt("Duration") * scale);

                    if (amp < 0 || duration <= 0) continue;

                    MobEffect effect = ForgeRegistries.MOB_EFFECTS
                            .getValue(new ResourceLocation(name));

                    if (effect != null) {
                        entity.addEffect(
                            new MobEffectInstance(effect, duration, amp)
                        );
                    }
                }
            }
        }
    }
}