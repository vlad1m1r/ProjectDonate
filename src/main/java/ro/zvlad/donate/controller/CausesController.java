package ro.zvlad.donate.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.zvlad.donate.dto.cause.*;
import ro.zvlad.donate.model.Cause;
import ro.zvlad.donate.service.CauseService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

@RequestMapping("/causes")
@RestController
@Validated
public class CausesController {
    private final CauseService causeService;

    public CausesController(CauseService causeService) {
        this.causeService = causeService;
    }

    @GetMapping
    public List<CauseIdDto> getCauses(HttpServletResponse response, @RequestParam(required = false, defaultValue = "1") @Min(1) int page){
        response.addHeader("Access-Control-Allow-Origin","*");
        return causeService.getCauses(page);
    }

    @GetMapping("/count")
    public CountCausesResponse getCauses(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin","*");
        return new CountCausesResponse(causeService.getCausesCount());
    }

}
