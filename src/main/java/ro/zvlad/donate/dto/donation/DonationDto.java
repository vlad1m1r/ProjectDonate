package ro.zvlad.donate.dto.donation;

import javax.validation.constraints.*;

public class DonationDto {
    private int cause_id;

    @DecimalMin("10") @Positive
    private int amount;

    @Pattern(regexp="^(RON|EUR|USD)$")
    private String currency;

    @NotBlank @Size(max = 255,min = 5)
    private String name;

    @Pattern(regexp = "^(.+)@(\\S+)$")
    private String email;

    public DonationDto(int cause_id, int amount, String currency, String name, String email) {
        this.cause_id = cause_id;
        this.amount = amount;
        this.currency = currency;
        this.name = name;
        this.email = email;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
