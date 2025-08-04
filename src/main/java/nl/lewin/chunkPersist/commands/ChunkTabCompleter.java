package nl.lewin.chunkPersist.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ChunkTabCompleter implements TabCompleter {

    private static final List<String> SUBCOMMANDS = List.of(
            "add", "remove", "list", "load_all", "unload_all", "debug", "help"
    );

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (!sender.isOp()) return List.of();

        if (args.length == 1) {
            return SUBCOMMANDS.stream()
                    .filter(sub -> sub.startsWith(args[0].toLowerCase()))
                    .toList();
        }

        final var sub = args[0].toLowerCase();

        switch (sub) {
            case "add", "remove" -> {
                if (args.length == 2) {
                    return Bukkit.getWorlds().stream()
                            .map(World::getName)
                            .filter(name -> name.startsWith(args[1]))
                            .collect(Collectors.toList());
                }
                if (args.length == 3 || args.length == 4) {
                    return List.of("~");
                }
            }
        }

        return new ArrayList<>();
    }
}

