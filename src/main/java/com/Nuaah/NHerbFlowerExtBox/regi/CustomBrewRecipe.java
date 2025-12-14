package com.Nuaah.NHerbFlowerExtBox.regi;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class CustomBrewRecipe implements IBrewingRecipe {
    @Override
    public boolean isInput(ItemStack input) {
        return input.is(Items.WARPED_BUTTON);
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.is(Items.SUGAR_CANE);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        return new ItemStack(NHerbFlowerExtBoxItems.ETHANOL_POTION.get());
    }
}
