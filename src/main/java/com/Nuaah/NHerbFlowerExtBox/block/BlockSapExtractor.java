package com.Nuaah.NHerbFlowerExtBox.block;

import com.Nuaah.NHerbFlowerExtBox.block.entity.SapExtractorEntity;
import com.Nuaah.NHerbFlowerExtBox.regi.tag.NHerbFlowerExtBoxTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BlockSapExtractor extends BaseEntityBlock {

    public static final VoxelShape SHAPE = Block.box(6,2,6,16,8,10);
    public static final VoxelShape SHAPE_N = Block.box(6,2,10,10,8,16);
    public static final VoxelShape SHAPE_S = Block.box(6,2,0,10,8,6);
    public static final VoxelShape SHAPE_E = Block.box(0,2,6,6,8,10);
    public static final VoxelShape SHAPE_W = Block.box(10,2,6,16,8,10);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty SAP =  BlockStateProperties.LIT;

    public BlockSapExtractor() {
        super(Properties.of()
                .instabreak()
                .noOcclusion());
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
        registerDefaultState(defaultBlockState().setValue(SAP, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING,SAP);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)){
            case NORTH -> {
                return SHAPE_N;
            }
            case SOUTH -> {
                return SHAPE_S;
            }
            case EAST -> {
                return SHAPE_E;
            }
            case WEST -> {
                return SHAPE_W;
            }
        }
        return SHAPE;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)){
            case NORTH -> {
                return SHAPE_N;
            }
            case SOUTH -> {
                return SHAPE_S;
            }
            case EAST -> {
                return SHAPE_E;
            }
            case WEST -> {
                return SHAPE_W;
            }
        }
        return SHAPE;
    }

    // ブロックの正面配置
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {

        Direction clickedFace = context.getClickedFace();

        if (clickedFace.getAxis().isHorizontal()) {
            Level level = context.getLevel();

            BlockPos targetPos = context.getClickedPos().relative(context.getClickedFace().getOpposite());

            // これで「クリックした土台」のBlockStateが取れます
            BlockState clickedBlockState = level.getBlockState(targetPos);

            System.out.println("LOG");
            System.out.println(clickedBlockState.is(NHerbFlowerExtBoxTags.Blocks.SAP_LOGS));
            this.defaultBlockState().setValue(SAP,clickedBlockState.is(NHerbFlowerExtBoxTags.Blocks.SAP_LOGS));  //木の判定

            // クリックされた面の向きを取得


            // 水平方向（東西南北）の面がクリックされた場合、その向きに設定
            if (clickedFace.getAxis().isHorizontal()) {
                return this.defaultBlockState().setValue(FACING, clickedFace).setValue(SAP,clickedBlockState.is(NHerbFlowerExtBoxTags.Blocks.SAP_LOGS));
            }

            // 上面や下面がクリックされた場合は、プレイヤーの向きを基準にする（予備の挙動）
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(SAP,clickedBlockState.is(NHerbFlowerExtBoxTags.Blocks.SAP_LOGS));
        }

        return null;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        // 自分の背面のブロックが変化した時、canSurviveがfalseなら自分を破壊する
        if (direction == state.getValue(FACING).getOpposite() && !state.canSurvive(level, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        // 自分の向きの反対側（くっついているはずの場所）の座標
        BlockPos attachedPos = pos.relative(facing.getOpposite());
        BlockState attachedState = level.getBlockState(attachedPos);

        // 土台の面が「しっかりした面（Sturdy）」であるかどうか
        // これにより、草や空気などにはくっつけなくなります
        return attachedState.isFaceSturdy(level, attachedPos, facing);
    }

    @Override
    public void neighborChanged(BlockState p_60509_, Level level, BlockPos pos, Block p_60512_, BlockPos p_60513_, boolean p_60514_) {
        super.neighborChanged(p_60509_, level, pos, p_60512_, p_60513_, p_60514_);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moved) {
        super.onPlace(state, level, pos, oldState, moved);

        // ブロック設置時に tick を予約
        level.scheduleTick(pos, this, 20); // 20 = 1秒ごと
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {

        if (state.getValue(SAP)){
            world.sendParticles(ParticleTypes.DRIPPING_WATER
                    , pos.getX() + 0.5, pos.getY() ,pos.getZ() + 0.5
                    ,1,0,0,0,0);

            world.scheduleTick(pos, this, 20); // 20 = 1秒ごと
        }
    }


    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(world, pos, state, placer, stack);

        if (!world.isClientSide) {
            // 1. 自分の向き（FACING）を取得
            Direction myFacing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

            // 2. 向きの反対側（くっついている土台）の座標を計算
            BlockPos attachedPos = pos.relative(myFacing.getOpposite());

            // 3. 土台の BlockState を取得
            BlockState clickedBlockState = world.getBlockState(attachedPos);

            // 2. 自分の BlockEntity を取得
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof SapExtractorEntity entity) {
                // 3. BlockEntityに用意した専用メソッドにブロック情報を送る
                entity.setBaseBlockInfo(clickedBlockState.getBlock());
            }
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SapExtractorEntity(pos, state);
    }
}
