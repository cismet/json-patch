package com.github.fge.jsonpatch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;

import java.io.IOException;

/**
 * A JsonPatchFactory is able to create a JsonPatch from a JsonNode.
 */
public class JsonPatchFactory
{
    protected static final MessageBundle BUNDLE
        = MessageBundles.getBundle(JsonPatchMessages.class);

    protected final ObjectMapper mapper;
    protected final ObjectReader reader;
    protected final ObjectWriter writer;

    public JsonPatchFactory(final ObjectMapper mapper)
    {
        this.mapper = mapper;
        reader = mapper.reader();
        writer = mapper.writer();
    }

    public ObjectReader getReader()
    {
        return reader;
    }

    public ObjectWriter getWriter()
    {
        return writer;
    }

    /**
     * Builds a JSON Patch out of a JSON representation with support for an extended set of JSON patch operations
     *
     * @param node the JSON representation of the generated JSON Patch
     * @return a JSON Patch
     * @throws IOException input is not a valid JSON patch
     * @throws NullPointerException input is null
     */
    public JsonPatch fromJson(final JsonNode node)
            throws IOException
    {
        BUNDLE.checkNotNull(node, "jsonPatch.nullInput");
        return reader.withType(JsonPatch.class)
            .readValue(node);
    }
}
