package slotifier.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slotifier.Slotifier;
import slotifier.keyboard.KeyBinds;
import slotifier.util.InventoryUtils;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Inject(method = "handleInputEvents", at = @At("TAIL"))
    private void postInputEventHandling(CallbackInfo ci) {
        if (this.player == null) {
            return;
        }
        KeyBinds.getSlotifierKeyBinds().forEach(keybind -> {
            KeyBinding binding = keybind.getKeyBinding();
            if (!binding.isUnbound() && binding.isPressed()) {
                keybind.execute(this.player.currentScreenHandler.slots);
            }
        });
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void preTick(CallbackInfo ci) {
        Slotifier.tickCounter++;
        if (Slotifier.lastTickWithSlotPackets != -1 && Slotifier.lastTickWithSlotPackets < Slotifier.tickCounter) {
            InventoryUtils.sortInventory();
        }
    }
}
