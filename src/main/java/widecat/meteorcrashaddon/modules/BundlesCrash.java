package widecat.meteorcrashaddon.modules;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.BundleItemSelectedC2SPacket;
import net.minecraft.screen.CraftingScreenHandler;
import widecat.meteorcrashaddon.CrashAddon;

@SuppressWarnings("unused")
public class BundlesCrash extends Module {

    public BundlesCrash() {
        super(CrashAddon.CATEGORY, "bundle-crash", "Sends a funny bundles packet");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        // I have no idea WHY THIS FUCKING CODE WORKS, it doesn't make any sense.
        if (!(mc.player == null || mc.player.currentScreenHandler instanceof CraftingScreenHandler) || mc.getNetworkHandler() == null) return;
        try {
            mc.player.networkHandler.sendPacket(new BundleItemSelectedC2SPacket(mc.player.getInventory().selectedSlot, Integer.MIN_VALUE));
        } catch (Exception ignored) {
            error("Stopping crash because an error occurred!");
            toggle();
        }
    }

    @EventHandler
    private void onPacketSend(PacketEvent.Send event) {
        if (!(event.packet instanceof BundleItemSelectedC2SPacket packet0)) return;
        if (packet0.selectedItemIndex() < -1) return;
        event.connection.send(new BundleItemSelectedC2SPacket(packet0.slotId(), -1337));
        event.cancel();
    }

    @EventHandler
    private void onPacketSent(PacketEvent.Sent event) {
        if (!(event.packet instanceof BundleItemSelectedC2SPacket packet0)) return;
        event.packet = new BundleItemSelectedC2SPacket(packet0.slotId(), -1337);
    }
}
