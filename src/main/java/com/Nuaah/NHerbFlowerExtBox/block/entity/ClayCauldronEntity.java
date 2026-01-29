package com.Nuaah.NHerbFlowerExtBox.block.entity;

import com.Nuaah.NHerbFlowerExtBox.gui.container.ClayCauldronMenu;
import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.Nuaah.NHerbFlowerExtBox.regi.ConstituentsData;
import com.Nuaah.NHerbFlowerExtBox.regi.ConstituentsJsonLoader;
import com.Nuaah.NHerbFlowerExtBox.regi.CustomPotionData;
import com.Nuaah.NHerbFlowerExtBox.regi.capability.NHerbFlowerExtBoxCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ClayCauldronEntity extends BlockEntity implements MenuProvider {

    final int MAX_WATER = 3;
    int waterAmount = 0;
    List<Float> waterColor = new ArrayList<>(Arrays.asList(0.2f, 0.4f, 1.0f, 1.0f));

    final int MAX_PROGRESS = 100;
    int progress = 0;  //調合メーター
    int progressCounter = 0; //メーター調節用
    int bubbleCounter = 0;
    int sapCounter = 0;

    boolean evaporate = false;
    boolean hasItem = false;

    String liquidType = "";

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
        if (waterAmount <= 0) { //全部なし
            System.out.println("RESET");
            waterAmount = 0;
            liquidType = "";

            constituents.clear();
            durations.clear();
            waterColor.clear();
        }

        setChanged();
        if (this.level != null && !this.level.isClientSide) {
            // 周囲のクライアントに最新の状態を通知する
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
            this.level.setBlockEntity(this); // ★ これが重要
        }
    }

    public void addWater(int amount,String liquid){
        if (waterAmount <= 0) {
            if (liquid.equals("water")) waterColor = new ArrayList<>(Arrays.asList(0.2f, 0.4f, 1.0f, 1.0f));
            if (liquid.equals("ethanol")) waterColor = new ArrayList<>(Arrays.asList(0.4f, 0.6f, 1.0f, 1.0f));
        } else {
            if (!liquidType.equals(liquid)) return;
        }

        if (liquidType.equals(liquid) || liquidType.isEmpty()){
            waterAmount += amount;
        }

        if (waterAmount >= MAX_WATER) waterAmount = MAX_WATER;

        if (waterAmount <= 0) {
            waterAmount = 0;
            liquidType = "";
        } else {
            liquidType = liquid;
        }

        if(amount > 0){ //薄める
            for (Map.Entry<String, Float> entry : constituents.entrySet()) {
                float calc = entry.getValue() * 0.6F;
                if (calc < 0.1) constituents.remove(entry.getKey()); //薄すぎて消える
                else constituents.put(entry.getKey(),calc);
            }
            for (Map.Entry<String, Integer> entry : durations.entrySet()) {
                float calc = entry.getValue() * 0.6F;
                durations.put(entry.getKey(),(int)calc);
            }

            List<Float> color = new ArrayList<>(Arrays.asList(0.2f, 0.4f, 1.0f, 1.0f));
            if (liquidType.equals("ethanol")) color = new ArrayList<>(Arrays.asList(0.4f, 0.6f, 1.0f, 1.0f));

            for (int i = 0; i < waterColor.size(); i++) {//水の色
                float calc = (waterColor.get(i) + color.get(i)) / 2.0F;
                if (calc > 1.0F)calc = 1.0f;
                if (calc < 0) calc = 0;
                waterColor.set(i,calc);
            }
        }

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

    public String getLiquidType(){
        return liquidType;
    }

    public void setHeating(boolean heat){
        heating = heat;
        setChanged();
        if (this.level != null && !this.level.isClientSide) {
            // 周囲のクライアントに最新の状態を通知する
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public boolean getEvaporate(){
        return evaporate;
    }

    //蒸発の切り替え
    public void switchEvaporate(){
        System.out.println("SWITCH");
        progress = 0;
        progressCounter = 0;
        evaporate = !evaporate;
        System.out.println(evaporate);
        setChanged();
        if (this.level != null && !this.level.isClientSide) {
            // 周囲のクライアントに最新の状態を通知する
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void tick() {
        if((heating && hasItem && waterAmount > 0 && !evaporate) ||  //抽出処理
                (heating && waterAmount > 0 && evaporate)) {         //蒸発
            progressCounter += 1;
            if (progressCounter >= 2) {  //調節
                progressCounter = 0;
                if (progress >= MAX_PROGRESS) {  //抽出
                    progress = 0;
                    consumePowder();
                    if (evaporate) evaporatePotion();  //蒸発
                } else {
                    progress += 1;
                }
            }
        } else {
            progress = 0;
        }

        if (waterAmount > 0){  //樹液
            BlockState blockState = level.getBlockState(getBlockPos().above());
            BlockEntity entity = level.getBlockEntity(getBlockPos().above());

            if (blockState.hasProperty(BlockStateProperties.LIT)){
                boolean sap = blockState.getValue(BlockStateProperties.LIT);

                if (sap && (entity instanceof SapExtractorEntity be)){
                    sapCounter += 1;
                    if (sapCounter >= 50){
                        mixingHerb(be.getData());
                        sapCounter = 0;
                    }
                }
            }

            //泡
            if (getLevel() != null){
                bubbleCounter += 1;
                if (evaporate){  //蒸発
                    if (bubbleCounter >= 1) {
                        BlockPos pos = getBlockPos();
                        double randomX = (level.random.nextDouble() - 0.5) * 0.4;  // -0.2 ～ 0.2
                        double randomZ = (level.random.nextDouble() - 0.5) * 0.4;
                        getLevel().addParticle(ParticleTypes.SPLASH
                                , pos.getX() + 0.5 + randomX, pos.getY() + 0.1 + waterAmount * 0.3, pos.getZ() + 0.5 + randomZ,
                                0, 0, 0);

                        bubbleCounter = 0;
                    }
                } else {
                    if (bubbleCounter >= 20) {
                        BlockPos pos = getBlockPos();
                        double randomX = (level.random.nextDouble() - 0.5) * 0.4;  // -0.2 ～ 0.2
                        double randomZ = (level.random.nextDouble() - 0.5) * 0.4;
                        getLevel().addParticle(ParticleTypes.SPLASH
                                , pos.getX() + 0.5 + randomX, pos.getY() + 0.1 + waterAmount * 0.3, pos.getZ() + 0.5 + randomZ,
                                0, 0, 0);

                        bubbleCounter = 0;
                    }
                }
            }

            //アイテムへ付与
            BlockPos pos = getBlockPos();
            AABB bowl = new AABB(
                    pos.getX() + 0.25, pos.getY() + 0.4, pos.getZ() + 0.25,
                    pos.getX() + 0.75, pos.getY() + 0.6, pos.getZ() + 0.75
            );

            List<ItemEntity> items =
                    level.getEntitiesOfClass(ItemEntity.class, bowl);

            if (!items.isEmpty()){  //パーティクル
                combineParticle();
            }


            if (level.isClientSide) return;

            for (ItemEntity item : items) {
                ItemStack stack = item.getItem();

                if (!stack.is(ItemTags.SWORDS)) return;

                CompoundTag tag = stack.getOrCreateTag();
                if (!tag.getBoolean("HasPotionCap")) {
                    tag.putBoolean("HasPotionCap", true);

                    tag.putLong("SyncTick", level.getGameTime()); // ItemStack側のNBTを更新

                    item.getPersistentData().putBoolean("ForceSync", true);

                    item.setItem(stack);
                }

                if (!tag.getBoolean("IntoCauldron")) {  //ファーストぽちゃん
                    stack.getCapability(NHerbFlowerExtBoxCapabilities.POTION_CAP).ifPresent(cap -> {
                        cap.clearPotion();
                        getLevel().playSound(null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), SoundEvents.BREWING_STAND_BREW, SoundSource.NEUTRAL, 1.0F, 1.0F);
                        item.setPickUpDelay(100);
                    });
                    tag.putBoolean("IntoCauldron", true);
                } else {  //出てくる
                    if(!item.hasPickUpDelay()){
                        System.out.println("POP");
                        combinePotion(stack,item);

                        tag.putBoolean("IntoCauldron", false);
                        addWater(-1);
                        if (stack.isDamageableItem()) {  //劣化
                            int itemDagame = (int)(stack.getDamageValue() * 0.1);
                            stack.hurt(Math.max(50,itemDagame), level.random, null);  //最低50
                            if (stack.getDamageValue() >= stack.getMaxDamage()) {
                                item.discard();
                            }
                        }
                    }
                }
            }
        }

        setChanged();
        if (this.level != null && !this.level.isClientSide) {
            // 周囲のクライアントに最新の状態を通知する
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    private void combinePotion(ItemStack stack,ItemEntity item){

        stack.getCapability(NHerbFlowerExtBoxCapabilities.POTION_CAP).ifPresent(cap -> {
            System.out.println("CAP");
            BlockPos pos = getBlockPos();
            double speedX = (level.random.nextDouble() - 0.5) * 0.3;
            double speedY = 0.5;  // 上昇力
            double speedZ = (level.random.nextDouble() - 0.5) * 0.3;

            item.setDeltaMovement(speedX, speedY, speedZ);

            //パーティクル
            ServerLevel serverLevel = (ServerLevel) getLevel();
            serverLevel.sendParticles(ParticleTypes.EXPLOSION
                    , pos.getX(), pos.getY() + 1.0,pos.getZ()
                    ,1,0,0,0,1);

            //効果音
            getLevel().playSound(null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 1.0F, 1.0F);

            for (Map.Entry<String, Float> entry : constituents.entrySet()) {
                cap.setConstituents(entry.getKey(),entry.getValue());
//                for (ServerPlayer player : serverLevel.getServer().getPlayerList().getPlayers()) {
//                    player.sendSystemMessage(Component.literal(entry.getKey()));
//                }
            }

            for (Map.Entry<String, Integer> entry : durations.entrySet()) {
                cap.setDurations(entry.getKey(),entry.getValue());
//                for (ServerPlayer player : serverLevel.getServer().getPlayerList().getPlayers()) {
//                    player.sendSystemMessage(Component.literal(entry.getKey()));
//                }
            }

            System.out.println("POPOPOPOP");

            CompoundTag tag = stack.getOrCreateTag();

//            for (ServerPlayer player : serverLevel.getServer().getPlayerList().getPlayers()) {
//                player.sendSystemMessage(Component.literal("§a[Server] §fCHECK: POP処理実行中"));
//            }


            if (constituents.isEmpty()) {
//                for (ServerPlayer player : serverLevel.getServer().getPlayerList().getPlayers()) {
//                    player.sendSystemMessage(Component.literal("EMPTY"));
//                }

                serverLevel.sendParticles(ParticleTypes.EXPLOSION
                        , pos.getX(), pos.getY() + 1.0,pos.getZ()
                        ,10,0,0,0,1);

                tag.putBoolean("HasPotionCap", false);

                tag.remove("Potions");
                tag.remove("Durations");
            }

            CompoundTag capNbt = cap.serializeNBT();

            //ItemStack本体のNBTに書き込む
            stack.getOrCreateTag().put("PotionData", capNbt);

            item.setItem(stack.copy());
        });

        setChanged();
        if (this.level != null && !this.level.isClientSide) {
            // 周囲のクライアントに最新の状態を通知する
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    private void combineParticle(){
        if (getLevel() != null){
            BlockPos pos = getBlockPos();
            double randomX = (level.random.nextDouble() - 0.5) * 0.4;  // -0.2 ～ 0.2
            double randomZ = (level.random.nextDouble() - 0.5) * 0.4;
            getLevel().addParticle(ParticleTypes.ENTITY_EFFECT
                    , pos.getX() + 0.5 + randomX, pos.getY() + 0.1 + waterAmount * 0.3,pos.getZ() + 0.5 + randomZ
                    ,waterColor.get(0),waterColor.get(1),waterColor.get(2));

            getLevel().addParticle(ParticleTypes.END_ROD
                    , pos.getX() + 0.5 + randomX, pos.getY() + 0.1 + waterAmount * 0.3,pos.getZ() + 0.5 + randomZ
                    ,0,10,0);
        }
    }

    private void mixingHerb(ConstituentsData data){
        System.out.println("MIXING");
        System.out.println(data);
        if (data != null) {
            System.out.println("NOT NULL");
            for (ConstituentsData.ComponentData c : data.components) {
                String effect = c.id;
                String soluble = c.soluble;
                System.out.println(effect);

                if ((soluble.equals("water"))){
                    if(!liquidType.equals("water")) continue;
                } else if ((soluble.equals("fat"))){
                    if (!liquidType.equals("ethanol")) continue;
                }

                System.out.println("SYstem mix");

                if (constituents.get(effect) != null){ //効果レベル
                    float calc = constituents.get(effect) + c.amount;
                    constituents.put(effect,Math.min(3,calc));
                } else {
                    constituents.put(effect,c.amount);
                }

                if (durations.get(effect) != null){ //効果時間
                    int calc = durations.get(effect) + c.duration;
                    durations.put(effect,Math.min(300,calc));
                } else {
                    durations.put(effect,c.duration);
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

    //消費
    private void consumePowder(){
        for (int i = 0; i < itemHandler.getSlots(); i++) {

            ItemStack stack = itemHandler.getStackInSlot(i);

            if(!stack.isEmpty()){
                String id = stack.getItem().toString();
                ConstituentsData data = ConstituentsJsonLoader.CONSTITUENTS_DATA.get(id);
                mixingHerb(data); //成分足す
                itemHandler.extractItem(i,1,false);
                if (itemHandler.getStackInSlot(i).getCount() <= 0){ //消えた
                    itemHandler.setStackInSlot(i,ItemStack.EMPTY);
                }
            }
        }
    }

    private void evaporatePotion(){
        for (Map.Entry<String, Float> entry : constituents.entrySet()) {
            float postion = constituents.get(entry.getKey());
            int duration = durations.get(entry.getKey());
            postion *= 1.1F;
            duration *= 2;
            constituents.put(entry.getKey(),postion);
            durations.put(entry.getKey(),duration);
        }
        addWater(-1);

        setChanged();
        if (this.level != null && !this.level.isClientSide) {
            // 周囲のクライアントに最新の状態を通知する
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
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
        nbt.putBoolean("Evaporate",evaporate);
        nbt.put("Items", itemHandler.serializeNBT());
        nbt.putString("Liquid",liquidType);

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
            mapTag3.putInt(entry.getKey(), entry.getValue());
        }

        nbt.put("Durations", mapTag3);

    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);

        waterAmount = nbt.getInt("WaterAmount");
        progress = nbt.getInt("Progress");
        heating = nbt.getBoolean("Heating");
        evaporate = nbt.getBoolean("Evaporate");
        itemHandler.deserializeNBT(nbt.getCompound("Items"));
        liquidType = nbt.getString("Liquid");

        constituents.clear();
        CompoundTag mapTag = nbt.getCompound("Constituents");
        for (String key : mapTag.getAllKeys()) {
            constituents.put(key, mapTag.getFloat(key));
        }

        CompoundTag mapTag2 = nbt.getCompound("WaterColors");
        for (int i = 0; i < waterColor.size(); i++) {
            waterColor.set(i,mapTag2.getFloat("WaterColor" + i));
        }

        durations.clear();
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
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int a, Inventory inventory, Player player) {
        return new ClayCauldronMenu(a,inventory,this);
    }
}
