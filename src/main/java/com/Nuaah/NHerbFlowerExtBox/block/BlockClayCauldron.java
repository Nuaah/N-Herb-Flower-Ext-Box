package com.Nuaah.NHerbFlowerExtBox.block;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxItems;
import com.Nuaah.NHerbFlowerExtBox.block.entity.ClayCauldronEntity;
import com.Nuaah.NHerbFlowerExtBox.block.entity.NHerbFlowerExtBoxEntityTypes;
import com.Nuaah.NHerbFlowerExtBox.regi.ConstituentsData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class BlockClayCauldron extends BaseEntityBlock {
    private static final int SIDE_THICKNESS = 2;
    private static final int LEG_WIDTH = 4;
    private static final int LEG_HEIGHT = 3;
    private static final int LEG_DEPTH = 2;
    protected static final int FLOOR_LEVEL = 4;
    private static final VoxelShape INSIDE = box(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE = Shapes.join(Shapes.block(), Shapes.or(box(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), box(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), INSIDE), BooleanOp.ONLY_FIRST);

    public BlockClayCauldron() {
        super(Properties.of()
            .strength(2.0F,2.0F)
            .requiresCorrectToolForDrops()
        );
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide()) {

            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(!player.isShiftKeyDown()){
                if (blockEntity instanceof ClayCauldronEntity be) {
                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 0.5;
                    double z = pos.getZ() + 0.5;
                    ItemStack heldItem = player.getItemInHand(hand);
                    if (heldItem.getItem() == Items.WATER_BUCKET) {  //水いれる
                        be.addWater(1,"water");
                        if (!player.getAbilities().instabuild) {
                            // 手に持っている水バケツを空のバケツに直接置き換える
                            player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                        }
                        System.out.println("SOUND");

                        world.playSound(null, x, y, z, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);
                    } else if (heldItem.getItem() == Items.BUCKET) { //水もらう
                        if (be.getWater() > 0){
                            be.addWater(-1);
                            if (!player.getAbilities().instabuild) {
                                // 手に持っている水バケツを空のバケツに直接置き換える
                                if (be.getLiquidType().equals("water")) player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
                            }
                            world.playSound(null, x, y, z, SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);
                        }

                    } else if (heldItem.getItem() == NHerbFlowerExtBoxItems.ETHANOL_POTION.get()) {
                        be.addWater(1,"ethanol");
                        if (!player.getAbilities().instabuild) {
                            player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                        }
                    } else if (heldItem.getItem() == Items.GLASS_BOTTLE) {
                        if (be.getWater() > 0){
                            ItemStack result = createCustomPotion(be);
                            if (!result.isEmpty()) { //ポーション
                                be.addWater(-1);
                                // プレイヤーに空瓶を与える
                                if (!player.getInventory().add(result)) {
                                    Block.popResource(world, pos, result); // 満杯時はドロップ
                                }

                                heldItem.shrink(1);  // スタックサイズを1減らす
                                if (heldItem.isEmpty()) {
                                    player.setItemInHand(hand, ItemStack.EMPTY);  // 0になったらスロットクリア
                                }

                                world.playSound(null, x, y, z, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);
                            } else { //成分なし
                                if (be.getLiquidType().equals("water")) {
                                    ItemStack stack = new ItemStack(Items.POTION);
                                    PotionUtils.setPotion(stack, Potions.WATER);
                                    if (!player.getInventory().add(stack)) {
                                        Block.popResource(world, pos, stack); // 満杯時はドロップ
                                    }
                                    heldItem.shrink(1);  // スタックサイズを1減らす
                                    if (heldItem.isEmpty()) {
                                        player.setItemInHand(hand, ItemStack.EMPTY);  // 0になったらスロットクリア
                                    }
                                    be.addWater(-1);


                                    world.playSound(null, x, y, z, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);
                                } else if (be.getLiquidType().equals("ethanol")) {
                                    ItemStack ethanol = new ItemStack(NHerbFlowerExtBoxItems.ETHANOL_POTION.get());
                                    if (!player.getInventory().add(ethanol)) {
                                        Block.popResource(world, pos, ethanol); // 満杯時はドロップ
                                    }
                                    heldItem.shrink(1);  // スタックサイズを1減らす
                                    if (heldItem.isEmpty()) {
                                        player.setItemInHand(hand, ItemStack.EMPTY);  // 0になったらスロットクリア
                                    }
                                    be.addWater(-1);

                                    world.playSound(null, x, y, z, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);
                                }
                            }
                        }
                    } else {
                        NetworkHooks.openScreen((ServerPlayer) player, be, pos);
                    }
                }
            } else {

            }
        }
        return InteractionResult.sidedSuccess(world.isClientSide());
    }

    private ItemStack createCustomPotion(ClayCauldronEntity entity) {
        ItemStack potion = new ItemStack(NHerbFlowerExtBoxItems.CUSTOM_POTION.get());

        CompoundTag tag = potion.getOrCreateTag();
        ListTag effectList = new ListTag();

        Map<String,Float> constituents = entity.getConstituents();
        Map<String,Integer> durations = entity.getDurations();

        System.out.println(constituents);
        CompoundTag effectTag = new CompoundTag();

        if (constituents.isEmpty()) return ItemStack.EMPTY; //効果なし

        for (Map.Entry<String, Float> entry : constituents.entrySet()) {
            System.out.println(entry.getKey());
            effectTag.putString("Name", entry.getKey());        // "healing" "fire_resistance"など
            effectTag.putFloat("Level", Math.min(entry.getValue(),3));         // レベル　最大3
            effectTag.putInt("Duration", durations.get(entry.getKey()));         // 効果時間
            effectList.add(effectTag.copy());
        }

        System.out.println(effectTag);

        CompoundTag mapTag = new CompoundTag();
        //水の色
        for (int i = 0; i < entity.getWaterColor().size(); i++) {
            mapTag.putFloat("WaterColor" + i,entity.getWaterColor().get(i));
        }

        tag.put("CustomEffects", effectList);
        tag.put("WaterColors", mapTag);
        potion.setTag(tag);

        return potion;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public VoxelShape getInteractionShape(BlockState p_151955_, BlockGetter p_151956_, BlockPos p_151957_) {
        return INSIDE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void neighborChanged(BlockState p_60509_, Level level, BlockPos pos, Block p_60512_, BlockPos p_60513_, boolean p_60514_) {
        super.neighborChanged(p_60509_, level, pos, p_60512_, p_60513_, p_60514_);

        BlockPos above = pos.above();
        BlockState stateAbove = level.getBlockState(above);
        boolean isSap = stateAbove.is(NHerbFlowerExtBoxBlocks.Blocks.SAP_EXTRACTOR.get());

        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ClayCauldronEntity be) {
                if (isSap) {
                    if (stateAbove.hasProperty(BlockStateProperties.LIT)){
                        boolean sap = stateAbove.getValue(BlockStateProperties.LIT);
                        if (sap){
                        }
                    }

//                    be.setHeating(true);
                } else {
//                    be.setHeating(false);
                }
            }
        }

        //下に火
        BlockPos below = pos.below();
        BlockState stateBelow = level.getBlockState(below);

        boolean isFire =
            stateBelow.is(Blocks.FIRE) ||
                stateBelow.is(Blocks.SOUL_FIRE) ||
                stateBelow.is(Blocks.CAMPFIRE) ||
                stateBelow.is(Blocks.SOUL_CAMPFIRE) ||
                stateBelow.is(Blocks.CANDLE_CAKE) ||
                stateBelow.is(Blocks.CANDLE);

        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ClayCauldronEntity be) { //熱源
                if (isFire) {
                    be.setHeating(true);
                } else {
                    be.setHeating(false);
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        //ドロップ処理
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof ClayCauldronEntity entity) {
                if (!world.isClientSide) {
                    ItemStackHandler handler = entity.getItemHandler(); // あらかじめ getter を用意しておく
                    for (int i = 0; i < handler.getSlots(); i++) {
                        ItemStack stack = handler.getStackInSlot(i);
                        if (!stack.isEmpty()) {
                            Containers.dropItemStack(world, pos.getX() + 0.5,pos.getY() + 0.5, pos.getZ() + 0.5, stack);
                        }
                    }
                }
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ClayCauldronEntity(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, NHerbFlowerExtBoxEntityTypes.CLAY_CAULDRON.get(), (lvl, pos, st, be) -> be.tick());
    }
}
