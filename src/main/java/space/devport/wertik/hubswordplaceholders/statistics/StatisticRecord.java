package space.devport.wertik.hubswordplaceholders.statistics;

import lombok.Getter;
import lombok.Setter;

public class StatisticRecord {

    @Getter
    @Setter
    private int kills = 0;

    @Getter
    @Setter
    private int deaths = 0;

    public StatisticRecord() {
    }

    public StatisticRecord(int kills, int deaths) {
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
