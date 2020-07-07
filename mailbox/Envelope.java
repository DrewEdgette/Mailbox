package me.drew.mailbox;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.Map;
import java.util.UUID;

public interface Envelope {

    // gets envelope map
    public Map<UUID, ItemStack[]> getEnvelopeMap();

    // sets envelope map
    public void setEnvelopeMap(Map<UUID, ItemStack[]> map);

    // gives the player an envelope item
    public void giveEnvelope(Player player);

    // opens the envelope GUI
    public void openEnvelope(Player player);
}
