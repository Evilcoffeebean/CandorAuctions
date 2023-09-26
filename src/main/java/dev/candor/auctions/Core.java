package dev.candor.auctions;

import dev.candor.auctions.command.CommandCenter;
import dev.candor.auctions.command.CommandTabCompleter;
import dev.candor.auctions.engine.ParticipantManager;
import dev.candor.auctions.event.JoinQuitEvents;
import dev.candor.auctions.event.MainMenuListener;
import dev.candor.auctions.event.RevokeMenuListener;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

@Getter
public class Core extends JavaPlugin {

    @Getter
    private static Core core;
    private final ParticipantManager participantManager = new ParticipantManager();
    private Economy economy;

    @Override
    public void onEnable() {
        core = this;
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        registerCommands();
        registerEvents(new JoinQuitEvents(), new RevokeMenuListener(), new MainMenuListener());
    }

    private void registerEvents(Listener... events) {
        Stream.of(events).forEach(event -> getServer().getPluginManager().registerEvents(event, this));
    }

    private void registerCommands() {
        getCommand("auctionhouse").setExecutor(new CommandCenter());
        getCommand("auctionhouse").setTabCompleter(new CommandTabCompleter());
    }

    @Override
    public void onDisable() {
        participantManager.getParticipants().clear();
        participantManager.clearUsers();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }
}
