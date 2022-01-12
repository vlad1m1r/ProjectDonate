package ro.zvlad.donate.dto.donation;

public class DonationIdDto {
    private int id;
    private int cause_id;
    private int amount;
    private String currency;
    private String name;
    private String email;

    public DonationIdDto(int id,int cause_id, int amount, String currency, String name, String email) {
        this.id = id;
        this.cause_id = cause_id;
        this.amount = amount;
        this.currency = currency;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
