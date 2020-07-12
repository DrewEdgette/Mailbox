package me.drew.mailbox;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class InventoryUtils {

    // checks if there is enough space to transfer items from one inventory to another
    public static boolean hasEnoughSpace(Player player, Inventory inv1, Inventory inv2){
        int inv1Count = -2;
        int inv2Count = 0;
        for (ItemStack item : inv1.getContents()) {
            if (item != null) {
                inv1Count++;
            }
        }

        for (ItemStack item : inv2.getContents()) {
            if (item != null) {
                inv2Count++;
            }
        }

        int inv2FreeSpace = 36 - inv2Count;
        if (inv2FreeSpace >= inv1Count) {
            return true;
        }

        return false;
    }


    // takes items from one inventory and transfers them to another
    public static void disperseInventory(Inventory inv1, Inventory inv2) {
        if (inv1 != null) {
            for (ItemStack item : inv1) {
                if (item != null)
                    inv2.addItem(item);
            }
            removeAllItems(inv1);
        }
    }


    // removes all items from a specified inventory
    public static void removeAllItems(Inventory inv) {
        for (ItemStack item : inv) {
            inv.remove(item);
        }
    }


    // adds an item to the specified inventory
    public static void addItem(Inventory inv, String itemName, Material materialName, Integer index) {
        ItemStack is = new ItemStack(materialName);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(itemName);
        is.setItemMeta(meta);
        inv.setItem(index, is);
    }


    // removes buttons
    public static void removeButtons(Inventory inv) {
        inv.remove(Material.EMERALD_BLOCK);
        inv.remove(Material.BARRIER);
    }


    // gets an item with custom info
    public static ItemStack createItem(String customName, Material material, String ID) {
        ItemStack is = new ItemStack(material);
        ItemMeta isMeta = is.getItemMeta();
        isMeta.setDisplayName(customName); //you can even set color
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ID);
        isMeta.setLore(lore);
        is.setItemMeta(isMeta);
        return is;
    }

    public static void transferItem(Player player, ItemStack item) {
        Inventory inventory = Bukkit.createInventory(player, 36);
        inventory.addItem(item);
    }
}
