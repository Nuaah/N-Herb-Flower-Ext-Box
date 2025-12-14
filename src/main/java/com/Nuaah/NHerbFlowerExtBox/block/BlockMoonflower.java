package com.Nuaah.NHerbFlowerExtBox.block;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import com.Nuaah.NHerbFlowerExtBox.block.entity.ClayCauldronEntity;
import com.Nuaah.NHerbFlowerExtBox.block.entity.MoonflowerEntity;
import com.Nuaah.NHerbFlowerExtBox.block.entity.NHerbFlowerExtBoxEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockMoonflower extends BaseEntityBlock {

    public static final BooleanProperty IS_NIGHT = BooleanProperty.create("is_night");
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final VoxelShape SHAPE = Block.box(5,0,5,11,13,11);

    public BlockMoonflower() {
        super(Properties.of()
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XZ)
            .ignitedByLava()
            .lightLevel((state) -> state.hasProperty(LIT) && state.getValue(LIT) ? 5 : 0));

        this.registerDefaultState(this.stateDefinition.any().setValue(IS_NIGHT, false).setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_NIGHT);
        builder.add(LIT);
    }

    protected boolean mayPlaceOn(BlockState p_51042_, BlockGetter p_51043_, BlockPos p_51044_) {
        return p_51042_.is(BlockTags.DIRT) || p_51042_.is(Blocks.FARMLAND);
    }

    public BlockState updateShape(BlockState p_51032_, Direction p_51033_, BlockState p_51034_, LevelAccessor p_51035_, BlockPos p_51036_, BlockPos p_51037_) {
        return !p_51032_.canSurvive(p_51035_, p_51036_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_51032_, p_51033_, p_51034_, p_51035_, p_51036_, p_51037_);
    }

    public boolean canSurvive(BlockState p_51028_, LevelReader p_51029_, BlockPos p_51030_) {
        BlockState plantState = this.defaultBlockState();
        BlockPos blockpos = p_51030_.below();
        if (p_51028_.getBlock() == this) //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return p_51029_.getBlockState(blockpos).canSustainPlant(p_51029_, blockpos, Direction.UP, (a,b) -> plantState);
        return this.mayPlaceOn(p_51029_.getBlockState(blockpos), p_51029_, blockpos);
    }

    public boolean propagatesSkylightDown(BlockState p_51039_, BlockGetter p_51040_, BlockPos p_51041_) {
        return p_51039_.getFluidState().isEmpty();
    }

    public boolean isPathfindable(BlockState p_51023_, BlockGetter p_51024_, BlockPos p_51025_, PathComputationType p_51026_) {
        return p_51026_ == PathComputationType.AIR && !this.hasCollision ? true : super.isPathfindable(p_51023_, p_51024_, p_51025_, p_51026_);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        var offset = state.getOffset(getter, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        var offset = state.getOffset(world, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MoonflowerEntity(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // サーバー側でのみ処理を行う（クライアント側は自動同期されるため不要）
        return createTickerHelper(type, NHerbFlowerExtBoxEntityTypes.MOONFLOWER.get(), MoonflowerEntity::tick);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moved) {
        super.onPlace(state, level, pos, oldState, moved);

        // ブロック設置時に tick を予約
        level.scheduleTick(pos, this, 20); // 20 = 1秒ごと
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {

        BlockEntity blockEntity = builder.getParameter(LootContextParams.BLOCK_ENTITY);

        System.out.println("DEOP");
        if (blockEntity instanceof MoonflowerEntity entity) {
            boolean isNight = entity.isNight(entity.getLevel());
            if (isNight) return super.getDrops(state, builder);
        }

        return Collections.emptyList();
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof MoonflowerEntity entity) {
            boolean isNight = entity.isNight(world);

            world.setBlock(pos, state.setValue(LIT, isNight), 3);
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource source) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MoonflowerEntity entity) {

            if (entity.isNight(world)) particle(pos,world);
        }
    }

    private static void particle(BlockPos pos,Level world){
        Random random = new Random();

        if (random.nextInt(0,5) != 0)return;

        double d0 = (double)pos.getX() + 0.5D + random.nextDouble(-0.2,0.2);
        double d1 = (double)pos.getY() + 0.7D + random.nextDouble(-0.3,0);
        double d2 = (double)pos.getZ() + 0.5D + random.nextDouble(-0.2,0.2);
        world.addParticle(ParticleTypes.END_ROD, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }
}
