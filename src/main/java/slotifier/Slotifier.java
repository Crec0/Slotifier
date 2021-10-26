package slotifier;

import net.fabricmc.api.ModInitializer;
import net.minecraft.screen.slot.Slot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slotifier.util.ReservedInventory;

import java.util.List;

public class Slotifier implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger();
    public static ReservedInventory reservedInventory;
    public static int tickCounter = 0;
    public static int lastTickWithSlotPackets = -1;

    public static void saveInventory(List<Slot> slots) {
        LOGGER.info("Saved Current Inventory");
        reservedInventory.save(slots);
    }

    public static void clearCache(List<Slot> ignored) {
        LOGGER.info("Cleared saved inventory");
        reservedInventory.clear();
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Slotifier initialized");
        reservedInventory = new ReservedInventory();
    }
}
