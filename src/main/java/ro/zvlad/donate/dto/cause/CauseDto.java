package ro.zvlad.donate.dto.cause;

import javax.validation.constraints.*;

public class CauseDto{
        @NotBlank @Size(max = 255,min = 5)
        String name;

        @Size(max = 65000)
        String description;

        @DecimalMin("1000") @Positive
        int target_amount;

        @Pattern(regexp="^(RON|EUR|USD)$")
        String currency;

        @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4])(:([0-5][0-9])){2}$")
        String created_on;

        @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4])(:([0-5][0-9])){2}$")
        String ending_on;

        public CauseDto(String name, String description, int target_amount, String currency, String created_on, String ending_on) {
                this.name = name;
                this.description = description;
                this.target_amount = target_amount;
                this.currency = currency;
                this.created_on = created_on;
                this.ending_on = ending_on;
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
}