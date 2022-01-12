package ro.zvlad.donate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ro.zvlad.donate.dto.donation.DonationDto;
import ro.zvlad.donate.dto.donation.DonationIdDto;
import ro.zvlad.donate.dto.report.ReportResponse;
import ro.zvlad.donate.exceptions.GeneralException;
import ro.zvlad.donate.model.Donation;
import ro.zvlad.donate.repo.CauseRepo;
import ro.zvlad.donate.repo.DonationRepo;

import java.util.List;
import java.util.stream.Collectors;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;

@Service
public class ReportService {

    @Autowired
    DonationRepo donationRepo;

    public ReportResponse getTotalDonations(){
        Pair<Integer,Integer> result=donationRepo.getDonationsReport();
        return new ReportResponse(result.getFirst(),result.getSecond());
    }
}
