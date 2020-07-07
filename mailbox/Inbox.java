package me.drew.mailbox;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Inbox {
    private Map<UUID, ItemStack[]> inboxMap = new HashMap<>();
    private String inboxName = "IDrinkMilk";


    // gets inboxMap
    public Map<UUID, ItemStack[]> getInboxMap() {
        return inboxMap;
    }

    // sets inboxMap
    public void setInboxMap(Map<UUID, ItemStack[]> map) {
        inboxMap = new HashMap<>(map);
    }

    // gets inboxName
    public String getInboxName() {
        return inboxName;
    }

    // sets inboxName
    public void setInboxName(String name) {
        inboxName = name;
    }

    public void openInbox(Player player) {
        inboxName = player.getDisplayName() + "'s Mailbox";
        Inventory inboxInv = Bukkit.createInventory(player, 36, inboxName);

        if (inboxMap.get(player.getUniqueId()) != null) {
            inboxInv.setContents(inboxMap.get(player.getUniqueId()));
        }

        player.openInventory(inboxInv);
    }

    // tells you if a player owns an inbox or not
    public boolean hasInbox(Player player) {
        return (inboxName.contains(player.getDisplayName()));
    }
}
