/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of this file and of both licenses is available at the root of this
 * project or, if you have the jar distribution, in directory META-INF/, under
 * the names LGPL-3.0.txt and ASL-2.0.txt respectively.
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.jsonpatch.diff;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.operation.AddOperation;
import com.github.fge.jsonpatch.operation.CopyOperation;
import com.github.fge.jsonpatch.operation.JsonPatchOperation;
import com.github.fge.jsonpatch.operation.MoveOperation;
import com.github.fge.jsonpatch.operation.RemoveOperation;
import com.github.fge.jsonpatch.operation.ReplaceOperation;

public class DiffOperation
{
    protected final Type type;
    /* An op's "from", if any */
    protected final JsonPointer from;
    /* Value displaced by this operation, if any */
    protected final JsonNode oldValue;
    /* An op's "path", if any */
    protected final JsonPointer path;
    /* An op's "value", if any */
    protected final JsonNode value;

    public static DiffOperation add(final JsonPointer path,
        final JsonNode value)
    {
        return new DiffOperation(Type.ADD, null, null, path, value);
    }

    public static DiffOperation copy(final JsonPointer from,
        final JsonPointer path, final JsonNode value)
    {
        return new DiffOperation(Type.COPY, from, null, path,
            value);
    }

    public static DiffOperation move(final JsonPointer from,
        final JsonNode oldValue, final JsonPointer path,
        final JsonNode value)
    {
        return new DiffOperation(Type.MOVE, from, oldValue, path,
            value);
    }

    public static DiffOperation remove(final JsonPointer from,
        final JsonNode oldValue)
    {
        return new DiffOperation(Type.REMOVE, from, oldValue, null, null);
    }

    public static DiffOperation replace(final JsonPointer from,
        final JsonNode oldValue, final JsonNode value)
    {
        return new DiffOperation(Type.REPLACE, from, oldValue, null,
            value);
    }

    /**
     *
     * @param type
     * @param from
     * @param oldValue
     * @param path
     * @param value
     */
    public DiffOperation(final Type type, final JsonPointer from,
        final JsonNode oldValue, final JsonPointer path,
        final JsonNode value)
    {
        this.type = type;
        this.from = from;
        this.oldValue = oldValue;
        this.path = path;
        this.value = value;
    }

    public Type getType()
    {
        return type;
    }

    public JsonPointer getFrom()
    {
        return from;
    }

    public JsonNode getOldValue()
    {
        return oldValue;
    }

    public JsonPointer getPath()
    {
        return path;
    }

    public JsonNode getValue()
    {
        return value;
    }

    public JsonPatchOperation asJsonPatchOperation()
    {
        return type.toOperation(this);
    }

    public enum Type {
        ADD
            {
                @Override
                public JsonPatchOperation toOperation(final DiffOperation op)
                {
                    return new AddOperation(op.path, op.value);
                }
            },
        COPY
        {
            @Override
            public JsonPatchOperation toOperation(final DiffOperation op)
            {
                return new CopyOperation(op.from, op.path);
            }
        },
        MOVE
        {
            @Override
            public JsonPatchOperation toOperation(final DiffOperation op)
            {
                return new MoveOperation(op.from, op.path);
            }
        },
        REMOVE
        {
            @Override
            public JsonPatchOperation toOperation(final DiffOperation op)
            {
                return new RemoveOperation(op.from);
            }
        },
        REPLACE
        {
            @Override
            public JsonPatchOperation toOperation(final DiffOperation op)
            {
                return new ReplaceOperation(op.from, op.value);
            }
        },
        ;

        public abstract JsonPatchOperation toOperation(final DiffOperation op);
    }
}
