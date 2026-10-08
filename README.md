<img src="src/main/resources/logo.png" align="right" width="128" alt="Ancient Trees Reworked logo" />

# Ancient Trees Reworked

Thirteen species of ancient trees for Minecraft, rebuilt for **Minecraft 26.3** and **NeoForge**.

A rewrite of [Ancient Trees / Dendrology](https://github.com/scottkillen-minecraft-mods/ancient-trees) by ScottKillen, Blorph and Ruyuna.

> _In the time before The Fall, the world was vibrant, teeming with life. The Fall changed the world and only a portion of what was
> has survived. In their wisdom, the Ancient Ones foresaw the Extinction and took steps to preserve trees within chests hidden in the
> world. It is tragic that, for all the good they did, the Ancient Ones could not save themselves..._

## Features

Every species has a complete wood family: Log, Wood, Stripped Log, Stripped Wood, Planks, Stairs, Slab, Fence, Fence Gate, Door,
Trapdoor, Pressure Plate, Button, Sign, Hanging Sign, Boat, Chest Boat, Leaves and Sapling.

### Species

| Species | Shape | Natural biomes |
|---|---|---|
| Acemus | small tree, rarely a large one; autumn leaf colors | forests, plains |
| Cedrum | tiered conifer, also in shallow water | taiga, mountains |
| Cerasu | small tree, rarely a large one; blossom leaves | cherry grove, forests |
| Delnas | straight trunk, flat crown | forests, plains |
| Ewcaly | tall tree with leaf tufts | savanna, badlands |
| Hekur | leaning, rooted trunk | swamps |
| Kiparis | slim cypress, may stand in water | swamps, jungles |
| Kulist | branching tree, may stand in water | forests, plains |
| Lata | wide crown on many branches | forests, plains |
| Nucis | wide crown on many branches | forests, plains |
| Porffor | small tree, rarely a large one | forests, plains |
| Salyx | willow with hanging leaf curtains | swamps, rivers |
| Tuopa | tall, narrow conifer | taiga, mountains |

### Ancient Parcel

Found rarely in dungeon, mineshaft, stronghold, desert temple and jungle temple chests. Right-click to open it:
most parcels crumble to dust, some contain a sapling.

### Sapling brewing

Ewcaly saplings brew like sugar (Potion of Swiftness), Kiparis saplings like a spider eye (Potion of Poison).

## Configuration

`config/ancient_trees-local.toml`:

| Option | Default | |
|---|---|---|
| `worldgen.naturalTrees` | `true` | natural trees on or off |
| `worldgen.chunksPerTree` | `4` | one tree per this many chunks in a biome |
| `saplings.leafDropMultiplier` | `1.0` | factor on the sapling drop chance from leaves (0 disables) |
| `parcels.chestChance` | `0.1` | chance for parcels in a chest (0 disables) |
| `parcels.chestTables` | 7 vanilla chests | loot tables that can contain parcels |

Loot-table options apply after a world reload.

## Credits and license

Original mod by ScottKillen, Blorph and Ruyuna (German translation by MCManuelLP). Rewrite by Lays24MC, released under the MIT license.

This is a private hobby project that I port for fun in my free time, so I cannot guarantee compatibility with other mods.

### Textures

Most textures are based on Minecraft's own textures (boats, signs, doors and similar), recolored to match the matching wood types and adjusted to look more like vanilla. They remain the property of Mojang. I'm no pixel-art pro, so please don't expect too much from them.

Not an official Minecraft product.
