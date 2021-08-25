package crec.slotifier.mixin;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HandledScreen.class)
public interface MixinMouseInputHandler {
    @Invoker("onMouseClick")
    void imitateMouseClick(Slot slot, int slotId, int button, SlotActionType actionType);
}
