package slotifier.mixins;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slotifier.Slotifier;
import slotifier.keyboard.KeybindHelper;

@Mixin(GameOptions.class)
public class MixinGameOptions {
    @Final
    @Mutable
    @Shadow
    public KeyBinding[] keysAll;

    @Inject(method = "load", at = @At("HEAD"))
    public void onLoad(CallbackInfo ci) {
        this.keysAll = KeybindHelper.registerKeyBinds(keysAll);
        Slotifier.LOGGER.info("Registered Keybinds");
    }
}
