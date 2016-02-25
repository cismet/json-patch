package com.github.fge.jsonpatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.github.fge.jackson.JacksonUtils;
import com.github.fge.jsonpatch.operation.*;

/**
 * StandardJsonPatchfactory can create a JsonPatchFactory configured to work with the RFC6902 set of JSON Patch operations.
 */
public class StandardJsonPatchFactory
{
    public static JsonPatchFactory create()
    {
        ObjectMapper mapper = JacksonUtils.newMapper();
        mapper.registerSubtypes(
                new NamedType(AddOperation.class, AddOperation.OPERATION_NAME),
                new NamedType(CopyOperation.class, CopyOperation.OPERATION_NAME),
                new NamedType(MoveOperation.class, MoveOperation.OPERATION_NAME),
                new NamedType(RemoveOperation.class, RemoveOperation.OPERATION_NAME),
                new NamedType(ReplaceOperation.class, ReplaceOperation.OPERATION_NAME),
                new NamedType(TestOperation.class, TestOperation.OPERATION_NAME)
        );
        return new JsonPatchFactory(mapper);
    }
}
