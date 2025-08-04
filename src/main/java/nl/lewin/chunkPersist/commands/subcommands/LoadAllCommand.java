package nl.lewin.chunkPersist.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.lewin.chunkPersist.ChunkManager;
import org.bukkit.command.CommandSender;

public final class LoadAllCommand implements SubCommand {
    private final ChunkManager chunkManager;

    public LoadAllCommand(ChunkManager chunkManager) {
        this.chunkManager = chunkManager;
    }

    @Override
    public String name() {
        return "load_all";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        chunkManager.loadAll();
        sender.sendMessage(Component.text("All managed chunks have been loaded.", NamedTextColor.GREEN));
    }
}
