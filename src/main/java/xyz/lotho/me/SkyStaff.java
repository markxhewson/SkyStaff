package xyz.lotho.me;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import xyz.lotho.me.commands.StaffCommand;
import xyz.lotho.me.managers.StaffManager;
import xyz.lotho.me.utils.Config;

public final class SkyStaff extends Plugin implements Listener {

    public Config config = new Config(this, "config.yml");
    public StaffManager staffManager = new StaffManager(this);

    public LuckPerms luckPermsAPI;

    @Override
    public void onEnable() {
        this.getProxy().getPluginManager().registerCommand(this, new StaffCommand(this));
        this.getProxy().getPluginManager().registerListener(this, this);

        luckPermsAPI = LuckPermsProvider.get();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        this.staffManager.addLoginTime(event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event) {
        this.staffManager.removeLoginTime(event.getPlayer());
    }
}
