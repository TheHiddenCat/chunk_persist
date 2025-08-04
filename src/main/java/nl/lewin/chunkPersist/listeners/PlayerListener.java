package nl.lewin.chunkPersist.listeners;

import nl.lewin.chunkPersist.ChunkManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public final class PlayerListener implements Listener {
    private final Plugin plugin;
    private final ChunkManager chunkManager;
    private BukkitTask unloadTask = null;
    private static final long ONE_HOUR_TICKS = 60 * 60 * 20; // 1 hour = 72000 ticks

    public PlayerListener(Plugin plugin, ChunkManager chunkManager) {
        this.plugin = plugin;
        this.chunkManager = chunkManager;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        if (unloadTask != null) {
            unloadTask.cancel();
            unloadTask = null;
            plugin.getLogger().info("Player joined – unload task cancelled.");
        }

        if (!chunkManager.loaded()) {
            plugin.getLogger().info("Loading persisted chunks...");
            chunkManager.loadAll();
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (Bukkit.getOnlinePlayers().isEmpty() && unloadTask == null) {
                scheduleUnloadTask();
            }
        }, 40L); // slight delay to ensure event completes
    }

    private void scheduleUnloadTask() {
        plugin.getLogger().info("No players online. Scheduling unload in " + ONE_HOUR_TICKS +" ticks.");
        unloadTask = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            plugin.getLogger().info(ONE_HOUR_TICKS + " ticks passed with no players online. Unloading chunk tickets...");
            chunkManager.unloadAll();
            unloadTask = null;
        }, ONE_HOUR_TICKS);
    }
}
