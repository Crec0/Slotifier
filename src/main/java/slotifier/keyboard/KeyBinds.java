package slotifier.keyboard;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.screen.slot.Slot;
import org.lwjgl.glfw.GLFW;
import slotifier.Slotifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public enum KeyBinds {
    SAVE(new KeyBinding("Save inventory state", GLFW.GLFW_KEY_K, "Slotifier"), Slotifier::saveInventory), CLEAR(new KeyBinding("Clear inventory state", GLFW.GLFW_KEY_L, "Slotifier"),
            Slotifier::clearCache);

    private final KeyBinding keyBinding;
    private final Consumer<List<Slot>> inventoryConsumer;

    KeyBinds(KeyBinding keyBinding, Consumer<List<Slot>> inventoryConsumer) {
        this.keyBinding = keyBinding;
        this.inventoryConsumer = inventoryConsumer;
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

    public void execute(List<Slot> slots) {
        Slotifier.LOGGER.info("Execute");
        this.inventoryConsumer.accept(slots);
    }
}
