package com.example.demo.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miragesql.miragesql.bean.PropertyDesc;
import com.miragesql.miragesql.type.ValueType;
import com.miragesql.miragesql.util.AnnotationUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class JacksonSerializedValueType implements ValueType<Object> {

	@Setter
	private ObjectMapper objectMapper = new ObjectMapper();


	@Override
	public boolean isSupport(Class<?> type, PropertyDesc propertyDesc) {
		if (propertyDesc != null) {
			JsonSerialized property = propertyDesc.getAnnotation(JsonSerialized.class);
			if (property != null) {
				return true;
			}
		}
		return AnnotationUtils.findAnnotation(type, JsonSerialized.class) != null;
	}

	@Override
	public Object getDefaultValue() {
		return null;
	}

	private Object fromString(Class<?> type, String json) { // NOPMD
		if (json == null) {
			return null;
		}
		try {
			return objectMapper.readValue(json, type);
		} catch (IOException e) {
			log.warn("Failed to deserialize: {}", json, e);
		}
		return null;
	}

	private String toString(Object value) {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			log.warn("Failed to serialize: {}", value, e);
		}
		return null;
	}

	@Override
	public Object get(Class<?> type, ResultSet rs, int index) throws SQLException {
		String json = rs.getString(index);
		return json == null ? null : fromString(type, json);
	}

	@Override
	public Object get(Class<?> type, ResultSet rs, String columnName) throws SQLException {
		String json = rs.getString(columnName);
		return fromString(type, json);
	}

	@Override
	public Object get(Class<?> type, CallableStatement cs, int index) throws SQLException {
		String json = cs.getString(index);
		return json == null ? null : fromString(type, json);
	}

	@Override
	public Object get(Class<?> type, CallableStatement cs, String parameterName) throws SQLException {
		String json = cs.getString(parameterName);
		return fromString(type, json);
	}

	@Override
	public void set(Class<?> type, PreparedStatement stmt, Object value, int index) throws SQLException {
		String json = toString(value);
		if (json == null) {
			stmt.setNull(index, Types.VARCHAR);
		} else {
			stmt.setString(index, json);
		}
	}

	@Override
	public Class<?> getJavaType(int sqlType) {
		return String.class;
	}

	@Override
	public void registerOutParameter(Class<?> type, CallableStatement cs, int index) throws SQLException {
		cs.registerOutParameter(index, Types.VARCHAR);
	}

	@Override
	public void registerOutParameter(Class<?> type, CallableStatement cs, String parameterName) throws SQLException {
		cs.registerOutParameter(parameterName, Types.VARCHAR);
	}
}
