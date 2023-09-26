package dev.candor.auctions.util;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class MenuUtil {

    private final Inventory inventory;
    private final String name;
    private final int slots;

    public MenuUtil(final String name, final int slots) {
        this.name = StringUtil.color(name);
        this.slots = (slots % 9) == 0 ? slots : 54;
        this.inventory = Bukkit.createInventory(null, slots, ChatColor.translateAlternateColorCodes('&', name));
    }

    public void setItem(final int slot, final ItemStack item) {
        inventory.setItem(slot, item);
    }

    public void build(final Player caller) {
        caller.openInventory(inventory);
    }
}
