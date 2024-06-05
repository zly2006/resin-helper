package com.github.zly2006.resinhelper.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.FletchingBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(FletchingBlockEntity.Fletching.class)
public class MixinFletchingComponent {
    @Shadow @Final private char quality;

    @Shadow @Final private char impurities;

    @Shadow @Final private char nextLevelImpurities;

    @Shadow @Final private short processsTime;

    @Inject(
            method = "addToTooltip",
            at = @At("HEAD"),
            cancellable = true
    )
    private void appendTooltip(Consumer<Component> consumer, TooltipFlag tooltipFlag, CallbackInfo ci) {
        consumer.accept(Component.translatable("block.minecraft.fletching_table.from"));
        consumer.accept(CommonComponents.space().append(new Component[]{FletchingBlockEntity.Resin.getQualityComponent(this.quality)}).withStyle(ChatFormatting.GRAY));
        consumer.accept(CommonComponents.space().append(new Component[]{FletchingBlockEntity.Resin.getImpuritiesComponent(this.impurities)}).withStyle(ChatFormatting.GRAY));
        consumer.accept(Component.translatable("block.minecraft.fletching_table.to"));
        consumer.accept(Component.literal("In " + processsTime / 20.0 + "s").withStyle(ChatFormatting.GRAY));
        consumer.accept(CommonComponents.space().append(new Component[]{this.quality >= 'j' ? Component.translatable("item.minecraft.amber_gem").withStyle(ChatFormatting.GOLD) : FletchingBlockEntity.Resin.getImpuritiesComponent(this.nextLevelImpurities).withStyle(ChatFormatting.GRAY)}));
        ci.cancel();
    }
}
