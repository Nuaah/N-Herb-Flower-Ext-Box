package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NHerbFlowerExtBoxRecipeSerializers {
    // RecipeSerializerのDeferredRegisterを作成
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, NHerbFlowerExtBox.MOD_ID);

    // 🔨 MillstoneRecipeSerializerの登録
    // "millstone_grinding" がレシピJSONファイルで指定する "type" のIDになります。
    public static final RegistryObject<RecipeSerializer<MillstoneRecipe>> MILLSTONE_GRINDING =
            SERIALIZERS.register("millstone_grinding", MillstoneRecipeSerializer::new);

    // 他のカスタムレシピがあれば、ここに追加登録していきます...
}
