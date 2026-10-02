package me.wholesome_seal.custom_ender_eyes.events;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import me.wholesome_seal.custom_ender_eyes.Main;
import me.wholesome_seal.custom_ender_eyes.structure.WayPointBuilder;

public class EnderEyeRedirect implements Listener {
    private Main plugin;
    private FileConfiguration config;

    public EnderEyeRedirect(Main plugin) {
        this.plugin = plugin;
        this.config = this.plugin.getConfig();

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }
    
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (this.config.getBoolean("target-strongholds")) return;
        if (!event.getEntityType().equals(EntityType.ENDER_PEARL)) return;

        String waypointStoragePath;
        switch(event.getEntity().getWorld().getEnvironment()) {
            case NETHER: {
                waypointStoragePath = "waypoint.nether";
                if (this.config.getBoolean("allow-nether")) break; 
                return;
            }
            case THE_END: {
                waypointStoragePath = "waypoint.end";
                if (this.config.getBoolean("allow-end")) break;
                return;
            }
            default: {
                waypointStoragePath = "waypoint.overworld";
                break;
            }
        }

        Location eventLocation = event.getLocation();
        World eventWorld = event.getEntity().getWorld();

        WayPointBuilder nearestWaypoint = null;
        double nearestYet = Double.MAX_VALUE;
        for (WayPointBuilder waypoint : WayPointBuilder.getWayPoint(waypointStoragePath)) {
            Location waypointLocation = new Location(eventWorld, waypoint.coordsX, waypoint.coordsY, waypoint.coordsZ);
            double waypointDistance = eventLocation.distance(waypointLocation);

            if (waypointDistance < nearestYet) {
                nearestWaypoint = waypoint;
                nearestYet = waypointDistance;
            }
        }
        if (nearestWaypoint == null) return;

        EnderSignal enderEye = (EnderSignal) event.getEntity();
        enderEye.setTargetLocation(nearestWaypoint.getLocation(eventWorld));
    }
}
