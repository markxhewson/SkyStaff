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
        StringBuilder stringBuilder = new StringBuilder();

        String permission = this.instance.config.getConfig().getString("utils.staffPermission");
        String header = this.instance.config.getConfig().getString("messages.header")
                        .replace("{players}", String.valueOf(this.instance.getProxy().getPlayers().size()));

        stringBuilder.append(Chat.colorize("\n" + header + "\n\n"));

        this.instance.config.getConfig().getStringList("servers").forEach((serverName) -> {
            ServerInfo server = this.instance.getProxy().getServerInfo(serverName);

            if (server == null) return;
            if (server.getPlayers().stream().noneMatch((player) -> player.hasPermission(permission)) && !this.instance.config.getConfig().getBoolean("utils.showServersWithoutStaff")) return;

            Collection<ProxiedPlayer> players = server.getPlayers();

            String serverLine = this.instance.config.getConfig().getString("messages.serverLine")
                            .replace("{server}", server.getName())
                            .replace("{players}", String.valueOf(players.size()));

            stringBuilder.append(Chat.colorize(serverLine));

            if (!players.isEmpty() && server.getPlayers().stream().anyMatch((player) -> player.hasPermission(permission))) {
                players.forEach((player) -> {
                    if (player.hasPermission(permission)) {
                        String staffLine = this.instance.config.getConfig().getString("messages.staffLine")
                                .replace("{prefix}", this.instance.staffManager.getStaffPrefix(player))
                                .replace("{player}", player.getDisplayName())
                                .replace("{time}", this.instance.staffManager.getLoginTimeFormatted(player));

                        stringBuilder.append("\n").append(Chat.colorize(staffLine));
                    }
                });
                stringBuilder.append("\n");
            } else {
                stringBuilder.append(Chat.colorize("\n" + this.instance.config.getConfig().getString("messages.noStaffLine"))).append("\n");
            }
            stringBuilder.append("\n");
        });

        commandSender.sendMessage(new TextComponent(stringBuilder.toString()));
    }
};

