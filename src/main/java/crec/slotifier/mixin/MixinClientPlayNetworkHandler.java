package crec.slotifier.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.screen.slot.Slot;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {

    @Inject(
        method = "onItemPickupAnimation(Lnet/minecraft/network/packet/s2c/play/ItemPickupAnimationS2CPacket;)V",
        at = @At(value = "JUMP", opcode = Opcodes.IFNULL, ordinal = 0),
        locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void dropUnReservedSlots(ItemPickupAnimationS2CPacket packet, CallbackInfo ci, Entity entity){
        if (entity instanceof ItemEntity){
            ItemStack itemStack = ((ItemEntity) entity).getStack();
            for(Slot slot : Objects.requireNonNull(MinecraftClient.getInstance().player).currentScreenHandler.slots){
                System.out.printf("%d %s \n", slot.getIndex(), slot.getStack());
            }
        }
    }
}
