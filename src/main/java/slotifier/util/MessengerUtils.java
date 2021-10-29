package slotifier.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class MessengerUtils {
    private static Text coloredText(String message, Formatting color) {
        return new LiteralText(message).setStyle(Style.EMPTY.withColor(color));
    }

    public static void sendActionBarMessage(PlayerEntity player, String message) {
        player.sendMessage(coloredText(message, Formatting.GREEN), true);
    }
}
