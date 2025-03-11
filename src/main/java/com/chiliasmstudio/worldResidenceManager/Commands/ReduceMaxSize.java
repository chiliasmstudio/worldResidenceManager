package com.chiliasmstudio.worldResidenceManager.Commands;

import com.chiliasmstudio.worldResidenceManager.WorldResidenceManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReduceMaxSize {

    public static int reduceMaxSize(CommandContext<CommandSourceStack> ctx) {
        long maxsize = LongArgumentType.getLong(ctx, "maxsize"); // Retrieve the speed argument
        CommandSender sender = ctx.getSource().getSender(); // Retrieve the command sender
        if (!sender.hasPermission("WorldResidenceManager.AddMaxSize")) {
            sender.sendRichMessage("<red>no permission</red>");
            return 0;
        } else if (!(sender instanceof Player)) {
            sender.sendRichMessage("<red>Only players can do this</red>");
            return 0;
        }
        sender.sendRichMessage("<green>Goodbye world");
        WorldResidenceManager.worldResidenceManager.sqlDatabase.reduceMaxSize(((Player) sender).getUniqueId(),maxsize);
        sender.sendRichMessage(String.valueOf(WorldResidenceManager.worldResidenceManager.sqlDatabase.getMaxSize(((Player) sender).getUniqueId())));
        return Command.SINGLE_SUCCESS;
    }
}
