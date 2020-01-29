package space.devport.wertik.hubswordplaceholders.statistics;

import org.bukkit.configuration.ConfigurationSection;
import space.devport.wertik.hubswordplaceholders.util.Configuration;
import space.devport.wertik.hubswordplaceholders.Main;

import java.util.HashMap;
import java.util.UUID;

public class StatisticCache {

    private HashMap<String, StatisticRecord> statistics = new HashMap<>();
    private Configuration storage;

    public StatisticCache() {
        storage = new Configuration(Main.inst, "storage");
    }

    public StatisticRecord get(UUID uniqueID) {
        return statistics.getOrDefault(uniqueID.toString(), new StatisticRecord());
    }

    public void set(UUID uniqueID, StatisticRecord record) {
        statistics.put(uniqueID.toString(), record);
    }

    public void load() {
        statistics.clear();
        storage.reload();

        for (String uniqueID : storage.getYaml().getKeys(false)) {
            int kills = storage.getYaml().getInt(uniqueID + ".kills", 0);
            int deaths = storage.getYaml().getInt(uniqueID + ".deaths", 0);

            statistics.put(uniqueID, new StatisticRecord(kills, deaths));
        }
    }

    public void save() {
        storage.clear();

        for (String uniqueID : statistics.keySet()) {
            ConfigurationSection section = storage.getYaml().createSection(uniqueID);

            StatisticRecord record = statistics.get(uniqueID);

            section.set("kills", record.getKills());
            section.set("deaths", record.getDeaths());
        }

        storage.save();
    }
}
