package xyz.lotho.me.managers;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.lotho.me.SkyStaff;
import xyz.lotho.me.utils.Chat;

public class AlertsManager implements Listener {

    SkyStaff instance;
    String staffPermission;

    public AlertsManager(SkyStaff instance) {
        this.instance = instance;
        this.staffPermission = this.instance.config.getConfig().getString("utils.staffPermission");
    }


    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        this.instance.staffManager.addLoginTime(event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        this.instance.staffManager.removeLoginTime(player);

        if (this.instance.staffManager.isAlertsToggled(player)) return;

        String message = this.instance.config.getConfig().getString("staffAlerts.disconnected")
                .replace("{player}", player.getDisplayName())
                .replace("{server}", player.getServer().getInfo().getName());

        this.instance.getProxy().getPlayers().stream().filter((p) -> p.hasPermission(this.staffPermission)).forEach((p) -> {
            p.sendMessage(TextComponent.fromLegacyText(Chat.colorize(message)));
        });
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission(this.staffPermission)) return;
        if (this.instance.staffManager.isAlertsToggled(player)) return;

        String newServer = player.getServer().getInfo().getName();
        String oldServer;

        try {
            oldServer = event.getFrom().getName();
        } catch (NullPointerException exception) {
            oldServer = null;
        }

        String finalOldServer = oldServer;
        this.instance.getProxy().getPlayers().stream().filter((p) -> p.hasPermission(this.staffPermission)).forEach((p) -> {
            if (finalOldServer == null) {
                String message = this.instance.config.getConfig().getString("staffAlerts.connected")
                        .replace("{player}", player.getDisplayName())
                        .replace("{server}", player.getServer().getInfo().getName());

                p.sendMessage(TextComponent.fromLegacyText(Chat.colorize(message)));
            } else {
                String message = this.instance.config.getConfig().getString("staffAlerts.switched")
                        .replace("{player}", player.getDisplayName())
                        .replace("{oldServer}", finalOldServer)
                        .replace("{newServer}", newServer);

                p.sendMessage(TextComponent.fromLegacyText(Chat.colorize(message)));
            }
        });
    }
}
