package ro.zvlad.donate.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.zvlad.donate.dto.cause.*;
import ro.zvlad.donate.model.Cause;
import ro.zvlad.donate.service.CauseService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

@RequestMapping("/cause")
@RestController
public class CauseController {
    private final CauseService causeService;

    public CauseController(CauseService causeService) {
        this.causeService = causeService;
    }

    @GetMapping("/active")
    public List<CauseIdDto> getCauses(@RequestParam @Positive int page){
        return causeService.getCauses(page);
    }

    @GetMapping("/{causeID}")
    public Cause getCause(@PathVariable("causeID") int id){
        return causeService.getCause(id);
    }

    @PostMapping
    public AddCauseResponse addCause(@RequestBody @Valid CauseDto cause){
        return new AddCauseResponse(causeService.addCause(cause));
    }

    @PatchMapping("/{causeID}")
    public Cause updateCause(@PathVariable("causeID") int id, @RequestBody Map<String, Object> data) {
        return causeService.updatePartial(id, data);
    }

    @DeleteMapping("/{causeID}")
    public RemoveCauseResponse addCause(@PathVariable("causeID") int id){
        return new RemoveCauseResponse(causeService.removeCause(id));
    }

    @GetMapping("/{causeID}/total")
    public CountCausesResponse getCausesTotalAmount(HttpServletResponse response,@PathVariable("causeID") int id){
        response.addHeader("Access-Control-Allow-Origin","*");
        return new CountCausesResponse(causeService.getCausesTotalAmount(id));
    }
}
