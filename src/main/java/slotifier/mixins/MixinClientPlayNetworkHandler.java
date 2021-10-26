package slotifier.mixins;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slotifier.Slotifier;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler {
    @Inject(method = "onScreenHandlerSlotUpdate", at = @At("HEAD"))
    public void onUpdatePacketReceived(ScreenHandlerSlotUpdateS2CPacket packet, CallbackInfo ci) {
        Slotifier.lastTickWithSlotPackets = Slotifier.tickCounter;
    }
}
