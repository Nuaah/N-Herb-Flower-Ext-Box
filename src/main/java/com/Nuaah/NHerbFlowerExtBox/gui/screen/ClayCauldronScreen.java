package com.Nuaah.NHerbFlowerExtBox.gui.screen;

import com.Nuaah.NHerbFlowerExtBox.gui.container.ClayCauldronMenu;
import com.Nuaah.NHerbFlowerExtBox.gui.container.ClayCauldronMenu;
import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("removal")
public class ClayCauldronScreen extends AbstractContainerScreen<ClayCauldronMenu> {

    public static final ResourceLocation CLAY_CAULDRON_TEXTURE = new ResourceLocation(NHerbFlowerExtBox.MOD_ID,"textures/gui/container/clay_cauldron.png");

    public static final Component INVENTORY_TITLE = Component.translatable("container." + NHerbFlowerExtBox.MOD_ID + ".inventory");

    public ClayCauldronScreen(ClayCauldronMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.titleLabelX = 8;
        this.titleLabelY = 4;
        this.inventoryLabelX = 8;
        this.inventoryLabelY = this.imageHeight - 92;
    }

    @Override
    public void render(GuiGraphics graphics, int w, int h, float f) {
        this.renderBackground(graphics);
        super.render(graphics, w, h, f);
        this.renderTooltip(graphics, w, h);

        int setW = (this.width - this.imageWidth) / 2;
        addWaterTooltip(graphics,w,h);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float w, int h, int f) {
        int setW = (this.width - this.imageWidth) / 2;
        int setH = (this.height - this.imageHeight) / 2;
        graphics.blit(CLAY_CAULDRON_TEXTURE,setW,setH,0,0,this.imageWidth,this.imageHeight);

        // --- 水の描画 ---
        int water = menu.getWater();

        if (water > 0) {
            int height = (int)((water) * 10); // 最大44pxの縦ゲージ
            int x = setW + this.imageWidth - 41;   // 描画位置X
            int y = topPos + 49 - height; // 下から上に伸ばすように調整

            List<Float> waterColor = menu.getWaterColor();

            RenderSystem.setShaderColor(waterColor.get(0),waterColor.get(1),waterColor.get(2),waterColor.get(3));

            // GUI テクスチャの右側（u=176,v=0 に水スプライトがあると仮定）
            graphics.blit(
                new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "textures/gui/container/clay_cauldron.png"),
                x, y,
                176, 14,   // u,v
                16, height // width,height
            );
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }

        // --- 火の描画 ---
        if (menu.getHeating()){
            int x = setW * 2 - 44;
            int y = topPos + 66;

            graphics.blit(
                new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "textures/gui/container/clay_cauldron.png"),
                x, y,
                176, 0,   // u,v
                14, 14 // width,height
            );
        }

        // --- ゲージの描画 ---
        int progress = menu.getProgress();
        int meter = (int)(progress * 37 / 100);
        int x = setW * 2 - 55;
        int y = topPos + 54;
        graphics.blit(
            new ResourceLocation(NHerbFlowerExtBox.MOD_ID, "textures/gui/container/clay_cauldron.png"),
            x, y,
            176, 44,   // u,v
            meter, 5 // width,height
        );
    }

    private void addWaterTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int water = menu.getWater();

        // ★ 水ゲージの座標をあなたの GUI に合わせる（例）
        int x = 260;   // 描画位置X
        int y = 56; // 下から上に伸ばすように調整
        int width = 16;
        int height = 30;

        // --- マウスが水ゲージ上にあるか？ ---
        if (mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height) {

            // Tooltip に表示するテキスト
            List<Component> tooltip = new ArrayList<>();
            List<Component> lines = new ArrayList<>();
            Map<String,Float> constituents = menu.getConstituents();
            Map<String,Integer> durations = menu.getDurations();

            if (water > 0){
                tooltip.add(Component.translatable("tooltip.clay_cauldron." + NHerbFlowerExtBox.MOD_ID + ".water").withStyle(ChatFormatting.BLUE));
                tooltip.add(Component.translatable("tooltip.clay_cauldron." + NHerbFlowerExtBox.MOD_ID + ".component"));
            } else {
                tooltip.add(Component.translatable("tooltip.clay_cauldron." + NHerbFlowerExtBox.MOD_ID + ".nothing"));
            }

            for (String effect : constituents.keySet()) {

                float level = (float)Math.floor(constituents.get(effect) * 100) / 100;
                int durationTicks = durations.getOrDefault(effect, 0);

                Component effectName = Component.translatable("effect." + effect.replace(':','.'));
                Component levelComponent = Component.literal(String.valueOf(level));

                Component durationComponent = Component.literal("");
                if (durationTicks > 0) {
                    int duration = durationTicks / 20;

                    durationComponent = Component.literal(String.valueOf(duration));
                }

                MutableComponent line = Component.empty()
                        .append(effectName)
                        .append(" Lv.")
                        .append(levelComponent);

                if (!durationComponent.getString().isEmpty()) {
                    line = line.append(Component.literal(" ("))
                            .append(durationComponent)
                            .append(Component.literal("s)"));
                }

                tooltip.add(line);
            }



            // まとめて tooltip に追加
            if (lines.isEmpty()){

            } else {
                tooltip.addAll(lines);
            }

//            List<Component> tooltip = new ArrayList<>();
//            if (water > 0){
//                tooltip.add(Component.translatable("tooltip.clay_cauldron." + NHerbFlowerExtBox.MOD_ID + ".water").withStyle(ChatFormatting.BLUE));
//                tooltip.add(Component.translatable("tooltip.clay_cauldron." + NHerbFlowerExtBox.MOD_ID + ".component"));
//            } else {
//                tooltip.add(Component.translatable("tooltip.clay_cauldron." + NHerbFlowerExtBox.MOD_ID + ".nothing"));
//            }
//
//            Component component = Component.literal("");
//            Component level = Component.literal("") ;
//            Component durationComponent = Component.literal("") ;
//
//            Map<String,Float> constituents = menu.getConstituents();
//            Map<String,Integer> durations = menu.getDurations();
//
//            for (Map.Entry<String, Float> entry : constituents.entrySet()){ //効果レベル
//                String effect = entry.getKey();
//                component = Component.translatable("effect." + effect.replace(':','.'));
//                level = Component.literal(entry.getValue().toString());
//            }
//
//            for (Map.Entry<String, Integer> entry : durations.entrySet()){ //効果時間
//                if (entry.getValue() > 0){
//                    float duration = entry.getValue() / 20.0F;
//                    LocalTime time = LocalTime.of((int)(duration / 60.0F), (int)(duration % 60.0F));
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//
//                    durationComponent = Component.literal(time.format(formatter));
//                }
//            }
//
//            Component result = Component.empty().append(component).append(level).append("(").append(durationComponent).append(")");
//            tooltip.add(result);

            // Tooltip を描画
            guiGraphics.renderTooltip(font, tooltip, Optional.empty(), mouseX, mouseY);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int w, int h) {
        graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x3F3F3F, false);
        graphics.drawString(this.font, this.INVENTORY_TITLE ,this.inventoryLabelX, this.inventoryLabelY, 0x3F3F3F, false);
    }
}
