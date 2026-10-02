package me.wholesome_seal.custom_ender_eyes.events;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EnderSignal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import me.wholesome_seal.custom_ender_eyes.Main;
import me.wholesome_seal.custom_ender_eyes.structure.WayPointBuilder;

public class EnderEyeRedirect implements Listener {
    private final Main plugin;

    public EnderEyeRedirect(Main plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof EnderSignal enderEye)) return;

        FileConfiguration config = this.plugin.getConfig();
        if (config.getBoolean("target-strongholds")) return;

        World world = enderEye.getWorld();
        String waypointStoragePath;
        switch (world.getEnvironment()) {
            case NETHER -> {
                if (!config.getBoolean("allow-nether")) return;
                waypointStoragePath = "waypoint.nether";
            }
            case THE_END -> {
                if (!config.getBoolean("allow-end")) return;
                waypointStoragePath = "waypoint.end";
            }
            default -> waypointStoragePath = "waypoint.overworld";
        }

        Location origin = enderEye.getLocation();

        WayPointBuilder nearestWaypoint = null;
        double nearestYet = Double.MAX_VALUE;
        for (WayPointBuilder waypoint : WayPointBuilder.getWayPoint(waypointStoragePath)) {
            double dx = waypoint.coordsX + 0.5 - origin.getX();
            double dz = waypoint.coordsZ + 0.5 - origin.getZ();
            double distanceSquared = dx * dx + dz * dz;

            if (distanceSquared < nearestYet) {
                nearestWaypoint = waypoint;
                nearestYet = distanceSquared;
            }
        }
        if (nearestWaypoint == null) return;

        enderEye.setTargetLocation(nearestWaypoint.getLocation(world));
    }
}
