package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NHerbFlowerExtBoxRecipeType {
    // RecipeTypeのDeferredRegisterを作成
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, NHerbFlowerExtBox.MOD_ID);

    // 🔨 Millstone RecipeType の登録
    // この RegistryObject を使って、MillstoneRecipe の RecipeType インスタンスを取得します。
    public static final RegistryObject<RecipeType<MillstoneRecipe>> MILLSTONE_GRINDING_TYPE =
            RECIPE_TYPES.register("millstone_grinding", () -> new RecipeType<MillstoneRecipe>() {
                @Override
                public String toString() {
                    return NHerbFlowerExtBox.MOD_ID + ":millstone_grinding";
                }
            });

    // 登録処理をメインModクラスから呼び出す必要があります。
}
