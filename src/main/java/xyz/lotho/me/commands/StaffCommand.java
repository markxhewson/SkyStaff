package xyz.lotho.me.commands;

import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.lotho.me.SkyStaff;
import xyz.lotho.me.utils.Chat;

import java.util.*;

public class StaffCommand extends Command {

    SkyStaff instance;

    public StaffCommand(SkyStaff instance) {
        super("staff", "", "slist");

        this.instance = instance;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) commandSender;

        if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("hide")) {
                if (this.instance.staffManager.isHidden(player)) {
                    this.instance.staffManager.unhideStaff(player);
                    player.sendMessage(new TextComponent(Chat.colorize(this.instance.config.getConfig().getString("messages.hideLine").replace("{mode}", "enabled"))));
                } else {
                    this.instance.staffManager.hideStaff(player);
                    player.sendMessage(new TextComponent(Chat.colorize(this.instance.config.getConfig().getString("messages.hideLine").replace("{mode}", "disabled"))));
                }
            }
        } else {
            StringBuilder builder = new StringBuilder();

            String permission = this.instance.config.getConfig().getString("utils.staffPermission");
            String header = this.instance.config.getConfig().getString("messages.header").replace("{players}", String.valueOf(this.instance.getProxy().getPlayers().size()));

            builder.append("\n").append(Chat.colorize(header)).append("\n");
            this.instance.staffManager.getServerStaff().forEach((serverName, staff) -> {
                ServerInfo server = this.instance.getProxy().getServerInfo(serverName);

                if (server == null) return;
                if (server.getPlayers().stream().noneMatch((user) -> user.hasPermission(permission)) && !this.instance.config.getConfig().getBoolean("utils.showServersWithoutStaff")) return;

                Collection<ProxiedPlayer> players = server.getPlayers();

                String serverLine = this.instance.config.getConfig().getString("messages.serverLine").replace("{server}", server.getName()).replace("{players}", String.valueOf(players.size()));
                builder.append("\n").append(Chat.colorize(serverLine));

                if (staff.size() == 0) {
                    builder.append("\n").append(Chat.colorize(this.instance.config.getConfig().getString("messages.noStaffLine"))).append("\n");
                } else {
                    staff.forEach((staffMember) -> {
                        String staffLine = this.instance.config.getConfig().getString("messages.staffLine")
                                .replace("{prefix}", this.instance.staffManager.getStaffPrefix(staffMember))
                                .replace("{player}", staffMember.getDisplayName())
                                .replace("{time}", this.instance.staffManager.getLoginTimeFormatted(staffMember));

                        builder.append(Chat.colorize("\n" + staffLine));
                    });
                    builder.append("\n");
                }
            });
            player.sendMessage(new TextComponent(builder.toString()));
        }
    }
};

