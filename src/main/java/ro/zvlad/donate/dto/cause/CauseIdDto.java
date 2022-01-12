package ro.zvlad.donate.dto.cause;

import javax.validation.constraints.*;

public class CauseIdDto {
        int id;
        String name;
        String description;
        int target_amount;
        String currency;
        String created_on;
        String ending_on;

        public CauseIdDto(int id, String name, String description, int target_amount, String currency, String created_on, String ending_on) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.target_amount = target_amount;
                this.currency = currency;
                this.created_on = created_on;
                this.ending_on = ending_on;
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
}