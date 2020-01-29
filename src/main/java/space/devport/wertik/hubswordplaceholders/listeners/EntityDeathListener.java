package space.devport.wertik.hubswordplaceholders.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import space.devport.wertik.hubswordplaceholders.Main;
import space.devport.wertik.hubswordplaceholders.statistics.StatisticRecord;

public class EntityDeathListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDeath(EntityDamageByEntityEvent e) {

        if (!(e.getEntity() instanceof Player))
            return;

        if (((Player) e.getEntity()).getKiller() == null)
            return;

        if (e.getFinalDamage() < ((Player) e.getEntity()).getHealth())
            return;

        Player player = (Player) e.getEntity();

        Player killer = player.getKiller();

        if (!Main.inst.getPvpCache().isInPvP(killer) || !Main.inst.getPvpCache().isInPvP(player))
            return;

        StatisticRecord playerRecord = Main.inst.getStatisticCache().get(player);
        StatisticRecord killerRecord = Main.inst.getStatisticCache().get(killer);

        playerRecord.addDeath();
        killerRecord.addKill();

        Main.inst.getStatisticCache().set(player.getUniqueId(), playerRecord);
        Main.inst.getStatisticCache().set(killer.getUniqueId(), killerRecord);

        Main.inst.cO.debug("Updated " + player.getName() + "'s deaths to " + playerRecord.getDeaths());
        Main.inst.cO.debug("Updated " + player.getName() + "'s kills to " + killerRecord.getKills());
    }
}
