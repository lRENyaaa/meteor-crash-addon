package widecat.meteorcrashaddon.modules;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.BundleItemSelectedC2SPacket;
import net.minecraft.screen.CraftingScreenHandler;
import widecat.meteorcrashaddon.CrashAddon;

public class BundleCrash extends Module {

    public BundleCrash() {
        super(CrashAddon.CATEGORY, "bundle-crash", "Sends a incorrect bundle item select packet, only works on 1.21.4");
    }

    @EventHandler
    private void onTickPost(TickEvent.Post event) {
        if (!(mc.player == null || mc.player.currentScreenHandler instanceof CraftingScreenHandler) || mc.getNetworkHandler() == null) return;
        mc.player.networkHandler.sendPacket(new BundleItemSelectedC2SPacket(mc.player.getInventory().getSelectedSlot(), Integer.MIN_VALUE));
    }

    @EventHandler
    private void onPacketSend(PacketEvent.Send event) {
        if (!(event.packet instanceof BundleItemSelectedC2SPacket packet)) return;
        if (packet.selectedItemIndex() < -1) return;
        event.connection.send(new BundleItemSelectedC2SPacket(packet.slotId(), Integer.MIN_VALUE));
        event.cancel();
    }

    @EventHandler
    private void onPacketSent(PacketEvent.Sent event) {
        if (!(event.packet instanceof BundleItemSelectedC2SPacket packet0)) return;
        event.packet = new BundleItemSelectedC2SPacket(packet0.slotId(), Integer.MIN_VALUE);
    }
}
