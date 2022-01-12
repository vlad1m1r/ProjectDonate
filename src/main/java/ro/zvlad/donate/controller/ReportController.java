package ro.zvlad.donate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.zvlad.donate.dto.report.ReportResponse;
import ro.zvlad.donate.service.ReportService;

@RestController
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/report")
    public ReportResponse getTotalDonations(){
        return reportService.getTotalDonations();
    }
}
