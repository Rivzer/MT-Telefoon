package be.rivzer.telefoon.Listeners;

import be.rivzer.telefoon.Config.Config;
import be.rivzer.telefoon.Inventorys.Telefoon;
import be.rivzer.telefoon.Logger;
import be.rivzer.telefoon.Main;
import be.rivzer.tweefaplus.API_MANAGER.EnabledEvent;
import be.rivzer.tweefaplus.API_MANAGER.SuccesEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

public class OpenTelefoon implements Listener {

    private Main plugin = (Main) Main.getPlugin(Main.class);

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e){
        Player p = e.getPlayer();

        if(e.getItem() == null || p == null) return;
        if(p.getInventory().getItemInMainHand() == null) return;
        if(p.getInventory().getItemInMainHand().getItemMeta() == null) return;
        if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() == null) return;
        if(!p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Nummer")) && !p.getInventory().getItemInMainHand().getType().equals(Material.BLAZE_POWDER)) return;
        if(!(e.getAction() == Action.RIGHT_CLICK_AIR) && !(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        if (Bukkit.getPluginManager().getPlugin("2FA-Plus") != null) {
            if(Bukkit.getPluginManager().getPlugin("2FA-Plus").isEnabled()){
                if(p.hasPermission("2fa.use")){
                    if(FA.map2.containsKey(p.getUniqueId())){
                        if(FA.map2.get(p.getUniqueId()) == true){
                            if(FA.map.containsKey(p.getUniqueId())){
                                if(FA.map.get(p.getUniqueId()) == true){
                                    if(InventoryClick.isOpstarten(p) == true){
                                        p.sendMessage(Logger.color(Main.Prefix() + "&cU telefoon is aan het opstarten!"));
                                        return;
                                    }

                                    p.sendMessage(Logger.color(Main.Prefix() + "&7Uw telefoon wordt geopend."));

                                    p.closeInventory();
                                    Telefoon.OpenTelefoon(p);
                                }
                            }
                        }
                        else {
                            if(InventoryClick.isOpstarten(p) == true){
                                p.sendMessage(Logger.color(Main.Prefix() + "&cU telefoon is aan het opstarten!"));
                                return;
                            }

                            p.sendMessage(Logger.color(Main.Prefix() + "&7Uw telefoon wordt geopend."));

                            p.closeInventory();
                            Telefoon.OpenTelefoon(p);
                        }
                    }
                }
                else{
                    if(InventoryClick.isOpstarten(p) == true){
                        p.sendMessage(Logger.color(Main.Prefix() + "&cU telefoon is aan het opstarten!"));
                        return;
                    }

                    p.sendMessage(Logger.color(Main.Prefix() + "&7Uw telefoon wordt geopend."));

                    p.closeInventory();
                    Telefoon.OpenTelefoon(p);
                }
            }
            else{
                if(InventoryClick.isOpstarten(p) == true){
                    p.sendMessage(Logger.color(Main.Prefix() + "&cU telefoon is aan het opstarten!"));
                    return;
                }

                p.sendMessage(Logger.color(Main.Prefix() + "&7Uw telefoon wordt geopend."));

                p.closeInventory();
                Telefoon.OpenTelefoon(p);
            }
        }
        else{
            if(InventoryClick.isOpstarten(p) == true){
                p.sendMessage(Logger.color(Main.Prefix() + "&cU telefoon is aan het opstarten!"));
                return;
            }

            p.sendMessage(Logger.color(Main.Prefix() + "&7Uw telefoon wordt geopend."));

            p.closeInventory();
            Telefoon.OpenTelefoon(p);
        }
    }
}
