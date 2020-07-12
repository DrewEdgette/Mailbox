package me.drew.mailbox.events;

import me.drew.mailbox.InventoryUtils;
import me.drew.mailbox.Mailbox;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static org.bukkit.Material.SKULL;

public class InboxEvents implements Listener {
    private final Mailbox plugin;

    public InboxEvents(Mailbox plugin) {
        this.plugin = plugin;
    }


    // opens up a player's inbox
    @EventHandler
    public void OnClickedInbox(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if ((action == Action.PHYSICAL)) return;

        if (action == Action.RIGHT_CLICK_BLOCK) {

            Block clickedBlock = event.getClickedBlock();
            Material type = clickedBlock.getType();

            if (type == SKULL) {

                BlockState state = clickedBlock.getState();
                if (((Skull) state).hasOwner()) {
                    UUID skullID = ((Skull) state).getOwningPlayer().getUniqueId();
                    plugin.getInbox().clickSkull(player, skullID);
                }
            }
        }
    }


    // saves inbox contents before closing
    @EventHandler
    public void onCloseInbox(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        String inboxName = player.getDisplayName() + "'s Mailbox";
        Inventory inv = event.getInventory();
        if (event.getView().getTitle().equals(inboxName)) {
            InventoryUtils.removeButtons(inv);
            plugin.setInboxItems(player.getUniqueId(), inv.getContents());
        }
    }
}
