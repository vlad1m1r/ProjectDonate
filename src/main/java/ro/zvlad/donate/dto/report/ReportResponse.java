package ro.zvlad.donate.dto.report;

public class ReportResponse {
    int total_number;
    int total_amount;

    public ReportResponse(int total_number, int total_amount) {
        this.total_number = total_number;
        this.total_amount = total_amount;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }
}
