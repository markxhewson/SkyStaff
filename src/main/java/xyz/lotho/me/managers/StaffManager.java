package xyz.lotho.me.managers;

import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.lotho.me.SkyStaff;
import xyz.lotho.me.utils.Time;

import java.util.HashMap;

public class StaffManager {

    SkyStaff instance;

    public HashMap<ProxiedPlayer, Long> loginTimes = new HashMap<>();

    public StaffManager(SkyStaff instance) {
        this.instance = instance;
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

    public String getLoginTimeFormatted(ProxiedPlayer player) {
        return Time.format(System.currentTimeMillis() - this.loginTimes.get(player));
    }

    public String getStaffPrefix(ProxiedPlayer player) {
        User user = this.instance.luckPermsAPI.getUserManager().getUser(player.getUniqueId());

        assert user != null;
        return user.getCachedData().getMetaData().getPrefix();
    }
}
