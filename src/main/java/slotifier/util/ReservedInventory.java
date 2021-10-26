package slotifier.util;

import com.google.common.collect.Sets;
import net.minecraft.item.Item;
import net.minecraft.screen.slot.Slot;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ReservedInventory {
    private HashMap<Item, Set<Integer>> stackSlotMap;

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
    }

    public void clear() {
        stackSlotMap = null;
    }

    public boolean isActive() {
        return stackSlotMap != null;
    }

    public Slot getNextAvailableSlot(Item item, List<Slot> playerSlots) {
        if (!stackSlotMap.containsKey(item))
            return null;

        for (int index : stackSlotMap.get(item)) {
            Slot slot = playerSlots.get(index);
            if (slot.getStack().getItem() != item)
                return slot;
        }
        return null;
    }

    public boolean isCorrectSlot(Item item, int currentSlot) {
        if (stackSlotMap == null)
            return false;
        return stackSlotMap.containsKey(item) && stackSlotMap.get(item).contains(currentSlot);
    }
}
