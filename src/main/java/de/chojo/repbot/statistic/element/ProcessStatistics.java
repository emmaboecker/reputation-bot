package de.chojo.repbot.statistic.element;

import de.chojo.jdautil.localization.util.Replacement;
import de.chojo.repbot.statistic.EmbedDisplay;
import de.chojo.repbot.statistic.ReplacementProvider;
import net.dv8tion.jda.api.EmbedBuilder;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ProcessStatistics implements ReplacementProvider, EmbedDisplay {

    private static final int MB = 1024 * 1024;
    private static final Instant START = Instant.now();
    private final long total;
    private final long used;
    private final long free;
    private final long max;
    private final long threads;

    public ProcessStatistics(long total, long used, long free, long max, long threads) {
        this.total = total;
        this.used = used;
        this.free = free;
        this.max = max;
        this.threads = threads;
    }

    public static ProcessStatistics create() {
        var instance = Runtime.getRuntime();
        var total = instance.totalMemory() / MB;
        var free = instance.freeMemory() / MB;
        var used = total - free / MB;
        var max = instance.maxMemory() / MB;

        return new ProcessStatistics(total, free, used, max, Thread.activeCount());
    }

    public long total() {
        return total;
    }

    public long used() {
        return used;
    }

    public long free() {
        return free;
    }

    public long max() {
        return max;
    }

    public long threads() {
        return threads;
    }

    public long uptime() {
        return START.until(Instant.now(), ChronoUnit.MILLIS);
    }

    @Override
    public List<Replacement> replacements() {
        return List.of(Replacement.create("total_mem", total), Replacement.create("used_mem", used),
                Replacement.create("free_mem", free), Replacement.create("max_mem", max),
                Replacement.create("threads", threads));
    }

    @Override
    public void appendTo(EmbedBuilder embedBuilder) {
        embedBuilder.addField("Process Info",
                String.format("""
                                Threads: %s
                                Memory: %s/%s MB
                                Uptime: %s
                                """.stripIndent(),
                        threads, used, total,
                        DurationFormatUtils
                                .formatDuration(uptime(), "dd:HH:mm:ss")), false);
    }
}
