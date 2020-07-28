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
package org.spongepowered.common.mixin.core.world;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.common.bridge.tileentity.TileEntityTypeBridge;
import org.spongepowered.common.bridge.world.ServerWorldBridge;
import org.spongepowered.common.bridge.world.WorldBridge;

import java.util.List;
import java.util.Random;

@Mixin(net.minecraft.world.World.class)
public abstract class WorldMixin implements WorldBridge, IWorld {

    @Shadow @Final public boolean isRemote;
    @Shadow @Final public Random rand;

    @Shadow public abstract Dimension shadow$getDimension();

    @Shadow public abstract WorldInfo shadow$getWorldInfo();

    private boolean impl$isDefinitelyFake = false;
    private boolean impl$hasChecked = false;

    @Override
    public boolean bridge$isFake() {
        if (this.impl$hasChecked) {
            return this.impl$isDefinitelyFake;
        }
        this.impl$isDefinitelyFake = this.isRemote || this.shadow$getWorldInfo() == null || this.shadow$getWorldInfo().getWorldName() == null || !(this instanceof ServerWorldBridge);
        this.impl$hasChecked = true;
        return this.impl$isDefinitelyFake;
    }

    @Override
    public void bridge$clearFakeCheck() {
        this.impl$hasChecked = false;
    }

//    @SuppressWarnings("deprecation")
//    @Override
//    public boolean bridge$isAreaLoaded(final int xStart, final int yStart, final int zStart, final int xEnd, final int yEnd, final int zEnd, final boolean allowEmpty) {
//        return this.isAreaLoaded(xStart, yStart, zStart, xEnd, yEnd, zEnd);
//    }
//
//    @Inject(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V"), cancellable = true)
//    public void onDestroyBlock(final BlockPos pos, final boolean dropBlock, final CallbackInfoReturnable<Boolean> cir) {
//
//    }
//
//    @Redirect(method = "addTileEntity",
//            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", remap = false),
//            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/world/World;tickableTileEntities:Ljava/util/List;"),
//                           to =   @At(value = "FIELD", target = "Lnet/minecraft/world/World;isRemote:Z")))
//    private boolean onAddTileEntity(final List<? super TileEntity> list, final Object tile) {
//        if (!this.bridge$isFake() && !this.canTileUpdate((TileEntity) tile)) {
//            return false;
//        }
//
//        return list.add((TileEntity) tile);
//    }
//
//    @SuppressWarnings("ConstantConditions")
//    private boolean canTileUpdate(final TileEntity tile) {
//        final org.spongepowered.api.block.entity.BlockEntity spongeTile = (org.spongepowered.api.block.entity.BlockEntity) tile;
//        return spongeTile.getType() == null || ((TileEntityTypeBridge) spongeTile.getType()).bridge$canTick();
//    }


}
