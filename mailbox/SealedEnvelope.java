package me.drew.mailbox;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SealedEnvelope implements Envelope {

    private static final String SE_NAME = "Sealed Envelope";
    private static final String TAKE_CONTENTS = "Take Contents";
    private static final String CLOSE = "Close";
    private static final Material envelopeMaterial = Material.ENCHANTED_BOOK;

    private boolean sealBroken;

    private ItemStack[] items;

    private final Mailbox plugin;

    public SealedEnvelope(Mailbox plugin) {
        this.plugin = plugin;
    }

    // first constructor
    public SealedEnvelope(Mailbox plugin, Map<UUID, ItemStack[]> items) {
        this.plugin = plugin;
        sealBroken = false;
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
    public void giveEnvelope(Player player, String ID) {
        ItemStack envelope = InventoryUtils.createItem(SE_NAME, envelopeMaterial, ID);
        player.getInventory().setItemInMainHand(envelope);
    }


    // opens the envelope GUI
    public void openEnvelope(Player player, ItemStack item) {
        // creates inventory and adds seal / close menu options
        Inventory envelope = Bukkit.createInventory(player, 9, SE_NAME);

        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        String ID = lore.get(0);

        items = plugin.getEnvelopeItems(ID);

        if (items != null) {
            envelope.setContents(items);
            player.sendMessage("found items, putting them in. but not really.");
        }

        InventoryUtils.addItem(envelope, TAKE_CONTENTS, Material.EMERALD_BLOCK, 7);
        InventoryUtils.addItem(envelope, CLOSE, Material.BARRIER, 8);

        player.openInventory(envelope);
    }
}
