package xyz.lotho.me.utils;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import xyz.lotho.me.SkyStaff;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Config {

    SkyStaff instance;
    Configuration configuration;
    String configName;

    public Config(SkyStaff instance, String configName) {
        this.instance = instance;
        this.configuration = null;

        create(configName);
    }

    public void create(String configName) {
        try {
            if (!this.instance.getDataFolder().exists()) {
                this.instance.getDataFolder().mkdir();
            }
            File dataFile = new File(this.instance.getDataFolder(), configName);
            if (!dataFile.exists()) {
                try (InputStream in = this.instance.getResourceAsStream(configName)) {
                    Files.copy(in, dataFile.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.instance.getDataFolder(), configName));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Configuration getConfig() {
        return this.configuration;
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.getConfig(), new File(this.instance.getDataFolder(), this.configName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
