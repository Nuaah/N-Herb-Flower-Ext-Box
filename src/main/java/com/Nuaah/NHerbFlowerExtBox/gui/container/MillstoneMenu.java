package com.Nuaah.NHerbFlowerExtBox.gui.container;

import com.Nuaah.NHerbFlowerExtBox.block.entity.MillstoneEntity;
import com.Nuaah.NHerbFlowerExtBox.gui.slot.MillstoneSlot;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MillstoneMenu extends AbstractContainerMenu {
    private final MillstoneEntity blockEntity;

    public MillstoneMenu(int a, Inventory playerInventory, MillstoneEntity entity) {
        super(NHerbFlowerExtBoxContainerTypes.MILLSTONE.get(), a);
        checkContainerSize(playerInventory,6);

        IItemHandler handler = entity.getItemHandler();

        for (int i = 0; i < 3; i++) {
            this.addSlot(new MillstoneSlot(handler,i,62 + 18 * i,17));
        }

        for (int i = 0; i < 3; i++) {
            this.addSlot(new MillstoneSlot(handler,3 + i,62 + 18 * i,53){

                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return false;
                }
            });
        }

        this.blockEntity = entity;

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack original = slot.getItem();
            newStack = original.copy();

            int containerSlots = blockEntity.getItemHandler().getSlots();
            if (index < containerSlots) {
                if (!this.moveItemStackTo(original, containerSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(original, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (original.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return newStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity != null && player.distanceToSqr(
                blockEntity.getBlockPos().getX() + 0.5D,
                blockEntity.getBlockPos().getY() + 0.5D,
                blockEntity.getBlockPos().getZ() + 0.5D
        ) <= 64.0D;
    }
}
