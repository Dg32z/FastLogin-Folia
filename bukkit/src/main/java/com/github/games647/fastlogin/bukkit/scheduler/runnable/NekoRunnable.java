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
package com.github.games647.fastlogin.bukkit.scheduler.runnable;

import com.github.games647.fastlogin.bukkit.scheduler.functions.Scheduler;
import com.github.games647.fastlogin.bukkit.scheduler.task.NekoTask;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.Runnable;

@Getter
public abstract class NekoRunnable implements Runnable {
    public NekoTask task;

    public void runTask(JavaPlugin plugin) {
        this.task = Scheduler.getGlobalRegionScheduler().runTask(plugin, this::run);
    }

    public void runTaskLater(JavaPlugin plugin, long delay) {
        this.task = Scheduler.getGlobalRegionScheduler().runTaskLater(plugin, this::run, delay);
    }

    public void runTaskTimer(JavaPlugin plugin, long delay, long period) {
        this.task = Scheduler.getGlobalRegionScheduler().runTaskTimer(plugin, this::run, delay, period);
    }

    public void runTaskAsynchronously(JavaPlugin plugin) {
        this.task = Scheduler.getAsyncScheduler().runTask(plugin, this::run);
    }

    public void runTaskLaterAsynchronously(JavaPlugin plugin, long delay) {
        this.task = Scheduler.getAsyncScheduler().runTaskLater(plugin, this::run, delay);
    }

    public void runTaskTimerAsynchronously(JavaPlugin plugin, long delay, long period) {
        this.task = Scheduler.getAsyncScheduler().runTaskTimer(plugin, this::run, delay, period);
    }

    public Plugin getOwner() {
        return this.task.getOwner();
    }

    public boolean isCancelled() {
        return this.task.isCancelled();
    }

    public void cancel() {
        this.task.cancel();
    }

}