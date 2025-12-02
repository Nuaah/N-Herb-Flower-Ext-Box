package com.Nuaah.NHerbFlowerExtBox.block.entity;

import com.Nuaah.NHerbFlowerExtBox.gui.container.MillstoneMenu;
import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.Nuaah.NHerbFlowerExtBox.regi.MillstoneRecipe;
import com.Nuaah.NHerbFlowerExtBox.regi.NHerbFlowerExtBoxRecipeSerializers;
import com.Nuaah.NHerbFlowerExtBox.regi.NHerbFlowerExtBoxRecipeType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MillstoneEntity extends BlockEntity implements MenuProvider {

    public float rotation = 0f;
    private int[] progress = {0,0,0};

    private final ItemStackHandler itemHandler = new ItemStackHandler(6){
        @Override
        protected void onContentsChanged(int slotIndex) {
            super.onContentsChanged(slotIndex);

            if(slotIndex < 3) progress[slotIndex] = 0;
        }
    };

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public MillstoneEntity(BlockPos pos, BlockState state) {
        super(NHerbFlowerExtBoxEntityTypes.MILLSTONE.get(), pos,state);
    }

    public void rotationMillstone(MillstoneEntity entity) {
        SimpleContainer container = new SimpleContainer(1);
        rotation += 10.0F;

        for (int i = 0; i < 3; i++)
        {
            ItemStack input = itemHandler.getStackInSlot(i);
            ItemStack output = itemHandler.getStackInSlot(3 + i);
            container.setItem(0, itemHandler.getStackInSlot(i));
            progress[i] += 10;

            Optional<MillstoneRecipe> opt = level.getRecipeManager().getRecipeFor(NHerbFlowerExtBoxRecipeType.MILLSTONE_GRINDING_TYPE.get(), container, level);

            if (opt.isPresent()) { //レシピあり
                MillstoneRecipe recipe = opt.get();
                ItemStack result = recipe.getResult().copy();
                int recipeProgress = recipe.getProcessingTime();

                if (progress[i] >= recipeProgress){ //完成
                    if (output.isEmpty()) {
                        output = result;
                    } else if (ItemStack.isSameItemSameTags(output, result)) {
                        output.grow(result.getCount());
                    }
                    itemHandler.setStackInSlot(3 + i,output);
                    input.setCount(input.getCount()-1);
                    if(input.getCount() <= 0){
                        itemHandler.setStackInSlot(i,ItemStack.EMPTY);
                    }
                }
            }
        }
        if (rotation >= 360f) { //１周
            rotation = 0f;
        }

        this.setChanged();
        if (this.level != null && !this.level.isClientSide) {
            // 周囲のクライアントに最新の状態を通知する
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public float rotate(){
        return rotation;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putFloat("Rotation", rotation);
        nbt.put("Items", itemHandler.serializeNBT());
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        rotation = nbt.getFloat("Rotation");
        itemHandler.deserializeNBT(nbt.getCompound("Items"));
    }

    // クライアント同期用パケット
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    // サーバー側：sendBlockUpdatedが呼ばれたとき、クライアントに送るパケットを作成する
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // クライアント側：パケットを受け取ったとき、NBTデータを読み込んで自分の変数を更新する
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        // パケット内のNBTタグ（saveAdditionalで保存されたもの）を取得し、loadで読み込む
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            this.load(tag);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container." + NHerbFlowerExtBox.MOD_ID + ".millstone");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int a, Inventory inventory, Player player) {
        return new MillstoneMenu(a,inventory,this);
    }
}
