package me.drew.mailbox.events;

import me.drew.mailbox.Mailbox;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InboxEvents implements Listener {
    private final Mailbox plugin;

    public InboxEvents(Mailbox plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnClickedInbox(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if ((action == Action.PHYSICAL)) return;


        if (action == Action.RIGHT_CLICK_BLOCK) {
            if (itemInHand.hasItemMeta()) {
                // holding sealed envelope
                if (itemInHand.getItemMeta().getDisplayName().equals("Sealed Envelope")) {
                    player.sendMessage("you put the sealed envelope in the mailbox");
                }
            }
        }




    }
}
