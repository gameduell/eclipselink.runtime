package org.eclipse.persistence.json.bind.internal.conversion;

import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * @author David Král
 */
public class NumberTypeConverter extends AbstractTypeConverter<Number> {

    public NumberTypeConverter() {
        super(Number.class);
    }

    @Override
    public Number fromJson(String jsonValue, Type type) {
        return new BigDecimal(jsonValue);
    }

    @Override
    public String toJson(Number object) {
        return String.valueOf(object);
    }

}
