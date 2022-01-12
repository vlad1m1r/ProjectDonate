package ro.zvlad.donate;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.zvlad.donate.dto.cause.CauseDto;
import ro.zvlad.donate.dto.donation.DonationDto;
import ro.zvlad.donate.exceptions.GeneralException;
import ro.zvlad.donate.model.Cause;
import ro.zvlad.donate.model.Donation;
import ro.zvlad.donate.service.CauseService;
import ro.zvlad.donate.service.DonationService;

import java.util.HashMap;

@SpringBootTest
public class TestInteg {

    @Autowired
    private CauseService causeService;
    @Autowired
    private DonationService donationService;


    @Test
    public void insertUpdateRetrieveDeleteCause(){

        //insert
        CauseDto cause=new CauseDto("test","test2",1000,"RON","2021-01-01 00:00:00","2023-01-01 00:00:00");
        int insert_id=causeService.addCause(cause);

        //update
        final var partial=new HashMap<String, Object>();
        partial.put("target_amount",20000);
        partial.put("name","tesssssssssssssst");
        causeService.updatePartial(insert_id, partial);

        //retrieve
        Cause cause2=causeService.getCause(insert_id);
        assert(cause2.getId()==insert_id);
        assert(cause2.getDescription().equals(cause.getDescription()));

        assert(cause2.getTarget_amount()==20000);

        //delete
        assert(causeService.removeCause(insert_id));
        try {
            Cause cause3=causeService.getCause(insert_id);
            assert(cause3.getId()<0);
        } catch (GeneralException expectedException) {

        }

    }

    @Test
    public void causeCount(){
        int total1 = causeService.getCausesCount();

        CauseDto cause=new CauseDto("test","test2",1000,"RON","2021-01-01 00:00:00","2023-01-01 00:00:00");
        int insert_id=causeService.addCause(cause);

        int total2 = causeService.getCausesCount();
        causeService.removeCause(insert_id);
        int total3 = causeService.getCausesCount();

        assert(total1==total3);
        assert(total1<total2);
    }


    @Test
    public void donationAddRetrieveUpdateDelete(){
        DonationDto donation = new DonationDto(1,2,"RON","test","test@test.ro");
        int insert_id=donationService.addDonation(donation);

        donationService.updatePaymentData(insert_id,1,NULL,NULL);

        Donation retrieve1=donationService.getDonation(insert_id);
        assert(retrieve1.getId()==insert_id);
        assert(retrieve1.getStatus()==1);

        assert(donationService.deleteDonation(insert_id));
        try {
            Donation retrieve2=donationService.getDonation(insert_id);
            assert(retrieve2.getId()<0);
        } catch (GeneralException expectedException) {

        }

    }



}