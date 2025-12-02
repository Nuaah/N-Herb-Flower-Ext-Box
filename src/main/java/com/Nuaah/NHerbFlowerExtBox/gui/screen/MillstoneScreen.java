package com.Nuaah.NHerbFlowerExtBox.gui.screen;

import com.Nuaah.NHerbFlowerExtBox.gui.container.MillstoneMenu;
import com.Nuaah.NHerbFlowerExtBox.main.NHerbFlowerExtBox;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
@SuppressWarnings("removal")
public class MillstoneScreen extends AbstractContainerScreen<MillstoneMenu> {

    public static final ResourceLocation MILLSTONE_TEXTURE = new ResourceLocation(NHerbFlowerExtBox.MOD_ID,"textures/gui/container/millstone.png");

    public static final Component INVENTORY_TITLE = Component.translatable("container." + NHerbFlowerExtBox.MOD_ID + ".inventory");

    public MillstoneScreen(MillstoneMenu menu, Inventory inventory, Component component) {
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
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float w, int h, int f) {
        int setW = (this.width - this.imageWidth) / 2;
        int setH = (this.height - this.imageHeight) / 2;
        graphics.blit(MILLSTONE_TEXTURE,setW,setH,0,0,this.imageWidth,this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int w, int h) {
        graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x3F3F3F, false);
        graphics.drawString(this.font, this.INVENTORY_TITLE ,this.inventoryLabelX, this.inventoryLabelY, 0x3F3F3F, false);
    }
}
