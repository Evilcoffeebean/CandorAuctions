package dev.candor.auctions.engine;

import dev.candor.auctions.util.StringUtil;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class AuctionItem {

    private final Player owner;
    private final ItemStack item;
    private final double price;

    public AuctionItem(final Player owner, final ItemStack item, final double price) {
        this.owner = owner;
        this.item = item;
        this.price = price;
    }

    public ItemStack getItem() {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        if (meta != null) {
            Stream.of("&6Owner: &e" + owner.getName(), "&6Price: &e$" + price).forEach(line -> lore.add(StringUtil.color(line)));
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
}
