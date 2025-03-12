

package com.chiliasmstudio.worldResidenceManager.Listeners;

import com.bekvon.bukkit.residence.event.ResidenceDeleteEvent;
import com.chiliasmstudio.worldResidenceManager.WorldResidenceManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ResidenceDelete implements Listener {

    private final WorldResidenceManager worldResidenceManager;
    public ResidenceDelete(WorldResidenceManager worldResidenceManager) {
        this.worldResidenceManager = worldResidenceManager;
    }

    @EventHandler
    public void onResidenceDeleteEvent(ResidenceDeleteEvent event){
        worldResidenceManager.sqlDatabase.reduceCurrentSize(event.getPlayer().getUniqueId(),event.getResidence().getXZSize());

        long free_size_left = worldResidenceManager.sqlDatabase.getFreeSize(event.getPlayer().getUniqueId())-worldResidenceManager.sqlDatabase.getCurrentSize(event.getPlayer().getUniqueId());
        free_size_left = Math.max(0, free_size_left);
        event.getPlayer().sendMessage(Component.text("您還有 " + free_size_left + " 格免費額度").color(TextColor.fromHexString("#42A5FA")));

        // Notify the player of successful residence creation
        long remainingBlocks = worldResidenceManager.sqlDatabase.getMaxSize(event.getPlayer().getUniqueId())-worldResidenceManager.sqlDatabase.getCurrentSize(event.getPlayer().getUniqueId());
        // Notify the player of successful residence creation and remaining blocks
        event.getPlayer().sendMessage(Component.text("您還有 " + remainingBlocks + " 保護區額度").color(TextColor.fromHexString("#42FA3E")));
    }

}
