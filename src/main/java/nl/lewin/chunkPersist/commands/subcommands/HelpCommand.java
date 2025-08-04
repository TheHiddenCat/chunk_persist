package nl.lewin.chunkPersist.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

public final class HelpCommand implements SubCommand {

    @Override
    public String name() {
        return "help";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(Component.text("[ChunkPersist]", NamedTextColor.AQUA));
        sender.sendMessage(Component.text("/chunkpersist add <world> <x> <z>", NamedTextColor.GRAY));
        sender.sendMessage(Component.text("/chunkpersist remove <world> <x> <z>", NamedTextColor.GRAY));
        sender.sendMessage(Component.text("/chunkpersist list", NamedTextColor.GRAY));
        sender.sendMessage(Component.text("/chunkpersist load_all", NamedTextColor.GRAY));
        sender.sendMessage(Component.text("/chunkpersist unload_all", NamedTextColor.GRAY));
    }
}