package space.devport.wertik.hubswordplaceholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class HubPvPPlaceholders extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "pvphubsword";
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

        if (params.equalsIgnoreCase("status")) {
            return Main.inst.getPvpCache().isInPvP(p.getUniqueId()) ? Main.inst.getConfig().getColored("true") : Main.inst.getConfig().getColored("false");
        } else if (params.equalsIgnoreCase("kills")) {
            return String.valueOf(Main.inst.getStatisticCache().get(p.getUniqueId()).getKills());
        } else if (params.equalsIgnoreCase("deaths")) {
            return String.valueOf(Main.inst.getStatisticCache().get(p.getUniqueId()).getDeaths());
        }

        return null;
    }
}
