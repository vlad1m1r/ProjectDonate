package ro.zvlad.donate.controller;

import org.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ro.zvlad.donate.dto.PaymentResponseDto;
import ro.zvlad.donate.dto.cause.AddCauseResponse;
import ro.zvlad.donate.dto.cause.CauseDto;
import ro.zvlad.donate.dto.cause.CauseIdDto;
import ro.zvlad.donate.dto.cause.CountCausesResponse;
import ro.zvlad.donate.dto.donation.AddDonationResponse;
import ro.zvlad.donate.dto.donation.DonationDto;
import ro.zvlad.donate.dto.donation.DonationIdDto;
import ro.zvlad.donate.repo.CauseRepo;
import ro.zvlad.donate.service.CauseService;
import ro.zvlad.donate.service.DonationService;
import ro.zvlad.donate.service.PaymentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;

@RestController
public class DonationController {
    private final DonationService donationService;
    private final PaymentService paymentService;
    public DonationController(DonationService donationService,PaymentService paymentService) {
        this.donationService = donationService;
        this.paymentService = paymentService;
    }

    @ResponseBody
    @PostMapping(value = "/donate", produces = MediaType.APPLICATION_JSON_VALUE)
    public AddDonationResponse donationAdd(@RequestBody @Valid DonationDto donation) throws IOException, JSONException {
        int insert_id=donationService.addDonation(donation);
        PaymentResponseDto responseDto=paymentService.getPaymentUrl(donation,insert_id,"Donatie online");

        donationService.updatePaymentData(insert_id,-1,responseDto.getUrl(),responseDto.getPaymentId());

        return new AddDonationResponse(responseDto.getUrl());
    }

    @PostMapping(value = "/donate/response", produces = MediaType.TEXT_HTML_VALUE)
    public String donationResponse(WebRequest request){
        int status=paymentService.getPaymentStatus(request);
        if(status<0){
            return "Invalid hash";
        }
        donationService.updatePaymentData(
                Integer.parseInt(request.getParameter("invoice_id")),
                status,
                NULL,
                request.getParameter("ep_id")
        );
        if(status==1){
            return "<meta http-equiv=\"refresh\" content=\"0;url=/donation/success\" />";
        }
        return "<meta http-equiv=\"refresh\" content=\"0;url=/donation/fail\" />";
    }

    @GetMapping(value = "/donation/{donationID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DonationIdDto getDonation(@PathVariable(value="donationID") int donationID){
        return donationService.mapToDto(donationService.getDonation(donationID));
    }

    @GetMapping(value = "/donations/{causeID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DonationIdDto> getDonations(@PathVariable(value="causeID") int causeID,@RequestParam(required = false, defaultValue = "1") @Min(1) int page){
        return donationService.getDonations(causeID,page);
    }

    @GetMapping(value = "/donations/{causeID}/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public CountCausesResponse getDonations(@PathVariable(value="causeID") int causeID){
        return new CountCausesResponse(donationService.getDonationsCount(causeID));
    }

}
