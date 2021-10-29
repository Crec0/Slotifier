package slotifier;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slotifier.util.MessengerUtils;
import slotifier.util.ReservedInventory;

public class Slotifier implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger();
    public static ReservedInventory reservedInventory;
    public static int tickCounter = 0;
    public static int lastTickWithSlotPackets = -1;

    public static void saveInventory(PlayerEntity player) {
        MessengerUtils.sendActionBarMessage(player, "Saved current player inventory state");
        reservedInventory.save(player.playerScreenHandler.slots);
    }

    public static void clearCache(PlayerEntity player) {
        MessengerUtils.sendActionBarMessage(player, "Cleared saved player inventory state");
        reservedInventory.clear();
    }

    public static boolean isEnabled() {
        return reservedInventory.isActive();
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Slotifier initialized");
        reservedInventory = new ReservedInventory();
    }
}
