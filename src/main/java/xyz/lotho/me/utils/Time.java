package xyz.lotho.me.utils;

import java.util.concurrent.TimeUnit;

public class Time {

    public static String format(long loginTime) {
        StringBuilder stringBuilder = new StringBuilder();

        long days = TimeUnit.MILLISECONDS.toDays(loginTime);
        loginTime -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(loginTime);
        loginTime -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(loginTime);
        loginTime -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(loginTime);

        if (days > 0L) stringBuilder.append(days).append("d");
        if (hours > 0L) stringBuilder.append(" ").append(hours).append("h");
        if (minutes > 0L) stringBuilder.append(" ").append(minutes).append("m");
        if (seconds > 0L) stringBuilder.append(" ").append(seconds).append("s");

        return stringBuilder.toString().trim().equals("") ? "N/A" : stringBuilder.toString().trim();
    }
}
