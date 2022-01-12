package ro.zvlad.donate.model;

import ro.zvlad.donate.dto.cause.CauseDto;
import ro.zvlad.donate.dto.donation.DonationDto;

import java.util.Objects;

public class Donation {
    private int id;
    private int cause_id;
    private int amount;
    private String currency;
    private String name;
    private String email;
    private int status;
    private String payment_url;
    private String payment_id;
    private String timestamp;

    public Donation(int id,int cause_id,int amount, String currency, String name, String email, int status, String payment_url, String payment_id, String timestamp) {
        this.id = id;
        this.cause_id = cause_id;
        this.amount = amount;
        this.currency = currency;
        this.name = name;
        this.email = email;
        this.status = status;
        this.payment_url = payment_url;
        this.payment_id = payment_id;
        this.timestamp = timestamp;
    }

    public Donation(DonationDto donation) {
        this.id = 0;
        this.cause_id = donation.getCause_id();
        this.amount = donation.getAmount();
        this.currency = donation.getCurrency();
        this.name = donation.getName();
        this.email = donation.getEmail();
        this.status = 0;
        this.payment_url = "";
        this.payment_id = "";
        this.timestamp = "";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cause_id, amount, currency, name, email, status, payment_url, payment_id, timestamp);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getCause_id() {
        return cause_id;
    }

    public void setCause_id(int cause_id) {
        this.cause_id = cause_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPayment_url() {
        return payment_url;
    }

    public void setPayment_url(String payment_url) {
        this.payment_url = payment_url;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", cause_id=" + cause_id +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", payment_url='" + payment_url + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
