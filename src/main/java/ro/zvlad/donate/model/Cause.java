package ro.zvlad.donate.model;

import ro.zvlad.donate.dto.cause.CauseDto;

import java.util.Objects;

public class Cause {
    private int id;
    private String name;
    private String description;
    private int target_amount;
    private String currency;
    private String created_on;
    private String ending_on;

    public Cause(int id, String name, String description, int target_amount, String currency, String created_on, String ending_on) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.target_amount = target_amount;
        this.currency = currency;
        this.created_on = created_on;
        this.ending_on = ending_on;
    }

    public Cause(CauseDto cause) {
        this.id = 0;
        this.name = cause.getName();
        this.description = cause.getDescription();
        this.target_amount = cause.getTarget_amount();
        this.currency = cause.getCurrency();
        this.created_on = cause.getCreated_on();
        this.ending_on = cause.getEnding_on();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cause cause = (Cause) o;
        return target_amount == cause.target_amount && name.equals(cause.name) && description.equals(cause.description) && currency.equals(cause.currency) && created_on.equals(cause.created_on) && ending_on.equals(cause.ending_on);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, target_amount, currency, created_on, ending_on);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTarget_amount() {
        return target_amount;
    }

    public void setTarget_amount(int target_amount) {
        this.target_amount = target_amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getEnding_on() {
        return ending_on;
    }

    public void setEnding_on(String ending_on) {
        this.ending_on = ending_on;
    }

    @Override
    public String toString() {
        return "Cause{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", target_amount=" + target_amount +
                ", currency='" + currency + '\'' +
                ", created_on='" + created_on + '\'' +
                ", ending_on='" + ending_on + '\'' +
                '}';
    }
}
