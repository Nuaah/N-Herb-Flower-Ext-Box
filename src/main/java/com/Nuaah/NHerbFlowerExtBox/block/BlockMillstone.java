package com.Nuaah.NHerbFlowerExtBox.block;

import com.Nuaah.NHerbFlowerExtBox.block.entity.MillstoneEntity;
import com.Nuaah.NHerbFlowerExtBox.block.entity.NHerbFlowerExtBoxEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Random;

public class BlockMillstone extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final VoxelShape SHAPE = Block.box(2,0,2,14,10,14);

    public BlockMillstone() {
        super(Properties.of()
            .strength(3.5F,6.0F)
            .requiresCorrectToolForDrops()
        );
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(player.isShiftKeyDown()){
                if (blockEntity instanceof MillstoneEntity be) {
                    be.rotationMillstone(be);
                }
            } else {
                if (blockEntity instanceof MillstoneEntity be) {
                    NetworkHooks.openScreen((ServerPlayer) player, be, pos);
                }
            }
        } else {
            if(player.isShiftKeyDown()){
                for (int i = 0; i < 10; i++) {
                    grindingParticle(pos, level);
                }
            }
        }
        return InteractionResult.SUCCESS;
//        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private void grindingParticle(BlockPos pos,Level level){
        Random random = new Random();
        double x = pos.getX() + random.nextDouble();
        double y = pos.getY() + 0.4;
        double z = pos.getZ() + random.nextDouble();

        // 水平方向のランダム速度
        double dx = (random.nextDouble() - 0.5) * 60.6; // -0.3 ~ 0.3
        double dy = 0.0; // 少しだけ上に浮く
        double dz = (random.nextDouble() - 0.5) * 60.6; // -0.3 ~ 0.3

        DustParticleOptions redstoneParticle = new DustParticleOptions(new Vector3f(0.6F, 0.6F, 0.6F), 1.0f);
        level.addParticle(redstoneParticle, x, y, z, 0, 0, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    // ブロックの正面配置
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        //ドロップ処理
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof MillstoneEntity entity) {
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
        return new MillstoneEntity(pos,state);
    }
}
