package com.Nuaah.NHerbFlowerExtBox.regi.brewingRecipes;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class CustomSplashBrewRecipe implements IBrewingRecipe {
    @Override
    public boolean isInput(ItemStack input) {
        return !input.isEmpty() && input.is(NHerbFlowerExtBoxItems.CUSTOM_POTION.get());
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.getItem() == Items.GUNPOWDER;
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        System.out.println("Checking Splash Recipe - Input: " + input.getItem().toString());

        if (input.getItem() != NHerbFlowerExtBoxItems.CUSTOM_POTION.get()) {
            return ItemStack.EMPTY;
        }

        System.out.println("Result: SPLASH");
        ItemStack result = new ItemStack(NHerbFlowerExtBoxItems.CUSTOM_SPLASH_POTION.get());
        if (input.hasTag()) {
            result.setTag(input.getTag().copy());
        }
        return result;
    }
}
