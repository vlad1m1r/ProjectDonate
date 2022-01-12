package ro.zvlad.donate.service;

import org.springframework.stereotype.Service;
import ro.zvlad.donate.dto.cause.CauseDto;
import ro.zvlad.donate.dto.cause.CauseIdDto;
import ro.zvlad.donate.dto.donation.DonationDto;
import ro.zvlad.donate.dto.donation.DonationIdDto;
import ro.zvlad.donate.exceptions.GeneralException;
import ro.zvlad.donate.model.Cause;
import ro.zvlad.donate.model.Donation;
import ro.zvlad.donate.repo.CauseRepo;
import ro.zvlad.donate.repo.DonationRepo;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;

@Service
public class DonationService {
    private final DonationRepo donationRepo;
    private final CauseRepo causeRepo;

    public DonationService(DonationRepo donationRepo,CauseRepo causeRepo) {
        this.donationRepo = donationRepo;
        this.causeRepo = causeRepo;
    }

    public Donation getDonation(int id) {
        try {
            return donationRepo.getById(id);
        } catch (IndexOutOfBoundsException ex) {
            throw new GeneralException("Donation not found", 404);
        }
    }

    public List<DonationIdDto> getDonations(int causeId,int page){
        return donationRepo.getDonations(causeId,page).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public int addDonation(DonationDto donation){
        return donationRepo.insert(new Donation(donation)).getId();
    }

    public int getDonationsCount(int causeId){
        return donationRepo.getDonationsCount(causeId);
    }

    public boolean deleteDonation(int donationId){return donationRepo.delete(donationId);}

    public boolean updatePaymentData(int id, int paymentStatus, String paymentUrl,String paymentId){
        Donation donation=donationRepo.getById(id);
        if(paymentStatus>=0)
            donation.setStatus(paymentStatus);
        if(paymentUrl!=NULL)
            donation.setPayment_url(paymentUrl);
        if(paymentId!=NULL)
            donation.setPayment_id(paymentId);
        donationRepo.save(donation);
        return true;
    }

    public DonationIdDto mapToDto(Donation donation){
        return new DonationIdDto(donation.getId(), donation.getCause_id(),donation.getAmount(),donation.getCurrency(),donation.getName(),donation.getEmail());
    }


}
