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

public class AddCurrentSize {

    public static int addCurrentSize(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        long maxsize = LongArgumentType.getLong(ctx, "maxsize"); // Retrieve the speed argument
        CommandSender sender = ctx.getSource().getSender(); // Retrieve the command sender
        final PlayerSelectorArgumentResolver targetResolver = ctx.getArgument("target_palyer", PlayerSelectorArgumentResolver.class);
        final Player target = targetResolver.resolve(ctx.getSource()).getFirst();

        if (!sender.hasPermission("WorldResidenceManager.add")) {
            sender.sendRichMessage("<red>no permission</red>");
            return 0;
        }

        WorldResidenceManager.worldResidenceManager.sqlDatabase.addCurrentSize(target.getUniqueId(),maxsize);
        sender.sendRichMessage(target.getName()+ " current size now is: " + WorldResidenceManager.worldResidenceManager.sqlDatabase.getCurrentSize(target.getUniqueId()));
        return Command.SINGLE_SUCCESS;
    }
}
