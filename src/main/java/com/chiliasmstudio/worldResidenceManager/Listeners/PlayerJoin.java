package com.chiliasmstudio.worldResidenceManager.Listeners;

import com.chiliasmstudio.worldResidenceManager.WorldResidenceManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoin implements Listener {

    private WorldResidenceManager worldResidenceManager;

    public PlayerJoin(WorldResidenceManager worldResidenceManager) {
        this.worldResidenceManager = worldResidenceManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        if (worldResidenceManager.sqlDatabase.isDataExists(playerId)) {
            worldResidenceManager.getComponentLogger().info("Player " + playerId + " already exists");
        } else {
            worldResidenceManager.sqlDatabase.insertData(playerId, 40000, 10000,0);
            worldResidenceManager.getComponentLogger().info("Player " + playerId + " added");
        }
    }
}
