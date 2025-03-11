/*
 *
 *  * WorldMISF - cms of mc-serverworld
 *  * Copyright (C) 2019-2020 mc-serverworld
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.chiliasmstudio.worldResidenceManager.Listeners;

import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import com.chiliasmstudio.worldResidenceManager.EconomyIO;
import com.chiliasmstudio.worldResidenceManager.WorldResidenceManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ResidenceCreation implements Listener {

    private final WorldResidenceManager worldResidenceManager;
    public ResidenceCreation(WorldResidenceManager worldResidenceManager) {
        this.worldResidenceManager = worldResidenceManager;
    }

    @EventHandler
    public void onResidenceCreationEvent(ResidenceCreationEvent event) {
        if (event.isCancelled())
            return;
        long max = worldResidenceManager.sqlDatabase.getMaxSize(event.getPlayer().getUniqueId());
        long current = worldResidenceManager.sqlDatabase.getCurrentSize(event.getPlayer().getUniqueId());
        final long newSize = event.getResidence().getXZSize();
        final long freeSize = 2500;
        final double costPerBlock = 1;

        if (current + newSize > max) {
            event.getPlayer().sendMessage(Component.text("超過您的保護區格數上限").color(TextColor.fromHexString("#FA523E")));
            event.setCancelled(true);
            return;
        }

        double cost = 0;
        // If the player has already used up the free blocks, charge for all new blocks
        if (current >= freeSize) {
            cost = newSize * costPerBlock;
        }
        // If the player still has some free blocks left, only charge for the excess
        else if (current + newSize > freeSize) {
            long paidBlocks = (current + newSize) - freeSize;
            cost = paidBlocks * costPerBlock;
        }

        // If there is a cost, check if the player has enough balance
        if (cost > 0) {
            if (!EconomyIO.hasBlance(event.getPlayer(), cost)) {
                event.getPlayer().sendMessage(Component.text("餘額不足, 無法購買額外保護區格數").color(TextColor.fromHexString("#FA523E")));
                event.setCancelled(true);
                return;
            }
            // Deduct the amount from the player's balance
            EconomyIO.takeBalance(event.getPlayer(), cost);
            event.getPlayer().sendMessage(Component.text("您已支付 " + cost + " 元來購買額外保護區格數").color(TextColor.fromHexString("#42FA3E")));
        }

        // Notify the player of successful residence creation
        long remainingBlocks = max - (current + newSize);

        // Notify the player of successful residence creation and remaining blocks
        event.getPlayer().sendMessage(Component.text("保護區創建成功! 您還有 " + remainingBlocks + " 保護區額度").color(TextColor.fromHexString("#42FA3E")));
    }

}
