package com.Nuaah.NHerbFlowerExtBox.mixin;

import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(PostChain.class)
public interface PostChainMixin {
    // passesフィールドにアクセスするためのメソッドを定義
    // @Accessor("passes") は Mixin の機能で、プライベートフィールドを公開する
    @Accessor("passes")
    List<PostPass> getPasses();
}
