package com.chiliasmstudio.worldResidenceManager.Commands;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.chiliasmstudio.worldResidenceManager.EconomyIO;
import com.chiliasmstudio.worldResidenceManager.SqlDatabase;
import com.chiliasmstudio.worldResidenceManager.WorldResidenceManager;
import com.chiliasmstudio.worldResidenceManager.WorldResidenceManagerConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class AddMaxSize {

    public static int addMaxSize(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        long maxsize = LongArgumentType.getLong(ctx, "maxsize"); // Retrieve the speed argument
        CommandSender sender = ctx.getSource().getSender(); // Retrieve the command sender
        final PlayerSelectorArgumentResolver targetResolver = ctx.getArgument("target_palyer", PlayerSelectorArgumentResolver.class);
        final Player target = targetResolver.resolve(ctx.getSource()).getFirst();

        if (!sender.hasPermission("WorldResidenceManager.AddMaxSize")) {
            sender.sendRichMessage("<red>no permission</red>");
            return 0;
        } else if (!(sender instanceof Player)) {
            sender.sendRichMessage("<red>Only players can do this</red>");
            return 0;
        }
        WorldResidenceManager.worldResidenceManager.sqlDatabase.addMaxSize(target.getUniqueId(),maxsize);
        sender.sendRichMessage(String.valueOf(WorldResidenceManager.worldResidenceManager.sqlDatabase.getMaxSize(target.getUniqueId())));
        return Command.SINGLE_SUCCESS;
    }
}
