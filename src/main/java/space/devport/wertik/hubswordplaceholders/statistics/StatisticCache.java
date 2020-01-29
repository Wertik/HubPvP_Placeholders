package space.devport.wertik.hubswordplaceholders.statistics;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import space.devport.wertik.hubswordplaceholders.Main;
import space.devport.wertik.hubswordplaceholders.util.Configuration;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.UUID;

public class StatisticCache {

    private HashMap<String, StatisticRecord> statistics = new HashMap<>();
    private TreeSet<StatisticRecord> orderedByKills = new TreeSet<>(new Comparator<StatisticRecord>() {
        @Override
        public int compare(StatisticRecord o1, StatisticRecord o2) {
            return Integer.compare(o2.getKills(), o1.getKills());
        }
    });

    private TreeSet<StatisticRecord> orderedByDeaths = new TreeSet<>(new Comparator<StatisticRecord>() {
        @Override
        public int compare(StatisticRecord o1, StatisticRecord o2) {
            return Integer.compare(o1.getDeaths(), o2.getDeaths());
        }
    });

    private void recalculate() {
        orderedByDeaths.clear();
        orderedByKills.clear();

        orderedByDeaths.addAll(statistics.values());
        orderedByKills.addAll(statistics.values());
    }

    public StatisticRecord getByKills(int pos) {
        int n = 0;

        for (StatisticRecord record : orderedByKills) {
            n++;
            if (n == pos)
                return record;
        }

        return null;
    }

    public StatisticRecord getByDeaths(int pos) {
        int n = 0;

        for (StatisticRecord record : orderedByDeaths) {
            n++;
            if (n == pos)
                return record;
        }

        return null;
    }

    private Configuration storage;

    public StatisticCache() {
        storage = new Configuration(Main.inst, "storage");
    }

    public StatisticRecord get(OfflinePlayer p) {
        return statistics.getOrDefault(p.getUniqueId().toString(), new StatisticRecord(p.getName()));
    }

    public void set(UUID uniqueID, StatisticRecord record) {
        statistics.put(uniqueID.toString(), record);

        recalculate();
    }

    public void load() {
        statistics.clear();
        storage.reload();

        for (String uniqueID : storage.getYaml().getKeys(false)) {
            int kills = storage.getYaml().getInt(uniqueID + ".kills", 0);
            int deaths = storage.getYaml().getInt(uniqueID + ".deaths", 0);

            OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(uniqueID));

            // Remove if he doesn't exist anymore.
            if (op == null)
                continue;

            statistics.put(uniqueID, new StatisticRecord(UUID.fromString(uniqueID), kills, deaths));
        }

        orderedByDeaths.addAll(statistics.values());
        orderedByKills.addAll(statistics.values());
    }

    public void save() {
        storage.clear();

        for (String uniqueID : statistics.keySet()) {
            ConfigurationSection section = storage.getYaml().createSection(uniqueID);

            StatisticRecord record = statistics.get(uniqueID);

            if (record.getKills() == 0 && record.getDeaths() == 0)
                continue;

            section.set("kills", record.getKills());
            section.set("deaths", record.getDeaths());
            section.set("name", record.getAssociatedName());
        }

        storage.save();
    }
}
