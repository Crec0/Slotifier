package slotifier.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import slotifier.Slotifier;

public class InventoryUtils {

    public static void dropSlot(int syncId, int slotId, PlayerEntity player, ClientPlayerInteractionManager interactionManager) {
        interactionManager.clickSlot(syncId, slotId, 1, SlotActionType.THROW, player);
    }

    public static void swapSlots(int syncId, int fromSlot, int toSlot, PlayerEntity player, ClientPlayerInteractionManager interactionManager) {
        interactionManager.clickSlot(syncId, fromSlot, 40, SlotActionType.SWAP, player);
        interactionManager.clickSlot(syncId, toSlot, 40, SlotActionType.SWAP, player);
        interactionManager.clickSlot(syncId, fromSlot, 40, SlotActionType.SWAP, player);
    }

    public static void sortInventory() {
        Slotifier.lastTickWithSlotPackets = -1;

        ReservedInventory reservedInventory = Slotifier.reservedInventory;
        if (!reservedInventory.isActive()) return;
        Slotifier.LOGGER.info("Inventory Active");

        MinecraftClient mc = MinecraftClient.getInstance();

        ClientPlayerInteractionManager interactionManager = mc.interactionManager;
        PlayerEntity player = mc.player;
        if (interactionManager == null || player == null) return;
        Slotifier.LOGGER.info("Manager player not null");

        ScreenHandler screenHandler = mc.player.playerScreenHandler;

        for (int i = 9; i < 36; i++) {
            Slot currentSlot = screenHandler.slots.get(i);

            ItemStack itemStack = currentSlot.getStack();
            Item stackItem = itemStack.getItem();
            int syncId = screenHandler.syncId;

            if (!reservedInventory.isCorrectSlot(stackItem, currentSlot.id)) {
                Slot toSlot = reservedInventory.getNextAvailableSlot(player, stackItem);
                if (toSlot != null && toSlot.id != currentSlot.id) {
                    Slotifier.LOGGER.info("SWAP SWAP -> from: {}, to: {}, Item: {}", currentSlot.id, toSlot.id, itemStack);
                    InventoryUtils.swapSlots(syncId, currentSlot.id, toSlot.id, player, interactionManager);
                } else {
                    Slotifier.LOGGER.info("YEET -> Slot: {}, Item: {}", currentSlot.id, itemStack);
                    InventoryUtils.dropSlot(syncId, currentSlot.id, player, interactionManager);
                }
            }
        }

    }
}
