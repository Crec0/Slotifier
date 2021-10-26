package slotifier.mixins.keybinds;

import com.google.common.collect.Lists;
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
import slotifier.util.KeyBinds;

import java.util.List;

@Mixin(GameOptions.class)
public class MixinGameOptions {
    @Final
    @Mutable
    @Shadow
    public KeyBinding[] keysAll;

    @Inject(method = "load", at = @At("HEAD"))
    public void onLoad(CallbackInfo ci) {
        // get categories and add our category at the end so the game knows about it
        var categoryMap = KeyBindingAccessor.slotifier_getCategoryOrderMap();
        categoryMap.put("Slotifier", categoryMap.size() + 1);

        // convert current keybindings to arraylist and add our new keybindings
        List<KeyBinding> keyBindings = Lists.newArrayList(this.keysAll);
        keyBindings.addAll(KeyBinds.getKeyBindings());

        // update the keys with our keys added
        this.keysAll = keyBindings.toArray(new KeyBinding[0]);

        Slotifier.LOGGER.info("Registered Keybinds");
    }
}
