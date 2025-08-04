package nl.lewin.chunkPersist;

import nl.lewin.chunkPersist.commands.ChunkCommand;
import nl.lewin.chunkPersist.commands.ChunkTabCompleter;
import nl.lewin.chunkPersist.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChunkPersist extends JavaPlugin {
    private ChunkManager chunkManager;

    @Override
    public void onEnable() {
        this.chunkManager = new ChunkManager(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this, chunkManager), this);
        var command = getCommand("chunkpersist");
        if (command != null) {
            command.setExecutor(new ChunkCommand(chunkManager));
            command.setTabCompleter(new ChunkTabCompleter());
        }
        else {
            getLogger().severe("Could not initialize `chunkpersist` command because `getCommand` returned null!");
        }
    }

    @Override
    public void onDisable() {
        chunkManager.unloadAll();
    }
}
