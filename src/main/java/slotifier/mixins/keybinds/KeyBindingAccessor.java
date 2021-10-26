package slotifier.mixins.keybinds;

import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor {
    @Accessor("categoryOrderMap")
    static Map<String, Integer> slotifier_getCategoryOrderMap() {
        throw new AssertionError("Mixin borked");
    }
}
