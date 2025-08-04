package nl.lewin.chunkPersist.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.lewin.chunkPersist.ChunkManager;
import org.bukkit.command.CommandSender;

public final class ListCommand implements SubCommand {
    private final ChunkManager chunkManager;

    public ListCommand(ChunkManager chunkManager) {
        this.chunkManager = chunkManager;
    }

    @Override
    public String name() {
        return "list";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(Component.text("Managed Chunks:", NamedTextColor.AQUA));
        for (ChunkManager.TrackedChunk tc : chunkManager.getManagedChunks()) {
            sender.sendMessage(Component.text(" - " + tc.toString(), NamedTextColor.GRAY));
        }
    }
}
