package space.devport.wertik.hubswordplaceholders.statistics;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.UUID;

public class StatisticRecord {

    @Getter
    @Setter
    private UUID uniqueID;

    @Getter
    @Setter
    private String associatedName;

    @Getter
    @Setter
    private int kills = 0;

    @Getter
    @Setter
    private int deaths = 0;

    public StatisticRecord(String name) {
        this.associatedName = name;
    }

    public StatisticRecord(UUID uniqueID, int kills, int deaths) {
        this.uniqueID = uniqueID;
        this.associatedName = Bukkit.getOfflinePlayer(uniqueID).getName();
        this.kills = kills;
        this.deaths = deaths;
    }

    public void addKill() {
        kills++;
    }

    public void addDeath() {
        deaths++;
    }
}
