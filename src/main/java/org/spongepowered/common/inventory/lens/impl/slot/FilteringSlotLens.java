/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.inventory.lens.impl.slot;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.common.inventory.adapter.InventoryAdapter;
import org.spongepowered.common.inventory.adapter.impl.slots.FilteringSlotAdapter;
import org.spongepowered.common.inventory.fabric.Fabric;
import org.spongepowered.common.item.inventory.lens.slots.FilteringSlotLens;

import java.util.function.Predicate;

public class FilteringSlotLens extends BasicSlotLens implements FilteringSlotLens {

    private final Predicate<ItemStack> stackFilter;
    private final Predicate<ItemType> typeFilter;

    public FilteringSlotLens(int index, Predicate<ItemStack> stackFilter, Predicate<ItemType> typeFilter) {
        this(index, FilteringSlotAdapter.class, stackFilter, typeFilter);
    }

    public FilteringSlotLens(int index, Class<? extends Inventory> adapterType, Predicate<ItemStack> stackFilter, Predicate<ItemType> typeFilter) {
        super(index, adapterType);

        this.stackFilter = stackFilter;
        this.typeFilter = typeFilter;
    }

    @Override
    public boolean setStack(Fabric inv, net.minecraft.item.ItemStack stack) {
        return this.getItemStackFilter().test((ItemStack) stack) && super.setStack(inv, stack);
    }

    @Override
    public Predicate<ItemStack> getItemStackFilter() {
        return this.stackFilter;
    }

    @Override
    public Predicate<ItemType> getItemTypeFilter() {
        return this.typeFilter;
    }

    @Override
    public InventoryAdapter getAdapter(Fabric inv, Inventory parent) {
        return new FilteringSlotAdapter(inv, this, parent);
    }

}