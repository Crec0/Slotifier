package crec.slotifier.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ItemEntity.class)
public class MixinItemEntity {
    @Inject(
            method = "onPlayerCollision(Lnet/minecraft/entity/player/PlayerEntity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/entity/player/PlayerEntity.sendPickup (Lnet/minecraft/entity/Entity;I)V",
                    shift = At.Shift.AFTER
            )
    )
    public void insertInject(PlayerEntity player, CallbackInfo ci){
        ScreenHandler screenHandler = player.currentScreenHandler;
        ClientPlayerInteractionManager manager = Objects.requireNonNull(MinecraftClient.getInstance().interactionManager);
        for (Slot slot: screenHandler.slots){
            if (slot.getStack().getItem().toString().equals("tuff")){
                System.out.println("tuff?");
                manager.clickSlot(screenHandler.syncId, slot.id, 0, SlotActionType.THROW, player);
            }
        }
    }
}
