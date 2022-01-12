package ro.zvlad.donate.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.zvlad.donate.exceptions.GeneralException;
import ro.zvlad.donate.model.Cause;
import ro.zvlad.donate.model.Donation;
import ro.zvlad.donate.service.CauseService;
import ro.zvlad.donate.service.DonationService;

@Controller
public class HomeController {
    DonationService donationService;
    CauseService causeService;
    HomeController(CauseService causeService,DonationService donationService){
        this.causeService=causeService;
        this.donationService=donationService;
    }
    @GetMapping(value = "/")
    public String donationFormView(Model model){
        model.addAttribute("title","Doneaza pentru cauzele noastre");
        return "view";
    }

    @GetMapping(value = "/donation/success")
    public String donationSuccess(Model model){
        model.addAttribute("message","Multumim pentru donatie");
        model.addAttribute("color","green");
        return "result";
    }

    @GetMapping(value = "/donation/fail")
    public String donationFail(Model model){
        model.addAttribute("message","Din pacate plata a esuat");
        model.addAttribute("color","red");
        return "result";
    }

    @GetMapping(value = "/donate/{causeID}")
    public String donationFormDonate(Model model,@PathVariable(value="causeID") int causeID){

        model.addAttribute("title","Doneaza pentru cauzele noastre");

        try {
            Cause cause = causeService.getCause(causeID);
            model.addAttribute("cause",cause);
        }catch (GeneralException ex){
            model.addAttribute("error","Cauza nu a fost gasita");
            return "donate";
        }

        return "donate";
    }


}
