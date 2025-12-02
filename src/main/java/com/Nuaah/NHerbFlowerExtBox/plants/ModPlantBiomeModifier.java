package com.Nuaah.NHerbFlowerExtBox.plants;

import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("removal")
public class ModPlantBiomeModifier {
    public static final ResourceKey<BiomeModifier> BELLFLOWER_KEY = createKey("bellflower");
    public static final ResourceKey<BiomeModifier> GARDEN_MARIGOLD_KEY = createKey("garden_marigold");

    public static void bootstrap(BootstapContext<BiomeModifier> context){
        // Biome/PlacedFeature の参照をセットアップ
        // Biome のタグを管理する Registry を参照 (例: 全てのオーバーワールドのバイオーム)
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        // PlacedFeature の Registry を参照
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        // Biome Modifier の登録
        context.register(BELLFLOWER_KEY, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                // Biome の指定: オーバーワールドの全てのバイオームタグを指定
                // BiomeTags.IS_OVERWORLD の代わりに、特定のタグ (例: BiomeTags.IS_FOREST) を使うことも可能
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),

                // Placed Feature の指定: 作成した bellflower_placed を参照
                HolderSet.direct(placedFeatures.getOrThrow(ModPlantPlacedFeature.BELLFLOWER_KEY)),

                // 生成ステップの指定: 花は 'VEGETAL_DECORATION' に置く
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(GARDEN_MARIGOLD_KEY, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                // Biome の指定: オーバーワールドの全てのバイオームタグを指定
                // BiomeTags.IS_OVERWORLD の代わりに、特定のタグ (例: BiomeTags.IS_FOREST) を使うことも可能
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),

                // Placed Feature の指定: 作成した bellflower_placed を参照
                HolderSet.direct(placedFeatures.getOrThrow(ModPlantPlacedFeature.GARDEN_MARIGOLD_KEY)),

                // 生成ステップの指定: 花は 'VEGETAL_DECORATION' に置く
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    public static ResourceKey<BiomeModifier> createKey(String name){
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,new ResourceLocation(NHerbFlowerExtBox.MOD_ID,name));
    }
}
