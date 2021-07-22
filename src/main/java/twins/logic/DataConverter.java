package twins.logic;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter {
	
	private ObjectMapper jackson;
	
	public DataConverter() {
		this.jackson = new ObjectMapper();
	}
	
	public String marshall(Object value) {
		try {
			return this.jackson
				.writeValueAsString(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public <T> T unmarshall(String json, Class<T> requiredType) {
		try {
			return this.jackson
				.readValue(json, requiredType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
