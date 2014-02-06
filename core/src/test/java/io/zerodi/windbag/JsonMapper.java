package io.zerodi.windbag;

import static com.yammer.dropwizard.testing.FixtureHelpers.fixture;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yammer.dropwizard.json.ObjectMapperFactory;

/**
 * Helper class, easing the pain of testing JSON fixtures and serialization / deserialization of objects. Dropwizard provides
 * {@link com.yammer.dropwizard.testing.JsonHelpers}, but there is no way to customize the mapper in it - this class basically customizes
 * the mapper.
 *
 * @author zerodi
 */
public class JsonMapper {
    private final ObjectMapper wrappingMapper;
    private final ObjectMapper nonwrappingMapper;

    private JsonMapper() {
        this.wrappingMapper = new ObjectMapperFactory().build();
        wrappingMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);

        this.nonwrappingMapper = new ObjectMapperFactory().build();
    }

    public static JsonMapper getInstance() {
        return new JsonMapper();
    }

    /**
     * Converts the given object into a canonical JSON string.
     *
     * @param object
     *            an object
     * @return {@code object} as a JSON string
     * @throws IllegalArgumentException
     *             if there is an error encoding {@code object}
     */
    public String asJson(Object object) throws IOException {
        return wrappingMapper.writeValueAsString(object);
    }

    /**
     * Loads the given fixture resource as a normalized JSON string.
     *
     * @param filename
     *            the filename of the fixture
     * @return the contents of {@code filename} as a normalized JSON string
     * @throws IOException
     *             if there is an error parsing {@code filename}
     */
    public String jsonFixture(String filename) throws IOException {
        return nonwrappingMapper.writeValueAsString(nonwrappingMapper.readValue(fixture(filename), JsonNode.class));
    }
}
