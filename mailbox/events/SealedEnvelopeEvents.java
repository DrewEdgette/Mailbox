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

public class SealedEnvelopeEvents implements Listener {
    private final Mailbox plugin;
    private static final String SE_NAME = "Sealed Envelope";

    public SealedEnvelopeEvents(Mailbox plugin) {
        this.plugin = plugin;
    }

    // checks to see if player is holding envelope and then opens it
    @EventHandler
    public void onClickedSealedEnvelope(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if ((action == Action.PHYSICAL) || (event.getItem() == null)) return;

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.hasItemMeta()) {
            if (itemInHand.getItemMeta().getDisplayName().equals(SE_NAME) && plugin.getSe().getSealBroken()) {
                player.sendMessage("you clicked the sealed envelope");
                plugin.getSe().openEnvelope(player);
            }
        }
    }



    // handles close / seal button me.drew.mailbox.commands
    @EventHandler
    public void onClickedSealedEnvelopeButton(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInv = event.getClickedInventory();

        if (clickedInv != null && event.getCurrentItem() != null) {
            if (clickedInv.getTitle().equals(SE_NAME)) {
                switch(event.getCurrentItem().getType()) {
                    case BARRIER:
                        event.setCancelled(true);
                        player.sendMessage("closing envelope...");
                        player.closeInventory();
                        break;
                    case EMERALD_BLOCK:
                        event.setCancelled(true);
                        player.sendMessage("dispersing contents...");
                        player.closeInventory();
                        player.getInventory().remove(Material.PAPER);
                        if (InventoryUtils.hasEnoughSpace(player, event.getClickedInventory(), player.getInventory())) {
                            InventoryUtils.disperseInventory(event.getClickedInventory(), player.getInventory());
                        }
                        else {
                            player.sendMessage("you don't have enough space to take this");
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }



    @EventHandler
    public void onCloseSealedEnvelope(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals(SE_NAME)) {
            InventoryUtils.removeButtons(event.getInventory());
            Map<UUID, ItemStack[]> map = new HashMap<>();
            map.put(event.getPlayer().getUniqueId(), event.getInventory().getContents());
            plugin.getSe().setEnvelopeMap(map);
        }
    }
}
