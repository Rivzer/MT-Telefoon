package be.rivzer.telefoon.Listeners;

import be.rivzer.tweefaplus.API_MANAGER.EnabledEvent;
import be.rivzer.tweefaplus.API_MANAGER.SuccesEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;

public class FA1 implements Listener {

    public static HashMap<UUID, Boolean> map = new HashMap<UUID, Boolean>();
    public static HashMap<UUID, Boolean> map2 = new HashMap<UUID, Boolean>();

    @EventHandler
    public void on2fa(SuccesEvent e){
        Player p = e.getPlayer();
        map.put(p.getUniqueId(), e.getSuccses());
    }

    @EventHandler
    public void on2fa(EnabledEvent e){
        Player p = e.getPlayer();
        map2.put(p.getUniqueId(), e.getEnabled());
    }

}
