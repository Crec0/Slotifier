package slotifier.util;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;
import slotifier.Slotifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public enum KeyBinds {
    SAVE(new KeyBinding("Save inventory state", GLFW.GLFW_KEY_K, "Slotifier"), Slotifier::saveInventory),
    CLEAR(new KeyBinding("Clear inventory state", GLFW.GLFW_KEY_L, "Slotifier"), Slotifier::clearCache);

    private final KeyBinding keyBinding;
    private final Consumer<PlayerEntity> consumer;

    KeyBinds(KeyBinding keyBinding, Consumer<PlayerEntity> consumer) {
        this.keyBinding = keyBinding;
        this.consumer = consumer;
    }

    public static List<KeyBinds> getSlotifierKeyBinds() {
        return Arrays.stream(values()).toList();
    }

    public static List<KeyBinding> getKeyBindings() {
        return Arrays.stream(values()).map(KeyBinds::getKeyBinding).toList();
    }

    public KeyBinding getKeyBinding() {
        return keyBinding;
    }

    public void execute(PlayerEntity player) {
        this.consumer.accept(player);
    }
}
