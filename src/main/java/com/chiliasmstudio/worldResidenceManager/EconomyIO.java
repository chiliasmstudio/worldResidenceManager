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

package com.chiliasmstudio.worldResidenceManager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyIO {

    private static Economy econ = null;

    public EconomyIO(WorldResidenceManager worldResidenceManager){
        RegisteredServiceProvider<Economy> economyProvider = worldResidenceManager.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }else worldResidenceManager.getComponentLogger().error(
                Component.text("Missing EconmyAPI!").color(TextColor.color(255,0,0)));
    }

    public static Boolean addBalance(Player player,Double bal){
        EconomyResponse r = econ.depositPlayer(player, bal);
        if(r.transactionSuccess())
            return true;
        player.sendMessage(ChatColor.RED + "ErrorMessage: " + r.errorMessage);
        return false;
    }
    public static Boolean takeBalance(Player player,Double bal){
        if(!econ.has(player,bal))
            return false;
        EconomyResponse r = econ.withdrawPlayer(player, bal);
        if(r.transactionSuccess())
            return true;
        player.sendMessage(ChatColor.RED + "ErrorMessage: " + r.errorMessage);
        return false;
    }
    public static Double getBalance(Player player){
        return econ.getBalance(player);
    }
    public static Boolean hasBlance(Player player,Double bal){
        return econ.has(player, bal);
    }
}
