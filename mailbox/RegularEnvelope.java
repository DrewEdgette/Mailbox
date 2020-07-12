package me.drew.mailbox;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegularEnvelope implements Envelope {

    private static final String ENVELOPE_NAME = "Envelope";
    private static final Material envelopeMaterial = Material.BOOK;

    private final Mailbox plugin;
    public RegularEnvelope(Mailbox plugin) {
        this.plugin = plugin;
    }

    private ItemStack[] items;


    // gives the player an envelope item
    public void giveEnvelope(Player player, String ID) {
        ItemStack envelope = InventoryUtils.createItem(ENVELOPE_NAME, envelopeMaterial, ID);
        player.getInventory().setItemInMainHand(envelope);
    }


    // opens the envelope GUI
    public void openEnvelope(Player player, ItemStack item) {
        // creates inventory and adds seal / close menu options
        Inventory envelope = Bukkit.createInventory(player, 9, "Envelope");


        ItemMeta meta = item.getItemMeta();

        if (meta.hasLore()) {
            List<String> lore = meta.getLore();

            String ID = lore.get(0);
            items = plugin.getEnvelopeItems(ID);
        }



        if (items != null) {
            envelope.setContents(items);
        }

        InventoryUtils.addItem(envelope, "Seal", Material.EMERALD_BLOCK, 7);
        InventoryUtils.addItem(envelope, "Close", Material.BARRIER, 8);

        player.openInventory(envelope);
    }

}
