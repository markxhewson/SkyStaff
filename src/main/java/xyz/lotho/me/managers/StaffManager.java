package xyz.lotho.me.managers;

import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.lotho.me.SkyStaff;
import xyz.lotho.me.utils.Time;

import java.util.ArrayList;
import java.util.HashMap;

public class StaffManager {

    SkyStaff instance;
    String staffPermission;

    public HashMap<ProxiedPlayer, Long> loginTimes = new HashMap<>();
    public ArrayList<ProxiedPlayer> hiddenStaff = new ArrayList<>();

    public StaffManager(SkyStaff instance) {
        this.instance = instance;
        this.staffPermission = this.instance.config.getConfig().getString("utils.staffPermission");
    }

    public void addLoginTime(ProxiedPlayer player) {
        this.loginTimes.put(player, System.currentTimeMillis());
    }

    public void removeLoginTime(ProxiedPlayer player) {
        this.loginTimes.remove(player);
    }

    public long getLoginTimeRaw(ProxiedPlayer player) {
        return this.loginTimes.get(player);
    }

    public void hideStaff(ProxiedPlayer player) {
        this.hiddenStaff.add(player);
    }

    public boolean isHidden(ProxiedPlayer player) {
        return this.hiddenStaff.contains(player);
    }

    public void unhideStaff(ProxiedPlayer player) {
        this.hiddenStaff.remove(player);
    }

    public String getLoginTimeFormatted(ProxiedPlayer player) {
        return Time.format(System.currentTimeMillis() - this.loginTimes.get(player));
    }

    public String getStaffPrefix(ProxiedPlayer player) {
        User user = this.instance.luckPermsAPI.getUserManager().getUser(player.getUniqueId());

        assert user != null;
        return user.getCachedData().getMetaData().getPrefix();
    }

    public HashMap<String, ArrayList<ProxiedPlayer>> getServerStaff() {
        HashMap<String, ArrayList<ProxiedPlayer>> serverStaff = new HashMap<>();

        this.instance.config.getConfig().getStringList("servers").forEach((serverName) -> {
            ServerInfo server = this.instance.getProxy().getServerInfo(serverName);

            if (server == null) return;
            if (server.getPlayers().stream().noneMatch((player) -> player.hasPermission(this.staffPermission)
                    && !this.instance.staffManager.isHidden(player)
                    ) && !this.instance.config.getConfig().getBoolean("utils.showServersWithoutStaff")) return;

            serverStaff.put(server.getName(), new ArrayList<>());
            server.getPlayers().stream().filter((player) -> player.hasPermission(this.staffPermission)).forEach((player) -> {
                if (this.instance.staffManager.isHidden(player)) return;

                serverStaff.get(server.getName()).add(player);
            });
        });

        return serverStaff;
    }
}
