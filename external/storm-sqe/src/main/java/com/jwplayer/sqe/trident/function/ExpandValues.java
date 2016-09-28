/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jwplayer.sqe.trident.function;

import org.apache.avro.util.Utf8;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.TreeSet;


public class ExpandValues extends BaseFunction {
    @Override
    @SuppressWarnings("unchecked")
    public void execute(TridentTuple tuple, TridentCollector collector) {
        Map map = (Map) tuple.get(0);
        TreeSet set = new TreeSet(map.keySet());

        for(Object key: set) {
            // Avro strings are stored using a special Avro type instead of using Java primitives
            Object value = map.get(key);
            if(value instanceof Utf8) {
                collector.emit(new Values(value.toString()));
            } else {
                collector.emit(new Values(value));
            }
        }
    }
}