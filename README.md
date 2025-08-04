# 🌍 ChunkPersist

A PaperMC plugin that keeps specific chunks loaded persistently, even when players are not nearby. Ideal for redstone contraptions that needs to stay active while unattended.

## 📦 Features

- Keep any chunk loaded using force-loading
- Load/unload all tracked chunks manually
- Add/remove chunks by coordinates or your current position
- Use `~` to reference your current chunk
- Visual debug mode with particle effects to highlight persisted chunks
- Tab completion and permission-based access control
- Build for PaperMC 1.21.7+

## 📥 Installation

1. Build the `.jar` file using Maven package.
2. Place it in your `plugins/` directory.
3. Restart or reload your server.

## 💬 Commands

| Command | Description |
|--------|-------------|
| `/chunkpersist add <world> <x> <z>` | Adds a chunk to be persisted |
| `/chunkpersist remove <world> <x> <z>` | Removes a chunk from being persisted |
| `/chunkpersist list` | Lists all currently managed chunks |
| `/chunkpersist load_all` | Manually loads all tracked chunks |
| `/chunkpersist unload_all` | Unloads all plugin-loaded chunks |
| `/chunkpersist debug` | Spawns particles at tracked chunks (for visualization) |

💡 Use `~` as a shortcut for current chunk coordinate:  
`/chunkpersist add world_nether ~ ~`
