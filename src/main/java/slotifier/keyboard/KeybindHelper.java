package slotifier.keyboard;

import com.google.common.collect.Lists;
import net.minecraft.client.option.KeyBinding;
import slotifier.mixins.KeyBindingAccessor;

import java.util.List;

public class KeybindHelper {
    public static KeyBinding[] registerKeyBinds(KeyBinding[] existingBinds) {
        var categoryMap = KeyBindingAccessor.slotifier_getCategoryOrderMap();
        categoryMap.put("Slotifier", categoryMap.size() + 1);
        List<KeyBinding> keyBindings = Lists.newArrayList(existingBinds);
        keyBindings.addAll(KeyBinds.getKeyBindings());
        return keyBindings.toArray(new KeyBinding[0]);
    }
}
