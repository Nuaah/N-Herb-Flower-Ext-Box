package com.Nuaah.NHerbFlowerExtBox.gui.container;

import com.Nuaah.NHerbFlowerExtBox.block.entity.ClayCauldronEntity;
import com.Nuaah.NHerbFlowerExtBox.block.entity.MillstoneEntity;
import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NHerbFlowerExtBoxContainerTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, NHerbFlowerExtBox.MOD_ID);

    public static final RegistryObject<MenuType<MillstoneMenu>> MILLSTONE =
        MENU_TYPES.register("millstone",
            () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.level();
                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof MillstoneEntity entity) {
                    return new MillstoneMenu(windowId, inv, entity);
                }
                return null;
            })
        );

    public static final RegistryObject<MenuType<ClayCauldronMenu>> CLAY_CAULDRON =
            MENU_TYPES.register("clay_cauldron",
                    () -> IForgeMenuType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        Level level = inv.player.level();
                        BlockEntity be = level.getBlockEntity(pos);
                        if (be instanceof ClayCauldronEntity entity) {
                            return new ClayCauldronMenu(windowId, inv, entity);
                        }
                        return null;
                    })
            );
}
