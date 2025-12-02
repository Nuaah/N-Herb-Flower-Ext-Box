package com.Nuaah.NHerbFlowerExtBox.regi;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.jetbrains.annotations.Nullable;

public class MillstoneRecipeSerializer implements RecipeSerializer<MillstoneRecipe> {
    @Override
    public MillstoneRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        // 必要なデータをJSONから取得
        Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
        ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
        int time = GsonHelper.getAsInt(json, "processing_time", 200); // 処理時間を読み込む

        return new MillstoneRecipe(recipeId, input, result, time);
    }

    @Override
    public MillstoneRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
        Ingredient input = Ingredient.fromNetwork(buf);
        ItemStack output = buf.readItem();
        int time = buf.readInt();
        return new MillstoneRecipe(id, input, output, time);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, MillstoneRecipe recipe) {
        recipe.getInput().toNetwork(buf);
        buf.writeItem(recipe.getResultItem(null));
        buf.writeInt(recipe.getProcessingTime());
    }
}
