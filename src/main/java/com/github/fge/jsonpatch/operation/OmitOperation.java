package com.github.fge.jsonpatch.operation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.operation.policy.PathMissingPolicy;

/**
 * Extended JSON Patch {@code omit} operation.
 *
 * The operation will remove the {@code value} at {@code path} if it exists.
 * It will do nothing if the actual value at {@code path} is not equal to {@code value}.
 * It will throw a "no such path" error if there is no value at {@code path}.
 */
public class OmitOperation extends OmitOperationBase
{
    public static final String OPERATION_NAME = "omit";

    @JsonCreator
    public OmitOperation(@JsonProperty("path") final JsonPointer path,
                         @JsonProperty("value") final JsonNode value)
    {
        super(OPERATION_NAME, path, value, PathMissingPolicy.THROW);
    }
}

