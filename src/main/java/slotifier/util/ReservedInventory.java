package slotifier.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReservedInventory {
    private Map<Item, Set<Integer>> stackSlotMap;

    public void save(List<Slot> slots) {
        stackSlotMap = Maps.newHashMap();
        // 9 is where main inventory starts excluding hotbar 36 where it ends
        for (int slot = 9; slot < 36; slot++) {
            Item item = slots.get(slot).getStack().getItem();
            if (item != Items.AIR)
                stackSlotMap.computeIfAbsent(item, k -> Sets.newHashSet()).add(slot);
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
        for (int slot : stackSlotMap.get(item)) {
            Slot playerSlot = playerSlots.get(slot);
            if (playerSlot.getStack().getItem() != item)
                return playerSlot;
        }
        return null;
    }

    public boolean isCorrectSlot(Item item, int currentSlot) {
        if (stackSlotMap == null) return false;
        return stackSlotMap.containsKey(item) && stackSlotMap.get(item).contains(currentSlot);
    }
}
