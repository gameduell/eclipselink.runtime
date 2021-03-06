package org.eclipse.persistence.json.bind.internal.conversion;

import org.eclipse.persistence.json.bind.internal.JsonbContext;

import javax.json.JsonStructure;
import javax.json.JsonWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;

/**
 * @author David Král
 */
public class JsonStructureTypeConverter extends AbstractTypeConverter<JsonStructure> {

    public JsonStructureTypeConverter() {
        super(JsonStructure.class);
    }

    @Override
    public JsonStructure fromJson(String jsonValue, Type type) {
        return getJsonObject(jsonValue);
    }

    @Override
    public String toJson(JsonStructure object) {
        final StringWriter stringWriter = new StringWriter();
        final JsonWriter jsonWriter = JsonbContext.getInstance().getJsonProvider().createWriter(stringWriter);
        jsonWriter.write(object);
        jsonWriter.close();

        return stringWriter.toString();
    }

}
