package space.devport.wertik.hubswordplaceholders;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import space.devport.wertik.hubswordplaceholders.listeners.EntityDeathListener;
import space.devport.wertik.hubswordplaceholders.statistics.StatisticCache;
import space.devport.wertik.hubswordplaceholders.util.Configuration;
import space.devport.wertik.hubswordplaceholders.util.ConsoleOutput;

public class Main extends JavaPlugin {

    public static Main inst;

    public ConsoleOutput cO;

    private Configuration config;

    public Configuration getCfg() {
        return config;
    }

    @Getter
    private HubPvPCache pvpCache;

    @Getter
    private StatisticCache statisticCache;

    @Override
    public void onEnable() {
        inst = this;

        config = new Configuration(this, "config");

        cO = new ConsoleOutput(this);
        cO.setDebug(config.getYaml().getBoolean("debug-enabled", false));
        cO.setPrefix(config.getColored("plugin-prefix"));

        if (getServer().getPluginManager().getPlugin("HubPvPSword") == null) {
            cO.err("Cannot find HubPVPSword. Disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        pvpCache = new HubPvPCache();
        statisticCache = new StatisticCache();

        statisticCache.load();

        getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);
        getServer().getPluginManager().registerEvents(pvpCache, this);

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new HubPvPPlaceholders().register();
            cO.info("Found PAPI. &aAdding placeholders!");
        }
    }

    @Override
    public void onDisable() {
        statisticCache.save();
    }
}
