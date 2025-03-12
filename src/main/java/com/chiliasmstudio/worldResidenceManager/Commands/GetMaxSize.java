package com.chiliasmstudio.worldResidenceManager.Commands;

import com.chiliasmstudio.worldResidenceManager.WorldResidenceManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetMaxSize {
    public static int getMaxSize(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSender sender = ctx.getSource().getSender(); // Retrieve the command sender
        final PlayerSelectorArgumentResolver targetResolver = ctx.getArgument("target_palyer", PlayerSelectorArgumentResolver.class);
        final Player target = targetResolver.resolve(ctx.getSource()).getFirst();

        if (!sender.hasPermission("WorldResidenceManager.get")) {
            sender.sendRichMessage("<red>no permission</red>");
            return 0;
        }

        sender.sendRichMessage(target.getName()+ " maxsize now is: " + WorldResidenceManager.worldResidenceManager.sqlDatabase.getMaxSize(target.getUniqueId()));
        return Command.SINGLE_SUCCESS;
    }
}
