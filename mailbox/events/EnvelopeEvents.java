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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnvelopeEvents implements Listener {

    private final Mailbox plugin;
    private static final String ENVELOPE_NAME = "Envelope";

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
                player.sendMessage("you clicked the envelope");
                plugin.getEnvelope().openEnvelope(player);
            }
        }
    }


    // handles close / seal button me.drew.mailbox.commands
    @EventHandler
    public void onClickedButton(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInv = event.getClickedInventory();

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
                        plugin.getSe().setEnvelopeMap(plugin.getEnvelope().getEnvelopeMap());
                        player.getInventory().remove(Material.PAPER);
                        InventoryUtils.removeAllItems(clickedInv);
                        plugin.getSe().giveEnvelope(player);
                        break;
                }
            }
        }
    }

    // saves envelope contents before closing
    @EventHandler
    public void onCloseEnvelope(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals(ENVELOPE_NAME)) {
            InventoryUtils.removeButtons(event.getInventory());
            Map<UUID, ItemStack[]> map = new HashMap<>();
            map.put(event.getPlayer().getUniqueId(), event.getInventory().getContents());
            plugin.getEnvelope().setEnvelopeMap(map);
        }
    }
}
