package com.Nuaah.NHerbFlowerExtBox.gui.container;

import com.Nuaah.NHerbFlowerExtBox.block.entity.ClayCauldronEntity;
import com.Nuaah.NHerbFlowerExtBox.gui.slot.ClayCauldronSlot;
import com.Nuaah.NHerbFlowerExtBox.gui.slot.MillstoneSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class ClayCauldronMenu extends AbstractContainerMenu {
    private final ClayCauldronEntity blockEntity;

    public ClayCauldronMenu(int a, Inventory playerInventory, ClayCauldronEntity entity) {
        super(NHerbFlowerExtBoxContainerTypes.CLAY_CAULDRON.get(), a);
        checkContainerSize(playerInventory,6);

        IItemHandler handler = entity.getItemHandler();

        for (int i = 0; i < 6; i++) {
            this.addSlot(new ClayCauldronSlot(handler,i,62 + 18 * (i % 3),17 + (i / 3 * 18)));
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

    public int getWater() {
        return blockEntity.getWater();
    }

    public int getProgress() {
        return blockEntity.getProgress();
    }

    public boolean getHeating() {
        return blockEntity.getHeating();
    }

    public List<Float> getWaterColor(){
        return blockEntity.getWaterColor();
    }

    public Map getConstituents(){
        System.out.println(blockEntity.getConstituents());
        return blockEntity.getConstituents();
    }

    public Map getDurations(){
        return blockEntity.getDurations();
    }

    public String getLiquidType(){
        return blockEntity.getLiquidType();
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
