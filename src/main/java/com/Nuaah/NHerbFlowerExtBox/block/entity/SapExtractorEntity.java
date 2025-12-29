package com.Nuaah.NHerbFlowerExtBox.block.entity;

import com.Nuaah.NHerbFlowerExtBox.regi.ConstituentsData;
import com.Nuaah.NHerbFlowerExtBox.regi.ConstituentsJsonLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("removal")
public class SapExtractorEntity extends BlockEntity {

    Map<String,Float> constituents = new HashMap<>();
    Map<String,Integer> durations = new HashMap<>();

    Block attackBlock ;
    ConstituentsData data;

    public SapExtractorEntity(BlockPos pos, BlockState state)  {
        super(NHerbFlowerExtBoxEntityTypes.SAP_EXTRACTOR.get(), pos,state);
    }

    public void setBaseBlockInfo(Block block){
        System.out.println("PLACE");
        String id = ForgeRegistries.BLOCKS.getKey(block).getPath();
        attackBlock = block;


        data = ConstituentsJsonLoader.CONSTITUENTS_DATA.get(id);

        for (ConstituentsData.ComponentData c : data.components) {
            String effect = c.id;
            String soluble = c.soluble;

            if (constituents.get(effect) != null){ //効果レベル
                float calc = constituents.get(effect) + c.amount;
                constituents.put(effect,calc);

            } else {
                constituents.put(effect,c.amount);
            }

            if (durations.get(effect) != null){ //効果時間
                int calc = durations.get(effect) + c.duration;
                durations.put(effect,calc);
            } else {
                durations.put(effect,c.duration);
            }
        }

        setChanged();
        if (this.level != null && !this.level.isClientSide) {
            // 周囲のクライアントに最新の状態を通知する
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public Map getConstituents(){
        return constituents;
    }

    public Map getDurations(){
        return durations;
    }

    public ConstituentsData getData(){
        if (attackBlock == null) return null;

        String id = ForgeRegistries.BLOCKS.getKey(attackBlock).getPath();
        ConstituentsData data = ConstituentsJsonLoader.CONSTITUENTS_DATA.get(id);
        return data;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);

        nbt.putString("AttachBlock",ForgeRegistries.BLOCKS.getKey(attackBlock).toString());

        //成分保存
        CompoundTag mapTag = new CompoundTag();
        for (Map.Entry<String, Float> entry : constituents.entrySet()) {
            mapTag.putFloat(entry.getKey(), entry.getValue());
        }

        nbt.put("Constituents", mapTag);

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
        System.out.println("LOADDD");
        String blockName = nbt.getString("AttachBlock");
        System.out.println(blockName);
        if (!blockName.isEmpty()) {
            ResourceLocation loc = new ResourceLocation(blockName);
            System.out.println(loc);
            Block storedBlock = ForgeRegistries.BLOCKS.getValue(loc);
            System.out.println(storedBlock);
            if (storedBlock != Blocks.AIR) {
                System.out.println("LOADDD");
                // 復元したBlockを使用（例: 内部データにセット）
                attackBlock = storedBlock;
            }
        }

        constituents.clear();
        CompoundTag mapTag = nbt.getCompound("Constituents");
        for (String key : mapTag.getAllKeys()) {
            constituents.put(key, mapTag.getFloat(key));
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
}
