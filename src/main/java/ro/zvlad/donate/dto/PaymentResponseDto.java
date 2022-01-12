package ro.zvlad.donate.dto;

public class PaymentResponseDto {
    String url;
    String paymentId;

    public PaymentResponseDto(String url, String paymentId) {
        this.url = url;
        this.paymentId = paymentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
