/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cgc.vaadin.util;

/**
 *
 * @author cesar
 */
import java.util.Date;
import java.util.Locale;
import com.vaadin.data.util.converter.Converter;

@SuppressWarnings("serial")
public class DateToSqlTimestampConverter implements Converter<Date, java.sql.Timestamp> {

    @Override
    public Class<java.sql.Timestamp> getModelType() {
        return java.sql.Timestamp.class;
    }

    @Override
    public Class<Date> getPresentationType() {
        return Date.class;
    }

    @Override
    public java.sql.Timestamp convertToModel(Date value, Locale locale) throws ConversionException {

        if (null == value) {
            return null;
        }

        return new java.sql.Timestamp(value.getTime());
    }

    @Override
    public Date convertToPresentation(java.sql.Timestamp value, Locale locale) throws ConversionException {
        if (null == value) {
            return null;
        }

        return new Date(value.getTime());
    }
}
