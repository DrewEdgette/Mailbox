package me.drew.mailbox;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.nio.charset.MalformedInputException;
import java.util.UUID;

public class Inbox {

    Mailbox plugin;

    public Inbox(Mailbox plugin) {
        this.plugin = plugin;
    }

    private ItemStack[] inboxContents;
    private String inboxName = "-";


    // gets inbox contents
    public ItemStack[] getInbox() {
        return inboxContents;
    }

    // sets inbox contents
    public void setInbox(ItemStack[] contents) { inboxContents = contents; }

    // gets inboxName
    public String getInboxName() {
        return inboxName;
    }

    // sets inboxName
    public void setInboxName(String name) {
        inboxName = name;
    }

    // opens the player's inbox
    public void openInbox(Player player) {
        inboxName = player.getDisplayName() + "'s Mailbox";
        Inventory inboxInv = Bukkit.createInventory(player, 36, inboxName);

        if (plugin.getInboxItems(player.getUniqueId()) != null) {
            inboxInv.setContents(plugin.getInboxItems(player.getUniqueId()));
        }

        player.openInventory(inboxInv);
    }


    // checks if a player owns an inbox
    public boolean ownsInbox(UUID playerID, UUID skullID) {
        return (playerID == skullID);
    }


    // determines what happens when a player clicks on a skull
    public void clickSkull(Player player, UUID skullID) {
        UUID playerID = player.getUniqueId();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // item has meta
        if (itemInHand.hasItemMeta()) {
            // holding sealed envelope
            if (itemInHand.getItemMeta().getDisplayName().equals("Sealed Envelope")) {
                player.sendMessage("you put the sealed envelope in the mailbox");
                Inventory temp = Bukkit.createInventory(player, 36);
                ItemStack[] items = plugin.getInboxItems(skullID);
                temp.setContents(items);
                temp.addItem(itemInHand);
                items = temp.getContents();
                plugin.setInboxItems(skullID, items);
                player.getInventory().remove(itemInHand);
            }
            // not holding sealed envelope
            else {
                // player owns mailbox
                if (ownsInbox(playerID, skullID)) {
                    openInbox(player);
                }
            }
        }
        // item doesn't have meta
        else {
            // player owns mailbox
            if (ownsInbox(playerID, skullID)) {
                openInbox(player);
            }
        }
    }
}
