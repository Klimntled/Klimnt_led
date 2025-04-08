package com.example.paperz;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class NoteListener implements Listener {

    private final PaperZ plugin;

    public NoteListener(PaperZ plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ItemStack leftHand = player.getInventory().getItemInOffHand();
        ItemStack rightHand = player.getInventory().getItemInMainHand();

        if (leftHand != null && leftHand.getType() == Material.PAPER && rightHand != null && rightHand.getType() == Material.FEATHER) {
            event.setCancelled(true);
            String message = event.getMessage();

            // Consume the feather
            if (rightHand.getAmount() > 1) {
                rightHand.setAmount(rightHand.getAmount() - 1);
            } else {
                player.getInventory().setItemInMainHand(null);
            }

            // Add the message to the paper's lore and change the name
            ItemStack paper = leftHand;
            ItemMeta meta = paper.getItemMeta();
            List<String> lore = meta.getLore();

            if (lore == null) {
                lore = new ArrayList<>();
            }

            lore.add(ChatColor.GRAY + message);

            meta.setLore(lore);
            meta.setDisplayName(ChatColor.GOLD + "Записка"); // Set the display name to "Записка"
            paper.setItemMeta(meta);

            player.sendMessage(ChatColor.GREEN + "Note added to the paper.");
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null && item.getType() == Material.PAPER) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                ItemMeta meta = item.getItemMeta();
                List<String> lore = meta.getLore();

                if (lore != null && !lore.isEmpty()) {
                    player.sendMessage(ChatColor.GREEN + "Записка:");
                    for (String line : lore) {
                        player.sendMessage(line);
                    }
                } else {
                    player.sendMessage(ChatColor.YELLOW + "На этой бумажке нету записей.");
                }
            }
        }
    }
}