package xyz.lotho.me.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.lotho.me.SkyStaff;
import xyz.lotho.me.utils.Chat;

public class StaffChatCommand extends Command {

    SkyStaff instance;
    String staffPermission;

    public StaffChatCommand(SkyStaff instance) {
        super("sc", "", "staffchat");

        this.instance = instance;
        this.staffPermission = instance.config.getConfig().getString("utils.staffPermission");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (!player.hasPermission(this.staffPermission)) {
            player.sendMessage(TextComponent.fromLegacyText(Chat.colorize(this.instance.config.getConfig().getString("utils.noPerm"))));
            return;
        };

        if (args.length == 0) {
            player.sendMessage(TextComponent.fromLegacyText(Chat.colorize(this.instance.config.getConfig().getString("staffChat.invalid"))));
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (String part : args) builder.append(part).append(" ");

        this.instance.getProxy().getPlayers().stream().filter((p) -> p.hasPermission(this.staffPermission)).forEach((p) -> {
            String message = this.instance.config.getConfig().getString("staffChat.format")
                    .replace("{player}", player.getDisplayName())
                    .replace("{server}", player.getServer().getInfo().getName())
                    .replace("{content}", builder.toString().trim())
                    .replace("{rank}", this.instance.staffManager.getStaffPrefix(player));

            p.sendMessage(TextComponent.fromLegacyText(Chat.colorize(message)));
        });
    }
}
