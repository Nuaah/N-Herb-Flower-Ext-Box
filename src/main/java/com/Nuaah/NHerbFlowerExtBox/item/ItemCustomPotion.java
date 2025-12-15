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
import net.minecraft.world.item.*;
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
        super(new Properties().stacksTo(1));
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
                int potionLevel = (int)Math.floor(effectTag.getFloat("Level"));
                int duration = effectTag.getInt("Duration");

                if(potionLevel <= 0) continue;

                potionLevel -= 1; //0スタート

                ResourceLocation effectId = new ResourceLocation(name);
                MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(effectId);

                System.out.println("effect");
                System.out.println(effect);
                // 自作効果を取得
                MobEffectInstance instance = new MobEffectInstance(effect,duration,potionLevel);
                System.out.println(instance);
                if (instance != null ) {
                    entity.addEffect(instance);
                }
            }
        }

        if (entity instanceof Player player){
            if (!player.getAbilities().instabuild) player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GLASS_BOTTLE));
        }

        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag p_41424_) {
        ListTag list = stack.getOrCreateTag().getList("CustomEffects", Tag.TAG_COMPOUND);
        System.out.println(list);
        for (Tag t : list) {

            CompoundTag effectTag = (CompoundTag) t;
            System.out.println(effectTag);

            String name = effectTag.getString("Name");
            int potionLevel = (int)Math.floor(effectTag.getFloat("Level"));
            int duration = effectTag.getInt("Duration");

            if(potionLevel <= 0) continue;

            Component effectComponent = Component.translatable("effect." + name.replace(':','.'));
            Component constituentsComponent = Component.literal(" Lv." + potionLevel + " (" + duration / 20 + "s)");
            Component result = Component.empty().append(effectComponent).append(constituentsComponent).withStyle(ChatFormatting.AQUA);

            tooltip.add(result);
        }
    }
}