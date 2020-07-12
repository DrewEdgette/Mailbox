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
            if (itemInHand.getItemMeta().getDisplayName().equals(SE_NAME)) {
                player.sendMessage("you clicked the sealed envelope");
                if (plugin.getSe().getSealBroken()) {
                    plugin.getSe().openEnvelope(player, itemInHand);
                }
            }
        }
    }



    // handles seal button
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


    // saves envelope contents before closing
    @EventHandler
    public void onCloseSealedEnvelope(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inv = event.getInventory();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            if (meta.hasLore()) {
                List<String> lore = meta.getLore();
                String ID = lore.get(0);

                if (event.getView().getTitle().equals(SE_NAME)) {
                    InventoryUtils.removeButtons(inv);
                    plugin.setEnvelopeItems(ID, inv.getContents());
                }
            }
        }
    }
}
