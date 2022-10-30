package tacos.converter;

import tacos.model.Ingredient;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static tacos.model.Ingredient.Type;

@Converter(autoApply = true)
public class IngredientTypeStringConverter implements AttributeConverter<Type, String> {

    @Override
    public String convertToDatabaseColumn(Ingredient.Type type) {
        return type.name();
    }

    @Override
    public Type convertToEntityAttribute(String s) {
        return Type.valueOf(s);
    }
}
