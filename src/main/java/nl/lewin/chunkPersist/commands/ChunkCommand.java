package nl.lewin.chunkPersist.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.lewin.chunkPersist.ChunkManager;
import nl.lewin.chunkPersist.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class ChunkCommand implements CommandExecutor {
    private final Map<String, SubCommand> subCommands;

    public ChunkCommand(ChunkManager chunkManager) {
        subCommands = new HashMap<>();
        registerSubcommand(new AddCommand(chunkManager));
        registerSubcommand(new RemoveCommand(chunkManager));
        registerSubcommand(new ListCommand(chunkManager));
        registerSubcommand(new LoadAllCommand(chunkManager));
        registerSubcommand(new UnloadAllCommand(chunkManager));
        registerSubcommand(new DebugCommand(chunkManager));
        registerSubcommand(new HelpCommand());
    }

    private void registerSubcommand(SubCommand sub) {
        subCommands.put(sub.name(), sub);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!sender.isOp()) {
            sender.sendMessage(Component.text("You must be a server operator to use this command.", NamedTextColor.RED));
            return true;
        }

        if (args.length == 0) {
            subCommands.get("help").execute(sender, args);
            return true;
        }

        var subName = args[0].toLowerCase();
        var sub = subCommands.get(subName);

        if (sub != null) {
            sub.execute(sender, args);
        } else {
            sender.sendMessage(Component.text("Unknown subcommand. Use /chunkpersist help", NamedTextColor.RED));
        }

        return true;
    }
}
