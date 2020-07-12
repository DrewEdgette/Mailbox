package me.drew.mailbox;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.Map;
import java.util.UUID;

public interface Envelope {

    // gives the player an envelope item
    public void giveEnvelope(Player player, String ID);

    // opens the envelope GUI
    public void openEnvelope(Player player, ItemStack item);
}
