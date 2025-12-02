package com.Nuaah.NHerbFlowerExtBox.gui.slot;

import com.Nuaah.NHerbFlowerExtBox.regi.tag.NHerbFlowerExtBoxTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MillstoneSlot extends SlotItemHandler {
    public MillstoneSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(NHerbFlowerExtBoxTags.Items.DRIED_HERBS);
    }
}
