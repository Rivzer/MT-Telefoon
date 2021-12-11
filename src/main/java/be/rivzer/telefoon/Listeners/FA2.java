package be.rivzer.telefoon.Listeners;

import be.rivzer.tweefaplus.API_MANAGER.EnabledEvent;
import be.rivzer.tweefaplus.API_MANAGER.SuccesEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;

public class FA2 implements Listener {

    public static HashMap<UUID, Boolean> map = new HashMap<UUID, Boolean>();

    @EventHandler
    public void on2fa(EnabledEvent e){
        Player p = e.getPlayer();
        map.put(p.getUniqueId(), e.getEnabled());
    }

    @EventHandler
    public void on2fa(SuccesEvent e){
        Player p = e.getPlayer();

        if(map.containsKey(p.getUniqueId())){
            if(map.get(p.getUniqueId()) == true){
                if(e.getSuccses() == true){
                    OnJoin.onJoin(p);
                }
            }
            else {
                OnJoin.onJoin(p);
            }
        }
    }

}
