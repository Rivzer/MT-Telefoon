package be.rivzer.telefoon.Listeners;

import be.rivzer.tweefaplus.API_MANAGER.EnabledEvent;
import be.rivzer.tweefaplus.API_MANAGER.SuccesEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;

public class FA implements Listener {

    public static HashMap<UUID, Boolean> map = new HashMap<UUID, Boolean>();
    public static HashMap<UUID, Boolean> map2 = new HashMap<UUID, Boolean>();

    @EventHandler
    public void EnabledEvent(EnabledEvent e){
        map2.put(e.getPlayer().getUniqueId(), e.getEnabled());
    }

    @EventHandler
    public void SuccesEvent(SuccesEvent e){
        Player p = e.getPlayer();
        if(map2.containsKey(p.getUniqueId())){
            if(map2.get(p.getUniqueId()) == true){
                if(e.getSuccses() == true){
                    map.put(p.getUniqueId(), true);
                }
            }
            else {
                map.put(p.getUniqueId(), true);
            }
        }
        else {
            map.put(p.getUniqueId(), true);
        }
    }

}
