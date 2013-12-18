package pl.kwi.validators;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class InputValidator {
	
	public Map<String, String> getErrorMessages(HttpServletRequest request) {
		
		Map<String, String> errorMessages = new HashMap<String, String>();
		
		String name = request.getParameter("name");
		if(StringUtils.isBlank(name)) {
			errorMessages.put("name", "Please fill this field");
		}
		
		return errorMessages;
		
	}

}
