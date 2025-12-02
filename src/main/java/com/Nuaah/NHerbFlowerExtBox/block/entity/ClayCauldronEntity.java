package com.Nuaah.NHerbFlowerExtBox.block.entity;

import com.Nuaah.NHerbFlowerExtBox.gui.container.ClayCauldronMenu;
import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.Nuaah.NHerbFlowerExtBox.regi.ConstituentsData;
import com.Nuaah.NHerbFlowerExtBox.regi.ConstituentsJsonLoader;
import com.Nuaah.NHerbFlowerExtBox.regi.CustomPotionData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ClayCauldronEntity extends BlockEntity implements MenuProvider {

    final int MAX_WATER = 3;
    int waterAmount = 0;
    List<Float> waterColor = new ArrayList<>(Arrays.asList(0.2f, 0.4f, 1.0f, 1.0f));

    final int MAX_PROGRESS = 100;
    int progress = 0;
    int progressCounter = 0;

    boolean hasItem = false;

    Map<String,Float> constituents = new HashMap<>();
    Map<String,Integer> durations = new HashMap<>();

    private final ItemStackHandler itemHandler = new ItemStackHandler(6){
        @Override
        protected void onContentsChanged(int slotIndex) {
            super.onContentsChanged(slotIndex);

            for (int i = 0; i < itemHandler.getSlots(); i++) { //入ってるか判定
                if(!itemHandler.getStackInSlot(i).isEmpty()){
                    hasItem = true;
                    break;
                }
                hasItem = false;
            }
        }
    };
    private boolean heating = false;

    public ClayCauldronEntity(BlockPos pos, BlockState state) {
        super(NHerbFlowerExtBoxEntityTypes.CLAY_CAULDRON.get(), pos,state);
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public void addWater(int amount){
        waterAmount += amount;
        if (waterAmount >= MAX_WATER) waterAmount = MAX_WATER;
        if (waterAmount < 0) waterAmount = 0;

        setChanged();
        if (this.level != null && !this.level.isClientSide) {
            // 周囲のクライアントに最新の状態を通知する
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public int getWater(){
        return waterAmount;
    }

    public int getMaxWater(){
        return MAX_WATER;
    }

    public int getProgress(){
        return progress;
    }

    public boolean getHeating(){
        return heating;
    }

    public List<Float> getWaterColor(){
        return waterColor;
    }

    public Map getConstituents(){
        return constituents;
    }

    public Map getDurations(){
        return durations;
    }

    public void setHeating(boolean heat){
        heating = heat;
        setChanged();
        if (this.level != null && !this.level.isClientSide) {
            // 周囲のクライアントに最新の状態を通知する
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void tick() {
        if(heating && hasItem && waterAmount > 0){
            progressCounter += 1;
            if (progressCounter >= 2){
                progressCounter = 0;
                if(progress >= MAX_PROGRESS){
                    progress = 0;
                    consumePowder();
                } else {
                    progress += 1;
                }
            }
        } else {
            progress = 0;
        }

//        System.out.println("NAIYOU");
//        System.out.println(constituents);
    }

    private void mixingHerb(ItemStack stack){
        String id = stack.getItem().toString(); // 例: bellflower
        ConstituentsData data = ConstituentsJsonLoader.CONSTITUENTS_DATA.get(id);

        if (data != null) {
            for (ConstituentsData.ComponentData c : data.components) {
                String effect = c.id;
                String soluble = c.soluble;
                if (!soluble.equals("water")) break;

                if (constituents.get(effect) != null){ //効果レベル
                    float calc = constituents.get(effect) + c.amount;
                    constituents.put(effect,calc);
                    System.out.println("EFFECT");
                    System.out.println(effect);
                } else {
                    constituents.put(effect,c.amount);
                }

                if (durations.get(effect) != null){ //効果時間
                    int calc = durations.get(effect) + c.duration;
                    durations.put(effect,calc);
                    System.out.println(effect);
                    System.out.println(calc);
                } else {
                    durations.put(effect,c.duration);
                    System.out.println(effect);
                    System.out.println(c.duration);
                }

                for (int i = 0; i < waterColor.size(); i++) {//水の色
                    float calc = waterColor.get(i) + data.color.get(i);
                    if (calc > 1.0F)calc = 1.0f;
                    waterColor.set(i,calc);
                }
            }

            setChanged();
            if (this.level != null && !this.level.isClientSide) {
                // 周囲のクライアントに最新の状態を通知する
                this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    }

    private void addEffect(ItemStack stack, CustomPotionData data){
        CompoundTag nbt = stack.getOrCreateTag();
        ListTag list = nbt.getList("CustomEffects", Tag.TAG_COMPOUND);

        list.add(data.toTag());
        nbt.put("CustomEffects", list);
    }

    //消費
    private void consumePowder(){
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if(!itemHandler.getStackInSlot(i).isEmpty()){
                mixingHerb(itemHandler.getStackInSlot(i)); //成分足す
                itemHandler.extractItem(i,1,false);
                if (itemHandler.getStackInSlot(i).getCount() <= 0){ //消えた
                    itemHandler.setStackInSlot(i,ItemStack.EMPTY);
                }
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container." + NHerbFlowerExtBox.MOD_ID + ".clay_cauldron");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("WaterAmount",waterAmount);
        nbt.putInt("Progress",progress);
        nbt.putBoolean("Heating",heating);
        nbt.put("Items", itemHandler.serializeNBT());

        //成分保存
        CompoundTag mapTag = new CompoundTag();
        for (Map.Entry<String, Float> entry : constituents.entrySet()) {
            mapTag.putFloat(entry.getKey(), entry.getValue());
        }

        nbt.put("Constituents", mapTag);

        CompoundTag mapTag2 = new CompoundTag();
        //水の色
        for (int i = 0; i < waterColor.size(); i++) {
            mapTag2.putFloat("WaterColor" + i,waterColor.get(i));
        }

        nbt.put("WaterColors", mapTag2);

        CompoundTag mapTag3 = new CompoundTag();
        //効果時間
        for (Map.Entry<String, Integer> entry : durations.entrySet()) {
            mapTag3.putFloat(entry.getKey(), entry.getValue());
        }

        nbt.put("Durations", mapTag3);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        System.out.println("LOAD");
        waterAmount = nbt.getInt("WaterAmount");
        progress = nbt.getInt("Progress");
        heating = nbt.getBoolean("Heating");
        itemHandler.deserializeNBT(nbt.getCompound("Items"));

        CompoundTag mapTag = nbt.getCompound("Constituents");
        for (String key : mapTag.getAllKeys()) {
            constituents.put(key, mapTag.getFloat(key));
        }

        CompoundTag mapTag2 = nbt.getCompound("WaterColors");
        for (int i = 0; i < waterColor.size(); i++) {
            waterColor.set(i,mapTag2.getFloat("WaterColor" + i));
        }

        CompoundTag mapTag3 = nbt.getCompound("Durations");
        for (String key : mapTag3.getAllKeys()) {
            durations.put(key, mapTag3.getInt(key));
        }
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

    @Override
    public @Nullable AbstractContainerMenu createMenu(int a, Inventory inventory, Player player) {
        return new ClayCauldronMenu(a,inventory,this);
    }
}
