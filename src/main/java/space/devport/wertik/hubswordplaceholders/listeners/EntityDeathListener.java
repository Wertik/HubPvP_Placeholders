package space.devport.wertik.hubswordplaceholders.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import space.devport.wertik.hubswordplaceholders.Main;
import space.devport.wertik.hubswordplaceholders.statistics.StatisticRecord;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        if (player.getKiller() != null) {
            Player killer = player.getKiller();

            StatisticRecord playerRecord = Main.inst.getStatisticCache().get(player.getUniqueId());
            StatisticRecord killerRecord = Main.inst.getStatisticCache().get(killer.getUniqueId());

            playerRecord.addDeath();
            killerRecord.addKill();

            Main.inst.getStatisticCache().set(player.getUniqueId(), playerRecord);
            Main.inst.getStatisticCache().set(killer.getUniqueId(), killerRecord);

            Main.inst.cO.debug("Updated " + player.getName() + "'s deaths to " + playerRecord.getDeaths());
            Main.inst.cO.debug("Updated " + player.getName() + "'s kills to " + killerRecord.getKills());
        }
    }
}
