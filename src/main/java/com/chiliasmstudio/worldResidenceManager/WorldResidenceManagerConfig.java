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
public class WorldResidenceManagerConfig {
    private WorldResidenceManager plugin;
    public WorldResidenceManagerConfig(WorldResidenceManager i){
        plugin = i;
    }
    public void loadDefConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
    }
    public int ConfigVersion() { return plugin.getConfig().getInt("configinfo.config-version"); }
    public Boolean debug() { return plugin.getConfig().getBoolean("configinfo.debug"); }

    public String Host() {
        return plugin.getConfig().getString("database.host");
    }
    public int Port() {
        return plugin.getConfig().getInt("database.port");
    }
    public String Database() {
        return plugin.getConfig().getString("database.database");
    }
    public String Username() {
        return plugin.getConfig().getString("database.username");
    }
    public String Password() {
        return plugin.getConfig().getString("database.password");
    }


}
