package dev.candor.auctions.util;

import org.bukkit.ChatColor;

public final class StringUtil {

    private StringUtil() {} //prevent instantiation

    public static String color(final String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String join(final String... text) {
        final StringBuilder response = new StringBuilder();
        for (int i = 0; i < text.length; i++)
            response.append(text[i]).append(i >= text.length - 1 ? "" : "\n");
        return color(response.toString());
    }

    public static String join(final int index, final String... text) {
        final StringBuilder builder = new StringBuilder();
        for (int i = index; i < text.length; i++)
            builder.append(text[i]).append(i >= text.length - 1 ? "" : " ");
        return builder.toString();
    }
}
