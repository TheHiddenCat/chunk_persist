package nl.lewin.chunkPersist.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.lewin.chunkPersist.ChunkManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class RemoveCommand implements SubCommand {
    private final ChunkManager chunkManager;

    public RemoveCommand(ChunkManager chunkManager) {
        this.chunkManager = chunkManager;
    }

    @Override
    public String name() {
        return "remove";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 4) {
            sender.sendMessage(Component.text("Usage: /chunkpersist remove <world> <x> <z>", NamedTextColor.RED));
            return;
        }

        var world = args[1];
        var ax = args[2];
        var ay = args[3];

        int x, z;

        if (ax.equals("~") || ay.equals("~")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(Component.text("Only players can use '~' in coordinates.", NamedTextColor.RED));
                return;
            }
            var chunk = player.getLocation().getChunk();
            x = ax.equals("~") ? chunk.getX() : Integer.parseInt(ax);
            z = ay.equals("~") ? chunk.getZ() : Integer.parseInt(ay);
        } else {
            x = Integer.parseInt(ax);
            z = Integer.parseInt(ay);
        }

        chunkManager.removeChunk(world, x, z);
        sender.sendMessage(Component.text("Chunk removed at " + x + "," + z + " in " + world, NamedTextColor.GREEN));
    }
}
