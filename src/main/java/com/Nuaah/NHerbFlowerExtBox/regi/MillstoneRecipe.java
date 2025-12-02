package com.Nuaah.NHerbFlowerExtBox.regi;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class MillstoneRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final Ingredient input;
    private final ItemStack result;
    private final int processingTime;

    public MillstoneRecipe(ResourceLocation id, Ingredient input, ItemStack result, int processingTime) {
        this.id = id;
        this.input = input;
        this.result = result;
        this.processingTime = processingTime;
    }

    @Override
    public boolean matches(Container inv, Level level) {
        // 例: インベントリの最初のスロットが入力アイテムと一致するかチェック
        if (inv.isEmpty()) {
            return false;
        }

        return input.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NHerbFlowerExtBoxRecipeSerializers.MILLSTONE_GRINDING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return NHerbFlowerExtBoxRecipeType.MILLSTONE_GRINDING_TYPE.get();
    }

    public Ingredient getInput() { return input; }
    public ItemStack getResult() { return result; }
    public int getProcessingTime() { return processingTime; }
}
