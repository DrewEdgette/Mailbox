package me.drew.mailbox.events;

import me.drew.mailbox.Mailbox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import java.util.Map;
import java.util.UUID;

public class PlayerEvents implements Listener {
    private final Mailbox plugin;

    public PlayerEvents(Mailbox plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerJoined(PlayerJoinEvent event) {

        Map<UUID, ItemStack[]> inboxMap = plugin.getInbox().getInboxMap();
        String playerName = event.getPlayer().getDisplayName();

        if (inboxMap.isEmpty()) {
            event.setJoinMessage("Welcome to the server, " + playerName + ". You don't have any mail.");
        }

        else {
            event.setJoinMessage("Welcome to the server, " + playerName + ". You have mail waiting for you.");
        }
    }
}
