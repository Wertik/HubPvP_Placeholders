package space.devport.wertik.hubswordplaceholders;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import sv.file14.hubpvpsword.api.event.PlayerEnterPvPModeEvent;
import sv.file14.hubpvpsword.api.event.PlayerExitPvPModeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HubPvPCache implements Listener {

    public List<String> playerCache = new ArrayList<>();

    public List<String> getCache() {
        return playerCache;
    }

    public boolean isInPvP(UUID uniqueID) {
        return playerCache.contains(uniqueID.toString());
    }

    public boolean isInPvP(Player player) {
        return playerCache.contains(player.getUniqueId().toString());
    }

    @EventHandler
    public void onEnter(PlayerEnterPvPModeEvent e) {
        playerCache.add(e.getPlayer().getUniqueId().toString());

        Main.inst.cO.debug("Added " + e.getPlayer().getUniqueId().toString() + " to pvp cache.");
    }

    @EventHandler
    public void onExit(PlayerExitPvPModeEvent e) {
        playerCache.remove(e.getPlayer().getUniqueId().toString());

        Main.inst.cO.debug("Removed " + e.getPlayer().getUniqueId().toString() + " from pvp cache.");
    }
}
