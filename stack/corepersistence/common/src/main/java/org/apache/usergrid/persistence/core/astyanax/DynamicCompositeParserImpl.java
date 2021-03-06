/*
 *
 *  *
 *  * Licensed to the Apache Software Foundation (ASF) under one
 *  * or more contributor license agreements.  See the NOTICE file
 *  * distributed with this work for additional information
 *  * regarding copyright ownership.  The ASF licenses this file
 *  * to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance
 *  * with the License.  You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an
 *  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  * KIND, either express or implied.  See the License for the
 *  * specific language governing permissions and limitations
 *  * under the License.
 *  *
 *
 */

package org.apache.usergrid.persistence.core.astyanax;


import java.nio.ByteBuffer;
import java.util.UUID;

import com.netflix.astyanax.Serializer;
import com.netflix.astyanax.model.CompositeParser;
import com.netflix.astyanax.model.DynamicComposite;
import com.netflix.astyanax.serializers.BooleanSerializer;
import com.netflix.astyanax.serializers.IntegerSerializer;
import com.netflix.astyanax.serializers.LongSerializer;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.serializers.UUIDSerializer;


public class DynamicCompositeParserImpl implements CompositeParser {
    private final DynamicComposite composite;
    private int position = 0;

    public DynamicCompositeParserImpl(ByteBuffer bb) {
        this.composite = DynamicComposite.fromByteBuffer(bb);
    }

    @Override
    public String readString() {
        return read( StringSerializer.get() );
    }

    @Override
    public Long readLong() {
        return read( LongSerializer.get() );
    }

    @Override
    public Integer readInteger() {
        return read( IntegerSerializer.get() );
    }

    @Override
    public Boolean readBoolean() {
        return read( BooleanSerializer.get() );
    }

    @Override
    public UUID readUUID() {
        return read( UUIDSerializer.get() );
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T read(Serializer<T> serializer) {
        Object obj = this.composite.get(position, serializer);
        position++;
        return (T) obj;
    }

}
