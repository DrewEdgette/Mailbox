package me.drew.mailbox;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SealedEnvelope implements Envelope {

    private static final String SE_NAME = "Sealed Envelope";
    private static final String TAKE_CONTENTS = "Take Contents";
    private static final String CLOSE = "Close";

    private Map<UUID, ItemStack[]> sealedMap;
    private boolean sealBroken;

    // first constructor
    public SealedEnvelope(Map<UUID, ItemStack[]> items) {
        sealedMap = new HashMap<>(items);
        sealBroken = false;
    }


    // second constructor
    public SealedEnvelope() {
        sealedMap = new HashMap<>();
        sealBroken = false;
    }

    // gets the sealed envelope map
    public Map<UUID, ItemStack[]> getEnvelopeMap() {
        return sealedMap;
    }

    // sets sealed envelope map
    public void setEnvelopeMap(Map<UUID, ItemStack[]> map) {
        sealedMap = new HashMap<>(map);
    }


    // gets sealBroken
    public boolean getSealBroken() {
        return sealBroken;
    }

    // sets sealBroken
    public void setSealBroken(boolean value) {
        sealBroken = value;
    }


    // overrides giveEnvelope so that the name is different
    public void giveEnvelope(Player player) {
        player.getInventory().setItemInMainHand(InventoryUtils.createItem(SE_NAME, Material.PAPER));
    }


    // opens the envelope GUI
    public void openEnvelope(Player player) {
        // creates inventory and adds seal / close menu options
        Inventory envelope = Bukkit.createInventory(player, 9, SE_NAME);

        for (UUID key : sealedMap.keySet()) {
            System.out.println(key);
        }

        if (sealedMap.get(player.getUniqueId()) != null) {
            envelope.setContents(sealedMap.get(player.getUniqueId()));
            player.sendMessage("found items, putting them in. but not really.");
        }

        InventoryUtils.addItem(envelope, TAKE_CONTENTS, Material.EMERALD_BLOCK, 7);
        InventoryUtils.addItem(envelope, CLOSE, Material.BARRIER, 8);

        player.openInventory(envelope);
    }
}
