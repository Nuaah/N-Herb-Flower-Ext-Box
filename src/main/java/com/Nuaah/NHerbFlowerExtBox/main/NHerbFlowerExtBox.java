package com.Nuaah.NHerbFlowerExtBox.main;

import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxBlocks;
import com.Nuaah.NHerbFlowerExtBox.NHerbFlowerExtBoxItems;
import com.Nuaah.NHerbFlowerExtBox.block.entity.NHerbFlowerExtBoxEntityTypes;
import com.Nuaah.NHerbFlowerExtBox.gui.container.NHerbFlowerExtBoxContainerTypes;
import com.Nuaah.NHerbFlowerExtBox.regi.*;
import com.Nuaah.NHerbFlowerExtBox.regi.net2.ConstituentsManager;
import com.Nuaah.NHerbFlowerExtBox.regi.net2.PacketSyncConstituents;
import com.Nuaah.NHerbFlowerExtBox.regi.net2.PlayerEventHandler;
import com.Nuaah.NHerbFlowerExtBox.regi.tab.NHerbFlowerExtBoxTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

@Mod("nherbflowerextbox")
@SuppressWarnings("removal")
public class NHerbFlowerExtBox {

    public static final String MOD_ID = "nherbflowerextbox";

    public NHerbFlowerExtBox(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        NHerbFlowerExtBoxBlocks.Blocks.BLOCKS.register(bus);
        NHerbFlowerExtBoxBlocks.BlockItems.BLOCK_ITEMS.register(bus);
        NHerbFlowerExtBoxItems.ITEMS.register(bus);
        NHerbFlowerExtBoxTabs.MOD_TABS.register(bus);
        NHerbFlowerExtBoxEntityTypes.BLOCK_ENTITY_TYPES.register(bus);
        NHerbFlowerExtBoxContainerTypes.MENU_TYPES.register(bus);
        NHerbFlowerExtBoxRecipeSerializers.SERIALIZERS.register(bus);
        NHerbFlowerExtBoxRecipeType.RECIPE_TYPES.register(bus);
        NHerbFlowerExtBoxEffect.EFFECTS.register(bus);

//        MinecraftForge.EVENT_BUS.register(ServerPacketHandler.class);

        bus.addListener(this::commonSetup);

        // 通常のゲームイベント（プレイヤーログインなど）は Forge イベントバスに登録
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            registerPackets();

            // サーバーでも JSON を読み込ませる（ConstituentsJsonLoader にロード用メソッドがある想定）
            // もし loader が ResourceManager に依存するなら、common側で読み込める実装にする必要あり
            try {
//                ConstituentsManager.loadAll(); // ← あなたのローダーのロード関数を呼ぶ
                System.out.println("ConstituentsJsonLoader: loaded on server/common setup, size=" + ConstituentsJsonLoader.CONSTITUENTS_DATA.size());
            } catch (Exception e) {
                e.printStackTrace();
            }

            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                    new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "bellflower"),
                    NHerbFlowerExtBoxBlocks.Blocks.POTTED_BELLFLOWER
            );
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                    new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "garden_marigold"),
                    NHerbFlowerExtBoxBlocks.Blocks.POTTED_GARDEN_MARIGOLD
            );
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                    new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "shepherds_purse"),
                    NHerbFlowerExtBoxBlocks.Blocks.POTTED_SHEPHERDS_PURSE
            );
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                    new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "lavender"),
                    NHerbFlowerExtBoxBlocks.Blocks.POTTED_LAVENDER
            );
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                    new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "ginseng"),
                    NHerbFlowerExtBoxBlocks.Blocks.POTTED_GINSENG
            );
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                    new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "peppermint"),
                    NHerbFlowerExtBoxBlocks.Blocks.POTTED_PEPPERMINT
            );
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                    new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "moonflower"),
                    NHerbFlowerExtBoxBlocks.Blocks.POTTED_MOONFLOWER
            );
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                    new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "prickly_pear"),
                    NHerbFlowerExtBoxBlocks.Blocks.POTTED_PRICKLY_PEAR
            );
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                    new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "peyote"),
                    NHerbFlowerExtBoxBlocks.Blocks.POTTED_PEYOTE
            );
        });
    }

    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    // モッドの初期化時（FMLCommonSetupEventなど）に呼び出す
    public static void registerPackets() {
        int id = 0;
        CHANNEL.registerMessage(
                id++,
                PacketSyncConstituents.class,
                PacketSyncConstituents::encode,
                PacketSyncConstituents::decode,
                PacketSyncConstituents::handle,
                java.util.Optional.of(net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT)
        );
    }
}
