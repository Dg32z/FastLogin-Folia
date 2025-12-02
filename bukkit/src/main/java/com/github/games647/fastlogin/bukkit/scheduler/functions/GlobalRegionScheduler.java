/*
 * SPDX-License-Identifier: MIT
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015-2024 games647 and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.games647.fastlogin.bukkit.scheduler.functions;

import com.github.games647.fastlogin.bukkit.scheduler.task.NekoTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

public class GlobalRegionScheduler {
    private BukkitScheduler bukkitScheduler;
    private io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler globalRegionScheduler;

    public GlobalRegionScheduler() {
        if (Scheduler.isFolia()) {
            this.globalRegionScheduler = Bukkit.getGlobalRegionScheduler();
        } else {
            this.bukkitScheduler = Bukkit.getScheduler();
        }

    }

    public NekoTask runTask(@NotNull Plugin plugin, @NotNull Runnable task) {
        return !Scheduler.isFolia() ? new NekoTask(this.bukkitScheduler.runTask(plugin, task)) : new NekoTask(this.globalRegionScheduler.run(plugin, (o) -> task.run()));
    }

    public NekoTask runTaskLater(@NotNull Plugin plugin, @NotNull Runnable task, long delay) {
        if (delay < 1L) {
            delay = 1L;
        }

        return !Scheduler.isFolia() ? new NekoTask(this.bukkitScheduler.runTaskLater(plugin, task, delay)) : new NekoTask(this.globalRegionScheduler.runDelayed(plugin, (o) -> task.run(), delay));
    }

    public NekoTask runTaskTimer(@NotNull Plugin plugin, @NotNull Runnable task, long initialDelayTicks, long periodTicks) {
        if (initialDelayTicks < 1L) {
            initialDelayTicks = 1L;
        }

        if (periodTicks < 1L) {
            periodTicks = 1L;
        }

        return !Scheduler.isFolia() ? new NekoTask(this.bukkitScheduler.runTaskTimer(plugin, task, initialDelayTicks, periodTicks)) : new NekoTask(this.globalRegionScheduler.runAtFixedRate(plugin, (o) -> task.run(), initialDelayTicks, periodTicks));
    }

    public void cancel(@NotNull Plugin plugin) {
        if (!Scheduler.isFolia()) {
            Bukkit.getScheduler().cancelTasks(plugin);
        } else {
            this.globalRegionScheduler.cancelTasks(plugin);
        }
    }
}
