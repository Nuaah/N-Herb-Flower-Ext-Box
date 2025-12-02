package com.Nuaah.NHerbFlowerExtBox.item;

import com.Nuaah.NHerbFlowerExtBox.regi.CustomPotionData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("removal")
public class ItemCustomPotion extends Item {
    public ItemCustomPotion() {
        super(new Properties());
    }

    public static List<CustomPotionData> getEffects(ItemStack stack) {
        List<CustomPotionData> result = new ArrayList<>();
        CompoundTag tag = stack.getTag();
        if (tag == null) return result;

        ListTag list = tag.getList("CustomEffects", Tag.TAG_COMPOUND);

        for (Tag t : list) {
            result.add(CustomPotionData.fromTag((CompoundTag)t));
        }

        return result;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 32;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {

        if (!level.isClientSide()) {
            for (CustomPotionData ed : getEffects(stack)) {
                System.out.println(ed.name);
                System.out.println(ed.duration);
                System.out.println(ed.level);
            }

            ListTag list = stack.getOrCreateTag().getList("CustomEffects", Tag.TAG_COMPOUND);

            for (Tag t : list) {
                CompoundTag effectTag = (CompoundTag)t;

                String name = effectTag.getString("Name");
                float potionLevel = effectTag.getInt("Level");
                int duration = effectTag.getInt("Duration");

                ResourceLocation effectId = new ResourceLocation(name);
                MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(effectId);

                // 自作効果を取得
                MobEffectInstance instance = new MobEffectInstance(effect,duration,(int)Math.floor(potionLevel));
//                MobEffectInstance effect = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, (int)potionLevel);
                if (instance != null) {
                    entity.addEffect(instance);
                }
            }
        }

        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag p_41424_) {
        ListTag list = stack.getOrCreateTag().getList("CustomEffects", Tag.TAG_COMPOUND);
        System.out.println(list);
        for (Tag t : list) {
            CompoundTag effectTag = (CompoundTag) t;

            String name = effectTag.getString("Name");
            Component effectComponent = Component.translatable("effect." + name.replace(':','.'));

            float potionLevel = effectTag.getInt("Level");
            int duration = effectTag.getInt("Duration");

            Component constituentsComponent = Component.literal(" Lv." + (Math.floor(potionLevel)) + " (" + duration / 20 + "s)");
            Component result = Component.empty().append(effectComponent).append(constituentsComponent).withStyle(ChatFormatting.AQUA);

            tooltip.add(result);
        }
    }
}