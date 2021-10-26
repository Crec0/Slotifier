package slotifier.util;

import com.google.common.collect.Sets;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import slotifier.Slotifier;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ReservedInventory {
    private HashMap<Item, Set<Integer>> stackSlotMap;
    private HashMap<Item, Set<Integer>> copyMap;

    public void save(List<Slot> slots) {
        stackSlotMap = new HashMap<>();
        // 9 is where main inventory starts excluding hotbar 36 where it ends
        for (int slot = 9; slot < 36; slot++) {
            Item item = slots.get(slot).getStack().getItem();
            if (stackSlotMap.containsKey(item)) {
                stackSlotMap.get(item).add(slot);
            } else {
                stackSlotMap.put(item, Sets.newHashSet(slot));
            }
        }
        copyMap = this.getCopy();
        Slotifier.LOGGER.info(stackSlotMap.toString());
    }

    public void clear() {
        stackSlotMap = null;
    }

    public boolean isActive() {
        return stackSlotMap != null;
    }

    public HashMap<Item, Set<Integer>> getCopy() {
        if (stackSlotMap == null) return null;
        HashMap<Item, Set<Integer>> copy = new HashMap<>();
        for (var entry : stackSlotMap.entrySet()) {
            Set<Integer> slots = Sets.newLinkedHashSet();
            slots.addAll(entry.getValue());
            copy.put(entry.getKey(), slots);
        }
        return copy;
    }

    public Slot getNextAvailableSlot(PlayerEntity player, Item item) {
        if (copyMap == null || !copyMap.containsKey(item)) return null;
        ScreenHandler screenHandler = player.playerScreenHandler;
        for (int index : copyMap.get(item)) {
            Slot slot = screenHandler.slots.get(index);
            Item slotItem = slot.getStack().getItem();
            if (slotItem instanceof AirBlockItem || slotItem != item) {
                return slot;
            }
        }
        return null;
    }

    public boolean isCorrectSlot(Item item, int currentSlot) {
        return stackSlotMap != null && stackSlotMap.containsKey(item) && stackSlotMap.get(item).contains(currentSlot);
    }
}
