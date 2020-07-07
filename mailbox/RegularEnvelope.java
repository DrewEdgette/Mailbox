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

public class RegularEnvelope implements Envelope {

    private static final String ENVELOPE_NAME = "Envelope";


    private Map<UUID, ItemStack[]> envelopeMap = new HashMap<>();

    // gets envelope map
    public Map<UUID, ItemStack[]> getEnvelopeMap() {
        return envelopeMap;
    }

    // sets envelope map
    public void setEnvelopeMap(Map<UUID, ItemStack[]> map) {
        envelopeMap = new HashMap<>(map);
    }

    // gives the player an envelope item
    public void giveEnvelope(Player player) {
        player.getInventory().setItemInMainHand(InventoryUtils.createItem(ENVELOPE_NAME, Material.PAPER));
    }



    // opens the envelope GUI
    public void openEnvelope(Player player) {
        // creates inventory and adds seal / close menu options
        Inventory envelope = Bukkit.createInventory(player, 9, "Envelope");

        if (envelopeMap.get(player.getUniqueId()) != null) {
            envelope.setContents(envelopeMap.get(player.getUniqueId()));
        }

        InventoryUtils.addItem(envelope, "Seal", Material.EMERALD_BLOCK, 7);
        InventoryUtils.addItem(envelope, "Close", Material.BARRIER, 8);

        player.openInventory(envelope);
    }

}
