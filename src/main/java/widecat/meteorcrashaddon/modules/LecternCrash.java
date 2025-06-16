package widecat.meteorcrashaddon.modules;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ingame.LecternScreen;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.screen.sync.ItemStackHash;
import widecat.meteorcrashaddon.CrashAddon;

public class LecternCrash extends Module {

    public LecternCrash() {
        super(CrashAddon.CATEGORY, "lectern-crash", "Sends a funny packet when you open a lectern");
    }

    @EventHandler
    private void onOpenScreenEvent(OpenScreenEvent event) {
        if (!(event.screen instanceof LecternScreen)) return;
        Int2ObjectMap<ItemStackHash> itemMap = new Int2ObjectArrayMap<>();
        itemMap.put(0, ItemStackHash.EMPTY);
        mc.getNetworkHandler().sendPacket(new ClickSlotC2SPacket(mc.player.currentScreenHandler.syncId, mc.player.currentScreenHandler.getRevision(), (short) 0, (byte) 0, SlotActionType.QUICK_MOVE, itemMap, ItemStackHash.fromItemStack(mc.player.currentScreenHandler.getCursorStack().copy(), mc.player.networkHandler.method_68823())));
        toggle();
    }
}
