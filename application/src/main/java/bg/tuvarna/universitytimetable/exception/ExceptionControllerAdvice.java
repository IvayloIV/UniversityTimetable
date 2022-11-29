package bg.tuvarna.universitytimetable.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ValidationException.class)
    public ModelAndView handleValidationException(ValidationException ex){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("view", ex.getViewName());
        modelAndView.setViewName("fragments/base-layout");

        Map<String, Object> models = ex.getModels();
        if (models != null) {
            ex.getModels().forEach(modelAndView::addObject);
        }

        return modelAndView;
    }
}
