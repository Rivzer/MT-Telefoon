package be.rivzer.telefoon.Listeners;

import be.rivzer.telefoon.API.TransactionsEvents;
import be.rivzer.telefoon.Config.Config;
import be.rivzer.telefoon.Inventorys.*;
import be.rivzer.telefoon.Logger;
import be.rivzer.telefoon.Main;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class InventoryClick implements Listener {

    public static Map<UUID, Long> NummerAdden = new HashMap<UUID, Long>();
    public static Map<UUID, Long> BerichtSturen = new HashMap<UUID, Long>();
    public static Map<UUID, Long> Opstarten = new HashMap<UUID, Long>();
    public static Map<UUID, Long> INGTRANS = new HashMap<UUID, Long>();
    private Main plugin = (Main) Main.getPlugin(Main.class);
    private static Player sender;
    private static ItemStack berichtDetails;
    private static ItemStack transactieDetails;
    private static List<String> transactions;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent e){
        if(e.getInventory().getName().equalsIgnoreCase(Logger.color("&0."))){
            e.setCancelled(true);

            ItemStack is = e.getCurrentItem();
            if(is == null) return;

            final String itemName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            Player p = (Player) e.getWhoClicked();

            List<String> itemlijst = Config.getCustomConfig1().getStringList("GPSList");
            String[] items20 = (String[])itemlijst.toArray(new String[0]);
            String[] var120 = items20;
            int var150 = items20.length;

            for(int i0 = 0; i0 < var150; ++i0) {
                String naam = var120[i0];

                if(is.getType().equals(Material.IRON_INGOT)){
                    if(itemName.equalsIgnoreCase(naam)){
                        if(Main.gpsapi.gpsIsActive(p)) Main.gpsapi.stopGPS(p);
                        Main.gpsapi.startGPS(p, naam);
                        p.sendMessage(Logger.color(Main.Prefix() + "&7Uw gps naar &f" + naam + " &7is &agestart."));
                        p.closeInventory();
                    }
                }
            }

            if(is.getType().equals(Material.IRON_INGOT)){
                if(itemName.equalsIgnoreCase("Power ON")){
                    p.sendMessage(Logger.color(Main.Prefix() + "&aTelefoon word opgestart..."));
                    Opstarten.put(p.getUniqueId(), System.currentTimeMillis() + (5 * 1000));
                    p.closeInventory();

                    Bukkit.getScheduler().runTaskLater(plugin, () -> {

                        Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".Power", true);
                        Config.saveConfig2();

                        p.sendMessage(Logger.color(Main.Prefix() + "&aU telefoon is opgestart!"));

                    }, 5 * 20);
                }
                else if(itemName.equalsIgnoreCase("Contacten")){
                    p.closeInventory();
                    Contacten.OpenTelefoonContacten(p);
                }
                else if(itemName.equalsIgnoreCase("ING")){
                    p.closeInventory();
                    ING.OpenTelefoonING(p);
                }
                else if(itemName.equalsIgnoreCase("Maak Nieuwe Transactie")){
                    p.closeInventory();
                    ING_NEW_TRANSACTION.OpenTelefoonING2(p);
                }
                else if(itemName.equalsIgnoreCase("Transactie History")){
                    p.closeInventory();
                    ING_TRANSACTION_HISTORY.OpenTelefoonING3(p);
                }
                else if(itemName.equalsIgnoreCase("Whatsapp")){
                    p.closeInventory();
                    Whatsapp.OpenTelefoonWhatsapp(p);
                }
                else if(itemName.equalsIgnoreCase("Berichten")){
                    p.closeInventory();
                    Whatsapp_BERICHTEN.OpenTelefoonWhatsapp2(p);
                }
                else if(itemName.equalsIgnoreCase("Maak Nieuw Bericht")){
                    p.closeInventory();
                    Whatsapp_NIEUW_BERICHT.OpenTelefoonWhatsapp3(p);
                }
                else if(itemName.equalsIgnoreCase("GoogleMaps")){
                    p.closeInventory();
                    GoogleMaps.OpenTelefoonGoogleMaps(p);
                }
                else if(itemName.equalsIgnoreCase("Stop GPS")){
                    p.closeInventory();

                    if(Main.gpsapi.gpsIsActive(p)){
                        Main.gpsapi.stopGPS(p);
                        p.sendMessage(Logger.color(Main.Prefix() + "&7Uw gps is &cgestopt."));
                    }
                }
                else if(itemName.equalsIgnoreCase("DarkWeb")){
                    p.closeInventory();
                    p.sendMessage(Logger.color("&b&lSOON"));
                }else if(itemName.equalsIgnoreCase("DarkWeb [LOCKED]")){
                    p.closeInventory();

                    if(!(Main.getEconomy().getBalance(p) >= 15000)){
                        p.sendMessage(Logger.color(Main.Prefix() + "&cU heeft niet genoeg geld om dit te unlocken!"));
                        return;
                    }

                    EconomyResponse er = Main.getEconomy().withdrawPlayer(p, 15000);

                    if(er.transactionSuccess()){
                        Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".DarkWeb", true);
                        Config.saveConfig2();

                        p.sendMessage(Logger.color(Main.Prefix() + "&aU heeft nu &8&lDarkWeb &age-unlocked!"));
                    }
                    else{
                        p.sendMessage(Logger.color(Main.Prefix() + "&cEr ging iets mis probeer het opnieuw!"));
                    }
                }
                else if(itemName.equalsIgnoreCase("Home")){
                    p.sendMessage(Logger.color(Main.Prefix() + "&7Uw telefoon wordt gesloten."));
                    p.closeInventory();
                }
                else if(itemName.equalsIgnoreCase("Terug")){
                    p.closeInventory();
                    Telefoon.OpenTelefoon(p);
                }
                else if(itemName.equalsIgnoreCase("Add")){
                    if(isInAdden(p) == true){
                        p.sendMessage(Logger.color(Main.Prefix() + "&cJe bent al een telefoon nummer aan het toevoegen!"));
                        return;
                    }
                    else if(stuurtBericht(p) == true){
                        p.sendMessage(Logger.color(Main.Prefix() + "&cU bent momenteel al een bericht aan het versturen u moet dit eerst annuleren!"));
                        return;
                    }
                    else if(isINGTRANS(p) == true){
                        p.sendMessage(Logger.color(Main.Prefix() + "&cU bent al een transactie aan het maken, u moet deze eerst annuleren!"));
                        return;
                    }

                    NummerAdden.put(p.getUniqueId(), System.currentTimeMillis() + (20 * 1000));

                    p.closeInventory();
                    p.sendMessage(Logger.color(Main.Prefix() + "&7Type het telefoon nummer dat u wilt toevoegen aan uw contacten (Incl 06-). Type 'Annuleer' om het te annuleren"));

                    Bukkit.getScheduler().runTaskLater(plugin, () -> {

                        if(isInAdden(p) == true){
                            p.sendMessage(Logger.color(Main.Prefix() + "&cUw tijd is verlopen om een contact toe te voegen!"));
                        }

                    }, 19 * 20);
                }
            }
            else if(is.getType().equals(Material.PAPER)){
                if(e.getAction() == InventoryAction.PICKUP_ALL){
                    List<String> itemlijst2 = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Berichten");
                    String[] items2 = (String[])itemlijst2.toArray(new String[0]);
                    String[] var12 = items2;
                    int var15 = items2.length;

                    for(int i = 0; i < var15; ++i) {
                        String list = var12[i];
                        String display = ChatColor.stripColor(is.getItemMeta().getDisplayName());

                        if(display.equalsIgnoreCase(list)){
                            List<String> berichten = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Berichten");

                            berichten.remove(list);
                            Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".Berichten", berichten);

                            Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".Messages." + list, null);
                            Config.saveConfig2();

                            if(berichten.size() <= 0){
                                Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".HeeftEenBericht", false);
                                Config.saveConfig2();
                            }

                            p.sendMessage(Logger.color(Main.Prefix() + "&f" + list + " &aIs succesvol gemarkeerd als gelezen."));
                            p.updateInventory();
                            p.closeInventory();
                            Whatsapp_BERICHTEN.OpenTelefoonWhatsapp2(p);
                            return;
                        }
                    }
                }
                else if(e.getAction() == InventoryAction.PICKUP_HALF){
                    List<String> itemlijst2 = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Berichten");
                    String[] items2 = (String[])itemlijst2.toArray(new String[0]);
                    String[] var12 = items2;
                    int var15 = items2.length;

                    for(int i = 0; i < var15; ++i) {
                        String list = var12[i];
                        String display = ChatColor.stripColor(is.getItemMeta().getDisplayName());

                        if(display.equalsIgnoreCase(list)){
                            p.sendMessage(Logger.color(Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Messages." + list + ".Brief")));
                            p.closeInventory();
                            return;
                        }
                    }
                }
            }
            else if(is.getType().equals(Material.SKULL_ITEM)){
                List<String> lore = is.getItemMeta().getLore();

                if(Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Contacts").contains(ChatColor.stripColor(lore.get(0)))){
                    //ING TRANSACTIE MAKEN
                    if(ChatColor.stripColor(lore.get(1)).equalsIgnoreCase("(Klik Om Transactie Te Maken)")){
                        if(e.getAction() == InventoryAction.PICKUP_ALL){
                            List<String> contacts = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Contacts");

                            if(!contacts.contains(ChatColor.stripColor(lore.get(0)))){
                                p.sendMessage(Logger.color(Main.Prefix() + "&cEr is iets heel hard fout gegaan, contacteer aub Rivzer#5116."));
                                return;
                            }
                            else if(stuurtBericht(p) == true){
                                p.sendMessage(Logger.color(Main.Prefix() + "&cU bent momenteel al een bericht aan het versturen u moet dit eerst annuleren!"));
                                return;
                            }
                            else if(isInAdden(p) == true){
                                p.sendMessage(Logger.color(Main.Prefix() + "&cU bent momenteel al een nummer aan het toevoegen u moet dit eerst annuleren!"));
                                return;
                            }

                            sender = Bukkit.getPlayer(ChatColor.stripColor(is.getItemMeta().getDisplayName()));

                            transactieDetails = is;

                            INGTRANS.put(p.getUniqueId(), System.currentTimeMillis() + (20 * 1000));

                            Bukkit.getScheduler().runTaskLater(plugin, () -> {

                                if(isINGTRANS(p) == true){
                                    p.sendMessage(Logger.color(Main.Prefix() + "&cUw tijd is verlopen om een transactie te maken naar &f" + e.getCurrentItem().getItemMeta().getDisplayName()));
                                }

                            }, 19 * 20);

                            p.sendMessage(Logger.color(Main.Prefix() + "&7Type u bedrag dat u wilt overmaken naar &f" + e.getCurrentItem().getItemMeta().getDisplayName() + "&7. Type 'Annuleer' om het te annuleren"));

                            p.closeInventory();
                        }
                        return;
                    }
                    //VERWIJDER
                    else if(ChatColor.stripColor(lore.get(1)).equalsIgnoreCase("(Shift-Klik Verwijderen)")){
                        if(e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY){
                            List<String> contacts = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Contacts");

                            if(!contacts.contains(ChatColor.stripColor(lore.get(0)))){
                                p.sendMessage(Logger.color(Main.Prefix() + "&cEr is iets heel hard fout gegaan, contacteer aub Rivzer#5116."));
                                return;
                            }

                            contacts = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Contacts");
                            contacts.remove(ChatColor.stripColor(lore.get(0)));
                            Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".Contacts", contacts);
                            Config.saveConfig2();

                            p.sendMessage(Logger.color(Main.Prefix() + "&aContact nummer &f" + ChatColor.stripColor(lore.get(0)) + " &ais verwijderd uit uw contacten."));
                            p.updateInventory();
                            Contacten.OpenTelefoonContacten(p);
                        }
                        return;
                    }
                    //BERICHT
                    else if(ChatColor.stripColor(lore.get(1)).equalsIgnoreCase("(Klik Om Bericht Te Sturen)")){
                        if(e.getAction() == InventoryAction.PICKUP_ALL){
                            List<String> contacts = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Contacts");

                            if(stuurtBericht(p) == true){
                                p.sendMessage(Logger.color(Main.Prefix() + "&cU bent al een bericht aan het sturen, u moet deze eerst annuleren!"));
                                return;
                            }
                            else if(isINGTRANS(p) == true){
                                p.sendMessage(Logger.color(Main.Prefix() + "&cU bent al een transactie aan het maken, u moet deze eerst annuleren!"));
                                return;
                            }
                            else if(!contacts.contains(ChatColor.stripColor(lore.get(0)))){
                                p.sendMessage(Logger.color(Main.Prefix() + "&cEr is iets heel hard fout gegaan, contacteer aub Rivzer#5116."));
                                return;
                            }
                            else if(isInAdden(p) == true){
                                p.sendMessage(Logger.color(Main.Prefix() + "&cU bent momenteel al een nummer aan het toevoegen u moet dit eerst annuleren!"));
                                return;
                            }

                            sender = Bukkit.getPlayer(ChatColor.stripColor(is.getItemMeta().getDisplayName()));

                            berichtDetails = is;

                            BerichtSturen.put(p.getUniqueId(), System.currentTimeMillis() + (20 * 1000));

                            Bukkit.getScheduler().runTaskLater(plugin, () -> {

                                if(stuurtBericht(p) == true){
                                    p.sendMessage(Logger.color(Main.Prefix() + "&cUw tijd is verlopen om een bericht te sturen naar &f" + e.getCurrentItem().getItemMeta().getDisplayName()));
                                }

                            }, 19 * 20);

                            p.sendMessage(Logger.color(Main.Prefix() + "&7Type u bericht dat u wilt versturen naar &f" + e.getCurrentItem().getItemMeta().getDisplayName() + "&7. Type 'Annuleer' om het te annuleren"));

                            p.closeInventory();
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void OnTransactionMaken(AsyncPlayerChatEvent e){
        String getMessage = e.getMessage();
        if(transactieDetails == null) return;
        List<String> lore = transactieDetails.getItemMeta().getLore();
        Player p = e.getPlayer();

        if(isINGTRANS(p) == true){
            e.setCancelled(true);

            if(getMessage.equalsIgnoreCase("annuleer")){
                //Annuleer en laat de speler terug praten in chat
                INGTRANS.remove(p.getUniqueId());
                p.sendMessage(Logger.color(Main.Prefix() + "&7U kan nu terug praten in de chat."));
                return;
            }

            List<String> contacts = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Contacts");

            INGTRANS.remove(p.getUniqueId());

            NBTItem nbti = new NBTItem(transactieDetails);
            NBTCompound skull = nbti.addCompound("SkullOwner");

            if(!contacts.contains(ChatColor.stripColor(lore.get(0)))){
                p.sendMessage(Logger.color(Main.Prefix() + "&cDit nummer staat niet in uw contacten lijst."));
                return;
            }
            else if(isInteger(getMessage) == false){
                p.sendMessage(Logger.color(Main.Prefix() + "&cU moet een geldig bedrag opgeven!"));
                return;
            }
            else if(!(Main.getEconomy().getBalance(p) >= Integer.valueOf(getMessage))){
                p.sendMessage(Logger.color(Main.Prefix() + "&cU heeft niet zoveel geld op uw bankekening staan!"));
                return;
            }
            else if(sender == null){
                transactions = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Transactions");
                transactions.add("TransID-" + (transactions.size()+1));
                Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".Transactions", transactions);

                transactions = Config.getCustomConfig2().getStringList("Players." + skull.getString("Id") + ".Transactions");
                transactions.add("TransID-" + (transactions.size()+1));
                Config.getCustomConfig2().set("Players." + skull.getString("Id") + ".Transactions", transactions);

                Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".TransactionsWeight." + "TransID-" + transactions.size() + ".Bedrag", Integer.valueOf(getMessage));
                Config.getCustomConfig2().set("Players." + skull.getString("Id") + ".TransactionsWeight." + "TransID-" + transactions.size() + ".Bedrag", Integer.valueOf(getMessage));

                Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".TransactionsWeight." + "TransID-" + transactions.size() + ".VanUUID", skull.getString("Id").toString());
                Config.getCustomConfig2().set("Players." + skull.getString("Id") + ".TransactionsWeight." + "TransID-" + transactions.size() + ".VanUUID", p.getUniqueId().toString());

                Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".TransactionsWeight." + "TransID-" + transactions.size() + ".HeeftTransactieGemaakt", p.getUniqueId().toString());
                Config.getCustomConfig2().set("Players." + skull.getString("Id") + ".TransactionsWeight." + "TransID-" + transactions.size() + ".HeeftTransactieGemaakt", p.getUniqueId().toString());

                Config.saveConfig2();

                INGTRANS.remove(p.getUniqueId());

                EconomyResponse pr = Main.getEconomy().withdrawPlayer(p, Integer.valueOf(getMessage));
                if(pr.transactionSuccess()){
                    Config.getCustomConfig2().set("Players." + skull.getString("Id") + ".TransactionsWeight." + "TransID-" + transactions.size() + ".Validaded", false);
                    Config.saveConfig2();

                    p.sendMessage(Logger.color(Main.Prefix() + "&aTransactie van &f€" + getMessage + " &ais overgemaakt naar &f" + transactieDetails.getItemMeta().getDisplayName()));

                    Bukkit.getPluginManager().callEvent(new TransactionsEvents(p, Integer.valueOf(getMessage)));
                }
                else{
                    p.sendMessage(Logger.color(Main.Prefix() + "&cEr ging iets mis probeer het later opnieuw!"));
                    return;
                }
            }
            else {
                transactions = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Transactions");
                transactions.add("TransID-" + (transactions.size()+1));
                Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".Transactions", transactions);

                transactions = Config.getCustomConfig2().getStringList("Players." + sender.getUniqueId() + ".Transactions");
                transactions.add("TransID-" + (transactions.size()+1));
                Config.getCustomConfig2().set("Players." + sender.getUniqueId() + ".Transactions", transactions);

                Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".TransactionsWeight." + "TransID-" + transactions.size() + ".Bedrag", Integer.valueOf(getMessage));
                Config.getCustomConfig2().set("Players." + sender.getUniqueId() + ".TransactionsWeight." + "TransID-" + transactions.size() + ".Bedrag", Integer.valueOf(getMessage));

                Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".TransactionsWeight." + "TransID-" + transactions.size() + ".VanUUID", sender.getUniqueId().toString());
                Config.getCustomConfig2().set("Players." + sender.getUniqueId() + ".TransactionsWeight." + "TransID-" + transactions.size() + ".VanUUID", p.getUniqueId().toString());

                Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".TransactionsWeight." + "TransID-" + transactions.size() + ".HeeftTransactieGemaakt", p.getUniqueId().toString());
                Config.getCustomConfig2().set("Players." + sender.getUniqueId() + ".TransactionsWeight." + "TransID-" + transactions.size() + ".HeeftTransactieGemaakt", p.getUniqueId().toString());

                Config.saveConfig2();

                INGTRANS.remove(p.getUniqueId());

                EconomyResponse pr = Main.getEconomy().withdrawPlayer(p, Integer.valueOf(getMessage));
                if(pr.transactionSuccess()){
                    EconomyResponse sr = Main.getEconomy().depositPlayer(sender, Integer.valueOf(getMessage));
                    if(sr.transactionSuccess()){
                        p.sendMessage(Logger.color(Main.Prefix() + "&aTransactie van &f€" + getMessage + " &ais overgemaakt naar &f" + sender.getName()));
                        sender.sendMessage(Logger.color(Main.Prefix() + "&7U heeft geld ontvangen van &f" + p.getName()));

                        Bukkit.getPluginManager().callEvent(new TransactionsEvents(p, Integer.valueOf(getMessage)));
                    }
                    else{
                        p.sendMessage(Logger.color(Main.Prefix() + "&cEr ging iets mis gegaan, &f" + sender.getName() + " &cheeft het geld niet ontvangen!"));
                        return;
                    }
                }
                else{
                    p.sendMessage(Logger.color(Main.Prefix() + "&cEr ging iets mis probeer het later opnieuw!"));
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onContactAdden(AsyncPlayerChatEvent e){
        String getMessage = e.getMessage();
        Player p = e.getPlayer();

        if(isInAdden(p) == true){
            e.setCancelled(true);

            if(getMessage.equalsIgnoreCase("annuleer")){
                //Annuleer en laat de speler terug praten in chat
                NummerAdden.remove(p.getUniqueId());
                p.sendMessage(Logger.color(Main.Prefix() + "&7U kan nu terug praten in de chat."));
                return;
            }

            List<String> itemlijst = Config.getCustomConfig2().getStringList("UUIDList");
            String[] items = (String[])itemlijst.toArray(new String[0]);
            String[] var11 = items;
            int var10 = items.length;

            for(int var9 = 0; var9 < var10; ++var9) {
                String uuid = var11[var9];

                if(Config.getCustomConfig2().getString("Players." + uuid + ".Nummer").equalsIgnoreCase(getMessage)){
                    if(getMessage.equalsIgnoreCase(Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Nummer"))){
                        p.sendMessage(Logger.color(Main.Prefix() + "&cJe kan jezelf niet toevoegen!"));
                        return;
                    }
                    else{
                        List<String> contacts = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Contacts");

                        if(contacts.contains(getMessage)){
                            p.sendMessage(Logger.color(Main.Prefix() + "&cDit nummer staat al in uw contacten lijst."));
                            return;
                        }

                        contacts = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Contacts");
                        contacts.add(Config.getCustomConfig2().getString("Players." + uuid + ".Nummer"));
                        Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".Contacts", contacts);
                        Config.saveConfig2();

                        p.sendMessage(Logger.color(Main.Prefix() + "&aHet nummer &7" + getMessage + " &ais toegevoegd aan uw contacten lijst!"));
                        NummerAdden.remove(p.getUniqueId());
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBerichtMaken(AsyncPlayerChatEvent e){
        String getMessage = e.getMessage();
        if(berichtDetails == null) return;
        List<String> lore = berichtDetails.getItemMeta().getLore();
        Player p = e.getPlayer();

        if(stuurtBericht(p)){
            e.setCancelled(true);

            if(getMessage.equalsIgnoreCase("annuleer")){
                //Annuleer en laat de speler terug praten in chat
                BerichtSturen.remove(p.getUniqueId());
                p.sendMessage(Logger.color(Main.Prefix() + "&7U kan nu terug praten in de chat."));
                return;
            }

            List<String> contacts = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Contacts");

            BerichtSturen.remove(p.getUniqueId());

            NBTItem nbti = new NBTItem(berichtDetails);
            NBTCompound skull = nbti.addCompound("SkullOwner");

            if(!contacts.contains(ChatColor.stripColor(lore.get(0)))){
                p.sendMessage(Logger.color(Main.Prefix() + "&cDit nummer staat niet in uw contacten lijst."));
                return;
            }
            else if(sender == null){
                p.sendMessage(Logger.color(Main.Prefix() + "&aBericht is verzonden naar &f&l" + berichtDetails.getItemMeta().getDisplayName()));

                List<String> berichten = Config.getCustomConfig2().getStringList("Players." + skull.getString("Id") + ".Berichten");

                String ID = "";
                ID = getRandomID(3, 1, 5, skull.getString("Id"), p);

                Config.getCustomConfig2().set("Players." + skull.getString("Id") + ".HeeftEenBericht", true);
                Config.getCustomConfig2().set("Players." + skull.getString("Id") + ".Messages" + ".Bericht-" + ID + ".Brief", getMessage);
                berichten.add("Bericht-" + ID);
                Config.getCustomConfig2().set("Players." + skull.getString("Id") + ".Berichten", berichten);

                Config.saveConfig2();
            }
            else {
                p.sendMessage(Logger.color(Main.Prefix() + "&aBericht is verzonden naar &f&l" + sender.getName()));
                sender.sendMessage(Logger.color(Main.Prefix() + "&7U heeft een nieuw bericht van &f&l" + p.getName()));

                List<String> berichten = Config.getCustomConfig2().getStringList("Players." + sender.getUniqueId() + ".Berichten");

                String ID = "";
                ID = getRandomID(3, 1, 5, String.valueOf(sender.getUniqueId()), p);

                Config.getCustomConfig2().set("Players." + sender.getUniqueId() + ".HeeftEenBericht", true);
                Config.getCustomConfig2().set("Players." + sender.getUniqueId() + ".Messages" + ".Bericht-" + ID + ".Brief", getMessage);
                berichten.add("Bericht-" + ID);
                Config.getCustomConfig2().set("Players." + sender.getUniqueId() + ".Berichten", berichten);

                Config.saveConfig2();
            }

            return;
        }
    }

    public static String getRandomID(int upper, int lower, int lang, String UUID, Player p){
        List<String> berichten = Config.getCustomConfig2().getStringList("Players." + UUID + ".Berichten");
        String randomID = "";
        boolean error = false;
        int trys = 0;

        for (int i = 0; i <= lang; i++){
            int randomNummerGen = new Random().nextInt((upper - lower) + 1) + lower;
            randomID = randomID + randomNummerGen;
        }

        if(berichten.contains("Bericht-" + randomID)){
            error = true;
        }

        while(error == true){
            randomID = "";
            trys++;

            for (int i = 0; i <= lang; i++){
                int randomNummerGen = new Random().nextInt((upper - lower) + 1) + lower;
                randomID = randomID + randomNummerGen;
            }

            if(!berichten.contains("Bericht-" + randomID)){
                error = false;
            }

            if(trys > 30){
                error = false;
                p.sendMessage(Logger.color(Main.Prefix() + "&cEr ging iets mis met het versturen van uw bericht! Contacteer aub zsm een developer! ID: &4&lOut Of Storage"));
            }
        }

        return randomID;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    public static boolean isInAdden(Player p){
        if(NummerAdden.containsKey(p.getUniqueId())) {
            if(NummerAdden.get(p.getUniqueId()) > System.currentTimeMillis()){
                return true;
            }
        }
        return false;
    }

    public static boolean stuurtBericht(Player p){
        if(BerichtSturen.containsKey(p.getUniqueId())) {
            if(BerichtSturen.get(p.getUniqueId()) > System.currentTimeMillis()){
                return true;
            }
        }
        return false;
    }

    public static boolean isOpstarten(Player p){
        if(Opstarten.containsKey(p.getUniqueId())) {
            if(Opstarten.get(p.getUniqueId()) > System.currentTimeMillis()){
                return true;
            }
        }
        return false;
    }

    public static boolean isINGTRANS(Player p){
        if(INGTRANS.containsKey(p.getUniqueId())) {
            if(INGTRANS.get(p.getUniqueId()) > System.currentTimeMillis()){
                return true;
            }
        }
        return false;
    }
}
