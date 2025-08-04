package nl.lewin.chunkPersist;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class ChunkManager {
    private final Plugin plugin;
    private final Set<TrackedChunk> managedChunks = new HashSet<>();
    private final File storageFile;
    private boolean isLoaded;

    public record TrackedChunk(UUID worldUUID, int x, int z) {}

    public ChunkManager(Plugin plugin) {
        this.plugin = plugin;
        this.storageFile = new File(plugin.getDataFolder(), "chunks.yml");
        loadFromDisk();
        this.isLoaded = false;
    }

    public boolean loaded() {
        return this.isLoaded;
    }

    public void addChunk(String worldName, int chunkX, int chunkZ) {
        var world = Bukkit.getWorld(worldName);
        if (world == null) return;

        var chunk = world.getChunkAt(chunkX, chunkZ);
        if (!chunk.isLoaded()) {
            chunk.load();
        }
        chunk.setForceLoaded(true);

        var tracked = new TrackedChunk(world.getUID(), chunkX, chunkZ);
        if (managedChunks.add(tracked)) {
            saveToDisk();
        }
    }

    public void removeChunk(String worldName, int chunkX, int chunkZ) {
        var world = Bukkit.getWorld(worldName);
        if (world == null) return;

        var chunk = world.getChunkAt(chunkX, chunkZ);
        chunk.setForceLoaded(false);

        var tracked = new TrackedChunk(world.getUID(), chunkX, chunkZ);
        if (managedChunks.remove(tracked)) {
            saveToDisk();
        }
    }

    public void unloadAll() {
        for (var tc : managedChunks) {
            var world = Bukkit.getWorld(tc.worldUUID);
            if (world != null) {
                var chunk = world.getChunkAt(tc.x, tc.z);
                chunk.setForceLoaded(false);
                plugin.getLogger().info("Unset force-loaded chunk at: " + tc.x + ", " + tc.z);
            }
        }
        this.isLoaded = false;
    }

    public void loadAll() {
        for (var tc : managedChunks) {
            var world = Bukkit.getWorld(tc.worldUUID);
            if (world != null) {
                var chunk = world.getChunkAt(tc.x, tc.z);
                chunk.load();
                chunk.setForceLoaded(true);
                plugin.getLogger().info("Set force-loaded chunk at: " + tc.x + ", " + tc.z);
            }
        }
        this.isLoaded = true;
    }

    public Set<TrackedChunk> getManagedChunks() {
        return new HashSet<>(managedChunks);
    }

    private void saveToDisk() {
        var config = new YamlConfiguration();
        List<Map<String, Object>> chunkList = new ArrayList<>();

        for (var tc : managedChunks) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("world", tc.worldUUID.toString());
            entry.put("x", tc.x);
            entry.put("z", tc.z);
            chunkList.add(entry);
        }

        config.set("chunks", chunkList);

        try {
            config.save(storageFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save tracked chunks: " + e.getMessage());
        }
    }

    private void loadFromDisk() {
        if (!storageFile.exists()) {
            plugin.getLogger().severe("Chunks file not found!");
            return;
        }

        var config = YamlConfiguration.loadConfiguration(storageFile);
        List<Map<?, ?>> chunkList = config.getMapList("chunks");

        for (Map<?, ?> entry : chunkList) {
            try {
                var worldUUID = UUID.fromString(entry.get("world").toString());
                int x = (int) entry.get("x");
                int z = (int) entry.get("z");

                managedChunks.add(new TrackedChunk(worldUUID, x, z));
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to load a tracked chunk entry: " + e.getMessage());
            }
        }
    }
}
