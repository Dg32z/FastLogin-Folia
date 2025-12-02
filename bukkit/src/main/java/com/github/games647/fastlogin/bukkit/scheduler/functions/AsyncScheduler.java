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

import java.util.concurrent.TimeUnit;

public class AsyncScheduler {
    private BukkitScheduler bukkitScheduler;
    private io.papermc.paper.threadedregions.scheduler.AsyncScheduler asyncScheduler;

    public AsyncScheduler() {
        if (Scheduler.isFolia()) {
            this.asyncScheduler = Bukkit.getAsyncScheduler();
        } else {
            this.bukkitScheduler = Bukkit.getScheduler();
        }

    }

    public NekoTask runTask(@NotNull Plugin plugin, @NotNull Runnable task) {
        return !Scheduler.isFolia() ? new NekoTask(this.bukkitScheduler.runTaskAsynchronously(plugin, task)) : new NekoTask(this.asyncScheduler.runNow(plugin, (o) -> task.run()));
    }

    public NekoTask runTaskLater(@NotNull Plugin plugin, @NotNull Runnable task, long delay) {
        return !Scheduler.isFolia() ? new NekoTask(this.bukkitScheduler.runTaskLaterAsynchronously(plugin, task, delay)) : new NekoTask(this.asyncScheduler.runDelayed(plugin, (o) -> task.run(), delay * 50L, TimeUnit.MILLISECONDS));
    }

    public NekoTask runTaskTimer(@NotNull Plugin plugin, @NotNull Runnable task, long delay, long period) {
        if (period < 1L) {
            period = 1L;
        }

        return !Scheduler.isFolia() ? new NekoTask(this.bukkitScheduler.runTaskTimerAsynchronously(plugin, task, delay, period)) : new NekoTask(this.asyncScheduler.runAtFixedRate(plugin, (o) -> task.run(), delay * 50L, period * 50L, TimeUnit.MILLISECONDS));
    }

    public void cancel(@NotNull Plugin plugin) {
        if (!Scheduler.isFolia()) {
            this.bukkitScheduler.cancelTasks(plugin);
        } else {
            this.asyncScheduler.cancelTasks(plugin);
        }
    }
}
