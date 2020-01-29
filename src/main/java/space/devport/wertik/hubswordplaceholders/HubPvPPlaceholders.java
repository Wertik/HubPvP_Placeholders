package space.devport.wertik.hubswordplaceholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import space.devport.wertik.hubswordplaceholders.statistics.StatisticRecord;

public class HubPvPPlaceholders extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "hubpvpsword";
    }

    @Override
    public String getAuthor() {
        return "Wertik1206";
    }

    @Override
    public String getVersion() {
        return Main.inst.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        if (p == null)
            return "";

        // %pvphubsword_kills/deaths_1_name/num%
        // %pvphubsword_status/kills/deaths%

        String[] arr = params.split("_");

        if (arr.length == 1) {
            if (params.equalsIgnoreCase("status")) {
                return Main.inst.getPvpCache().isInPvP(p.getUniqueId()) ? Main.inst.getCfg().getColored("true") : Main.inst.getCfg().getColored("false");
            } else if (params.equalsIgnoreCase("kills")) {
                return String.valueOf(Main.inst.getStatisticCache().get(p).getKills());
            } else if (params.equalsIgnoreCase("deaths")) {
                return String.valueOf(Main.inst.getStatisticCache().get(p).getDeaths());
            } else return "err_not_an_option";
        } else if (arr.length == 3) {
            StatisticRecord record;
            int pos;

            try {
                pos = Integer.parseInt(arr[1]);
            } catch (NumberFormatException e) {
                return "err_not_a_number";
            }

            if (pos == 0)
                pos += 1;

            if (arr[0].equalsIgnoreCase("kills"))
                record = Main.inst.getStatisticCache().getByKills(pos);
            else if (arr[0].equalsIgnoreCase("deaths"))
                record = Main.inst.getStatisticCache().getByDeaths(pos);
            else
                return "err_not_an_option";

            if (record == null)
                return Main.inst.getCfg().getColored("no-record");

            if (arr[2].equalsIgnoreCase("name"))
                return record.getAssociatedName();
            else if (arr[2].equalsIgnoreCase("num"))
                if (arr[0].equalsIgnoreCase("kills"))
                    return String.valueOf(record.getKills());
                else if (arr[0].equalsIgnoreCase("deaths"))
                    return String.valueOf(record.getDeaths());
                else return "err";
            else if (arr[2].equalsIgnoreCase("opnum"))
                if (arr[0].equalsIgnoreCase("kills"))
                    return String.valueOf(record.getDeaths());
                else if (arr[0].equalsIgnoreCase("deaths"))
                    return String.valueOf(record.getKills());
                else return "err";
        }

        return null;
    }
}
