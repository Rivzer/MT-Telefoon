package be.rivzer.telefoon.Listeners;

import be.rivzer.telefoon.Config.Config;
import be.rivzer.telefoon.Logger;
import be.rivzer.telefoon.Main;
import be.rivzer.tweefaplus.API_MANAGER.EnabledEvent;
import be.rivzer.tweefaplus.API_MANAGER.SuccesEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class OnJoin implements Listener {

    private Main plugin = (Main) Main.getPlugin(Main.class);

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (Bukkit.getPluginManager().getPlugin("2FA-Plus") != null) {
            if(Bukkit.getServer().getPluginManager().getPlugin("2FA-Plus").isEnabled()){
                if(p.hasPermission("2fa.use")){
                    if(FA2.map.containsKey(p.getUniqueId())){
                        if(FA2.map.get(p.getUniqueId()) == false){
                            onJoin(p);
                        }
                        else {
                            p.getInventory().setHeldItemSlot(8);
                            if(p.getInventory().getItemInMainHand() == null) return;
                            if(p.getInventory().getItemInMainHand().getType().equals(Material.BLAZE_POWDER)){
                                if(p.getInventory().getItemInMainHand().getItemMeta() == null) return;
                                if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() == null) return;
                                if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() == Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Nummer"));
                                p.getInventory().setItem(8, new ItemStack(Material.AIR));
                            }
                        }
                    }
                    else {
                        p.getInventory().setHeldItemSlot(8);
                        if(p.getInventory().getItemInMainHand() == null) return;
                        if(p.getInventory().getItemInMainHand().getType().equals(Material.BLAZE_POWDER)){
                            if(p.getInventory().getItemInMainHand().getItemMeta() == null) return;
                            if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() == null) return;
                            if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() == Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Nummer"));
                            p.getInventory().setItem(8, new ItemStack(Material.AIR));
                        }
                    }
                }
                else {
                    onJoin(p);
                }
            }
            else{
                onJoin(p);
            }
        }
        else{
            onJoin(p);
        }
    }

    public static void onJoin(Player p){
        List<String> list = Config.getCustomConfig2().getStringList("UUIDList");
        List<String> nummers = Config.getCustomConfig2().getStringList("Nummers");

        if(Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Nummer") == null){

            String nummer = "06-";
            boolean error = false;

            for (int i = 0; i <= 6; i++){
                int randomNummerGen = new Random().nextInt(9);
                nummer = nummer + randomNummerGen;
            }

            if(nummers.contains(nummer)){
                error = true;
            }

            while(error == true){
                nummer = "06-";
                for (int i = 0; i <= 6; i++){
                    int randomNummerGen = new Random().nextInt(9);
                    nummer = nummer + randomNummerGen;
                }

                if(!nummers.contains(nummer)){
                    error = false;
                }
            }

            Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".Nummer", nummer);
            Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".UserName", p.getName());
            Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".Power", false);
            list.add(String.valueOf(p.getUniqueId()));
            nummers.add(nummer);
            Config.getCustomConfig2().set("UUIDList", list);
            Config.getCustomConfig2().set("Nummers", nummers);
            Config.saveConfig2();

            p.sendMessage(Logger.color(Main.Prefix() + "&7Welkom op " + Config.getCustomConfig3().getString("ServerNaam") + "! Dit is de eerste keer dat je joint en jij hebt het nummer: &f" + nummer + " &7ontvangen!"));

            ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
            ItemMeta telefoonmeta = telefoon.getItemMeta();
            telefoonmeta.setDisplayName(nummer);
            telefoon.setItemMeta(telefoonmeta);

            p.getInventory().setItem(8, telefoon);
        }
        else{
            String nummer = Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Nummer");
            if(nummer == null){
                p.sendMessage(Logger.color("&c&lEr ging iets fout bij het inladen van u telefoon!"));
                return;
            }

            Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".Power", false);
            Config.saveConfig2();

            List<String> itemlijst = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Berichten");
            List<String> itemlijst2 = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Transactions");

            if(p.getInventory().getItem(8) == null){
                ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                ItemMeta telefoonmeta = telefoon.getItemMeta();
                telefoonmeta.setDisplayName(nummer);
                telefoon.setItemMeta(telefoonmeta);

                p.getInventory().setItem(8, telefoon);
            }
            else if(!p.getInventory().getItem(8).equals(Material.BLAZE_POWDER)) {
                ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                ItemMeta telefoonmeta = telefoon.getItemMeta();
                telefoonmeta.setDisplayName(nummer);
                telefoon.setItemMeta(telefoonmeta);

                p.getInventory().setItem(8, telefoon);
            }

            p.sendMessage(Logger.color(Main.Prefix() + "&7Telefoon word ingeladen, nummer: &f" + nummer));

            String[] items2 = (String[])itemlijst2.toArray(new String[0]);
            String[] var12 = items2;
            int var15 = items2.length;

            for(int i = 0; i < var15; ++i) {
                String transID = var12[i];

                if(p == null) return;

                if(Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".TransactionsWeight." + transID + ".Validaded") == "false"){
                    Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".TransactionsWeight." + transID + ".Validaded", null);
                    Config.saveConfig2();

                    int bedrag = Config.getCustomConfig2().getInt("Players." + p.getUniqueId() + ".TransactionsWeight." + transID + ".Bedrag");
                    Main.getEconomy().depositPlayer(p, bedrag);
                }
            }

            if(itemlijst.size() > 0){
                if(itemlijst.size() == 1){
                    p.sendMessage(Logger.color(Main.Prefix() + "&7U heeft nog &f" + itemlijst.size() + " &7openstaand bericht!"));
                }
                else {
                    p.sendMessage(Logger.color(Main.Prefix() + "&7U heeft nog &f" + itemlijst.size() + " &7openstaande berichten!"));
                }
            }
            else {
                p.sendMessage(Logger.color(Main.Prefix() + "&7U heeft nog &fgeen &7openstaande berichten!"));
            }
        }
    }
}
