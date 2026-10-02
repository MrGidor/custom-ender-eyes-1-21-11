# Custom-Ender-Eyes

Ported to 1.21.11 for the Horizons Eternia SMP server.
Feel free to use wherever you like, but credit the original author.

## Setup

- Put the `.jar` in the plugins folder of your server

## Behavior

Redirects the **eyes of ender** towards the nearest waypoint, if there are none, the eyes will keep their default behavior. This feature can be toggled on or off

## Commands

To access these commands the command sender needs to have the `customendereyes.endereye` permission

### Player only

- `/endereye waypoint <create/remove> <name>`
  - `create` Creates a waypoint with the given name at the player's current position in the player's current world
  - `remove` Removes all the waypoints with the given name in the player's current world

### For anyone with permission

- `/endereye target <stronghold/waypoint>`
  - `stronghold` The eyes of ender will move towards the nearest default target, this is usually the nearest stronghold
  - `waypoint` The eyes of ender will move only towards the nearest waypoint

## Config

| Config Key           | Value Type        | Default Value | Description                                                                                                               |
| -------------------- | ----------------- | ------------- | ------------------------------------------------------------------------------------------------------------------------- |
| `target-strongholds` | `true` or `false` | `true`        | Wether or not the eyes of ender should follow their default path. This value is changed by the `/endereye target` command |
