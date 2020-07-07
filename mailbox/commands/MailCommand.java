package me.drew.mailbox.commands;
import me.drew.mailbox.Mailbox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MailCommand implements CommandExecutor {
    private final Mailbox plugin;

    public MailCommand(Mailbox plugin) {
        this.plugin = plugin;
    }

    // /mail command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            plugin.getEnvelope().giveEnvelope(player);
            player.sendMessage("giving you an envelope");
        }
        return true;
    }
}
