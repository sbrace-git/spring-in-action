package tacos.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.util.Date;

@Converter(autoApply = true)
public class TacoDateTimestampConverter implements AttributeConverter<Date, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(Date date) {
        return new Timestamp(date.getTime());
    }

    @Override
    public Date convertToEntityAttribute(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }
}
