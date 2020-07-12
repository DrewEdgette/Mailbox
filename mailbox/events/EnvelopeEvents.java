package me.drew.mailbox.events;

import me.drew.mailbox.InventoryUtils;
import me.drew.mailbox.Mailbox;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class EnvelopeEvents implements Listener {

    private final Mailbox plugin;
    private static final String ENVELOPE_NAME = "Envelope";

    private ItemStack[] items;

    public EnvelopeEvents(Mailbox plugin) {
        this.plugin = plugin;
    }


    // checks to see if player is holding envelope and then opens it
    @EventHandler
    public void onClickedEnvelope(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if ((action == Action.PHYSICAL) || (event.getItem() == null)) return;

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.hasItemMeta()) {
            if (itemInHand.getItemMeta().getDisplayName().equals(ENVELOPE_NAME)) {
                player.sendMessage("you clicked the regular envelope");
                plugin.getEnvelope().openEnvelope(player, itemInHand);
            }
        }
    }



    // handles close / seal button
    @EventHandler
    public void onClickedButton(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInv = event.getClickedInventory();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasLore()) {
                List<String> lore = meta.getLore();
                String ID = lore.get(0);

                if (clickedInv != null && event.getCurrentItem() != null) {
                    if (clickedInv.getTitle().equals(ENVELOPE_NAME)) {
                        switch(event.getCurrentItem().getType()) {
                            case BARRIER:
                                event.setCancelled(true);
                                player.sendMessage("closing envelope...");
                                player.closeInventory();
                                break;
                            case EMERALD_BLOCK:
                                event.setCancelled(true);
                                player.sendMessage("sealing envelope...");
                                player.closeInventory();
                                plugin.setEnvelopeItems(ID, clickedInv.getContents());
                                player.getInventory().remove(Material.BOOK);
                                InventoryUtils.removeAllItems(clickedInv);
                                plugin.getSe().giveEnvelope(player, ID);
                                break;
                        }
                    }
                }
            }
        }
    }

    // saves envelope contents before closing
    @EventHandler
    public void onCloseEnvelope(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inv = event.getInventory();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            if (meta.hasLore()) {
                List<String> lore = meta.getLore();
                String ID = lore.get(0);

                if (event.getView().getTitle().equals(ENVELOPE_NAME)) {
                    InventoryUtils.removeButtons(inv);
                    plugin.setEnvelopeItems(ID, inv.getContents());
                }
            }
        }
    }
}
