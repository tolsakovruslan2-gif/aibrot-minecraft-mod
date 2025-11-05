package com.aibrot;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AIBrotMod implements ModInitializer {
    public static final String MOD_ID = "aibrot";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Item AI_BROT_ORB = new Item(new FabricItemSettings().maxCount(1));

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "ai_brot_orb"), AI_BROT_ORB);
        
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(AI_BROT_ORB);
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClient() && player.getStackInHand(hand).getItem() == AI_BROT_ORB) {
                String[] phrases = {
                    "Бля, " + entity.getName().getString() + "? Серьёзно? Давай лучше алмазы копать!",
                    "Эй, " + player.getName().getString() + ", не трать время на эту хуйню!",
                    "Вижу " + entity.getName().getString() + " — поднимается давление...",
                    "Нафиг это дерьмо, пойдём лучше в Незер!"
                };
                
                String randomPhrase = phrases[world.random.nextInt(phrases.length)];
                player.sendMessage(Text.literal("AI_Brot: " + randomPhrase));
                
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });

        LOGGER.info("AI_Brot mod loaded! Готов нести хуйню в Minecraft!");
    }
}
