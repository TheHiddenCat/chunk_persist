package nl.lewin.chunkPersist.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.lewin.chunkPersist.ChunkManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class DebugCommand implements SubCommand {
    private final ChunkManager chunkManager;

    public DebugCommand(ChunkManager chunkManager) {
        this.chunkManager = chunkManager;
    }

    @Override
    public String name() {
        return "debug";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            for (var tc : chunkManager.getManagedChunks()) {
                var world = Bukkit.getWorld(tc.worldUUID());
                if (world == null) continue;

                var chunk = world.getChunkAt(tc.x(), tc.z());
                var centerX = (chunk.getX() << 4) + 8;
                var centerZ = (chunk.getZ() << 4) + 8;
                var y = player.getLocation().getY();

                var loc = new Location(world, centerX + 0.5, y, centerZ + 0.5);
                Particle.DustOptions dust;
                if (chunk.isForceLoaded()) {
                    dust = new Particle.DustOptions(Color.fromRGB(0, 255, 0), 1.8f);
                }
                else {
                    dust = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1.8f);
                }
                world.spawnParticle(Particle.DUST, loc, 10, 0.3, 0.5, 0.3, 0.1, dust);
            }
        } else {
            sender.sendMessage(Component.text("Only players can use this command", NamedTextColor.RED));
        }
    }
}