package com.cloudflare.mod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import org.slf4j.Logger;
import java.net.URI;
import java.time.LocalDate;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CloudflareProtectionMod.MODID)
public class CloudflareProtectionMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "cloudflareprotectsthiswebsite";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Flag to track if player was disconnected
    private static boolean wasDisconnected = false;

    public CloudflareProtectionMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @OnlyIn(Dist.CLIENT)
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // Reset disconnect flag on login
        wasDisconnected = false;
        
        // Check if the player is in a multiplayer world
        if (Minecraft.getInstance().level != null && !Minecraft.getInstance().isLocalServer()) {
            // Get current date
            LocalDate currentDate = LocalDate.now();
            // Check if it's November 18th
            if (currentDate.getMonthValue() == 11 && currentDate.getDayOfMonth() == 18) {
                // Open the Cloudflare error page
                Util.getPlatform().openUri(URI.create("https://www.cloudflare.com/zh-cn/5xx-error-landing/?utm_source=errorcode_500&utm_campaign=maven.minecraftforge.net"));
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onScreenOpened(ScreenEvent.Opening event) {
        Screen screen = event.getNewScreen();
        // Check if we're opening the multiplayer screen
        if (screen instanceof JoinMultiplayerScreen) {
            // Get current date
            LocalDate currentDate = LocalDate.now();
            // Check if it's November 18th
            if (currentDate.getMonthValue() == 11 && currentDate.getDayOfMonth() == 18) {
                // Open the Cloudflare error page
                Util.getPlatform().openUri(URI.create("https://www.cloudflare.com/zh-cn/5xx-error-landing/?utm_source=errorcode_500&utm_campaign=maven.minecraftforge.net"));
            } else if (wasDisconnected) {
                // For other dates, show the page when returning to server list after disconnection
                Util.getPlatform().openUri(URI.create("https://www.cloudflare.com/zh-cn/5xx-error-landing/?utm_source=errorcode_500&utm_campaign=maven.minecraftforge.net"));
                // Reset the flag
                wasDisconnected = false;
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        // Check if the player is in a multiplayer world
        if (Minecraft.getInstance().level != null && !Minecraft.getInstance().isLocalServer()) {
            // Get current date
            LocalDate currentDate = LocalDate.now();
            // Check if it's November 18th
            if (currentDate.getMonthValue() == 11 && currentDate.getDayOfMonth() == 18) {
                // Open the Cloudflare error page
                Util.getPlatform().openUri(URI.create("https://www.cloudflare.com/zh-cn/5xx-error-landing/?utm_source=errorcode_500&utm_campaign=maven.minecraftforge.net"));
            }
            // For other dates, do not show the page on voluntary logout
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onClientDisconnect(ClientPlayerNetworkEvent.LoggingOut event) {
        // Check if the player is in a multiplayer world
        if (Minecraft.getInstance().level != null && !Minecraft.getInstance().isLocalServer()) {
            // Get current date
            LocalDate currentDate = LocalDate.now();
            // Check if it's November 18th
            if (currentDate.getMonthValue() == 11 && currentDate.getDayOfMonth() == 18) {
                // Open the Cloudflare error page
                Util.getPlatform().openUri(URI.create("https://www.cloudflare.com/zh-cn/5xx-error-landing/?utm_source=errorcode_500&utm_campaign=maven.minecraftforge.net"));
            } else {
                // For other dates, set the flag when disconnected (non-voluntary exit)
                wasDisconnected = true;
            }
        }
    }
}
