package com.chiliasmstudio.worldResidenceManager;

import com.chiliasmstudio.worldResidenceManager.Commands.AddMaxSize;
import com.chiliasmstudio.worldResidenceManager.Commands.ReduceMaxSize;
import com.chiliasmstudio.worldResidenceManager.Listeners.PlayerJoin;
import com.chiliasmstudio.worldResidenceManager.Listeners.ResidenceCreation;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class WorldResidenceManager extends JavaPlugin {

    public static WorldResidenceManager worldResidenceManager;
    public static WorldResidenceManagerConfig config;
    public SqlDatabase sqlDatabase;

    @Override
    public void onEnable() {
        worldResidenceManager = this;
        config = new WorldResidenceManagerConfig(this);
        config.loadDefConfig();
        sqlDatabase = new SqlDatabase();
        if(!sqlDatabase.connect()){
            getComponentLogger().error(Component.text("Please setup config.yml").color(TextColor.color(255, 0, 0)));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        sqlDatabase.createTableIfNotExists();
        EconomyIO economyIO = new EconomyIO(this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new ResidenceCreation(this), this);

        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("resman")
                .then(Commands.literal("addmaxsize")
                        .then(Commands.argument("target_palyer", ArgumentTypes.player())
                                .then(Commands.argument("maxsize",LongArgumentType.longArg())
                                        .executes(AddMaxSize::addMaxSize)
                )))

                .then(Commands.literal("reducemaxsize")
                        .then(Commands.argument("target_palyer", ArgumentTypes.player())
                                .executes(ReduceMaxSize::reduceMaxSize))
                );

        LiteralCommandNode<CommandSourceStack> buildCommand = command.build();
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(buildCommand);
        });

        /*
        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("resman")
                .then(Commands.literal("addmaxsize")
                        .then(Commands.argument("maxsize",LongArgumentType.longArg())
                                .executes(AddMaxSize::addMaxSize)))

                .then(Commands.literal("reducemaxsize")
                        .then(Commands.argument("maxsize",LongArgumentType.longArg())
                                .executes(ReduceMaxSize::reduceMaxSize)));

        LiteralCommandNode<CommandSourceStack> buildCommand = command.build();
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(buildCommand);
        });
        */
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        sqlDatabase.close();
    }


}
