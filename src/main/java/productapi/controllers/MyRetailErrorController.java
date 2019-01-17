package productapi.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRetailErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError() {
		//Logging
		return "Error - Please try again. :(";
	}
	
    @Override
    public String getErrorPath() {
        return "/error";
    }

}