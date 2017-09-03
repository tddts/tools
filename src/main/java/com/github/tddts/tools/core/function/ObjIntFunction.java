/*
 * Copyright 2017 Tigran Dadaiants
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.tddts.tools.core.function;

import java.util.function.BiFunction;

/**
 * Represents an operation that accepts an object-valued and a
 * {@code int}-valued argument and produces a result.
 * <p>
 * This is the {@code int}-consuming primitive specialization for
 * {@link BiFunction}.
 * <p>
 * This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(int, Object)}.
 *
 * @param <R> the type of the result of the function
 * @param <T> the type of the object argument to the operation
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 * @see java.util.function.Function
 */
@FunctionalInterface
public interface ObjIntFunction<R, T> {

  R apply(int value, T u);
}
