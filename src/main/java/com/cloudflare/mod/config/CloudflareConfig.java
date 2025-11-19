package com.cloudflare.mod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CloudflareConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    
    public static final ForgeConfigSpec.BooleanValue enableNonSpecialDateRedirect;
    
    static {
        BUILDER.push("Cloudflare Protection Mod Config");
        
        enableNonSpecialDateRedirect = BUILDER
                .comment("Enable redirect on non-special dates when player disconnects")
                .define("enableNonSpecialDateRedirect", false);
        
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}