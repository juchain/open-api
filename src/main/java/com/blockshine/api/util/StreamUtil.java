/*
 * Copyright 2015, 2016 Ether.Camp Inc. (US)
 * This file is part of Ethereum Harmony.
 *
 * Ethereum Harmony is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Ethereum Harmony is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Ethereum Harmony.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.blockshine.api.util;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Stan Reshetnyk on 13.01.17.
 */
public class StreamUtil {

    /**
     * Stream or value or empty stream if value is null.
     */
    public static <T> Stream<T> streamOf(T value) {
        return Optional.ofNullable(value)
                .map(Stream::of)
                .orElseGet(Stream::empty);
    }
}
