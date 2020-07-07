package me.drew.mailbox;

import me.drew.mailbox.commands.MailCommand;
import me.drew.mailbox.events.EnvelopeEvents;
import me.drew.mailbox.events.InboxEvents;
import me.drew.mailbox.events.PlayerEvents;
import me.drew.mailbox.events.SealedEnvelopeEvents;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Mailbox extends JavaPlugin {

    private Inbox inbox = new Inbox();
    private RegularEnvelope envelope = new RegularEnvelope();
    private SealedEnvelope se = new SealedEnvelope();


    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("we have begun");

        // Commands
        getCommand("mail").setExecutor(new MailCommand(this));

        // Events
        getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);
        getServer().getPluginManager().registerEvents(new InboxEvents(this), this);
        getServer().getPluginManager().registerEvents(new EnvelopeEvents(this), this);
        getServer().getPluginManager().registerEvents(new SealedEnvelopeEvents(this), this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // gets the inbox
    public Inbox getInbox() {
        return inbox;
    }

    // sets the inbox
    public void setInbox(Inbox newInbox) {
        inbox = newInbox;
    }

    // gets the envelope
    public RegularEnvelope getEnvelope() {
        return envelope;
    }

    // sets the envelope
    public void setEnvelope(RegularEnvelope newEnvelope) {
        envelope = newEnvelope;
    }

    // gets the sealed envelope
    public SealedEnvelope getSe() {
        return se;
    }

    // sets the sealed envelope
    public void setSe(SealedEnvelope newSe) {
        se = newSe;
    }
}
