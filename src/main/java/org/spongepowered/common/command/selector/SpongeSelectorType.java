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
package org.spongepowered.common.command.selector;

import com.mojang.brigadier.StringReader;
import net.minecraft.command.arguments.EntitySelectorParser;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.command.selector.Selector;
import org.spongepowered.api.command.selector.SelectorType;
import org.spongepowered.common.accessor.command.arguments.EntitySelectorParserAccessor;

public final class SpongeSelectorType implements SelectorType {

    private final String selectorKey;
    private final ResourceKey resourceKey;

    public SpongeSelectorType(
            final String selectorKey,
            final ResourceKey resourceKey) {
        this.selectorKey = selectorKey;
        this.resourceKey = resourceKey;
    }

    @Override
    public final String selectorKey() {
        return this.selectorKey;
    }

    @Override
    @NonNull
    public final Selector toSelector() {
        return this.toBuilder().build();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public final Selector.@NonNull Builder toBuilder() {
        final EntitySelectorParser parser = new EntitySelectorParser(new StringReader(this.selectorKey));
        ((EntitySelectorParserAccessor) parser).accessor$parseSelector();
        return (Selector.Builder) parser;
    }

    @Override
    @NonNull
    public final ResourceKey getKey() {
        return this.resourceKey;
    }

}
