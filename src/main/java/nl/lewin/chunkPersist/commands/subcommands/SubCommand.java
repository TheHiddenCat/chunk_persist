package nl.lewin.chunkPersist.commands.subcommands;

import org.bukkit.command.CommandSender;

public interface SubCommand {
    String name();
    void execute(CommandSender sender, String[] args);
}

