package com.Nuaah.NHerbFlowerExtBox.regi.brewingRecipes;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class CustomBrewRecipe implements IBrewingRecipe {
    @Override
    public boolean isInput(ItemStack input) {
        return !input.isEmpty() && input.is(Items.POTION) && !input.is(NHerbFlowerExtBoxItems.CUSTOM_POTION.get());
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return !ingredient.isEmpty() && ingredient.is(Items.SUGAR_CANE);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
// デバッグログ：入力アイテムの名前を表示
        System.out.println("Checking Ethanol Recipe - Input: " + input.getItem().toString());

        if (input.getItem() != Items.POTION) {
            return ItemStack.EMPTY;
        }

        System.out.println("Result: ETHANOL");
        return new ItemStack(NHerbFlowerExtBoxItems.ETHANOL_POTION.get());
    }
}
