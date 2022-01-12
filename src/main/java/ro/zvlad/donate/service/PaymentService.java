package ro.zvlad.donate.service;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ro.zvlad.donate.dto.PaymentResponseDto;
import ro.zvlad.donate.dto.donation.DonationDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.*;
import java.text.*;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private String mid="testaccount";
    private String key="00112233445566778899AABBCCDDEEFF";

    public PaymentResponseDto getPaymentUrl(DonationDto donation, int id, String desc) throws IOException, JSONException {


        Date date = new Date();
        String dateString = new SimpleDateFormat("yyyyMMddHHmmss").format(date);

        Map<String, String> params=new HashMap<String,String>();
        params.put("amount",String.valueOf(donation.getAmount()));
        params.put("curr",donation.getCurrency());
        params.put("invoice_id",String.valueOf(id));
        params.put("order_desc",desc);
        params.put("merch_id",mid);
        params.put("timestamp",dateString);
        params.put("nonce",nonceGen(32));

        ArrayList<String> data = new ArrayList<String>();
        data.add(params.get("amount"));
        data.add(params.get("curr"));
        data.add(params.get("invoice_id"));
        data.add(params.get("order_desc"));
        data.add(params.get("merch_id"));
        data.add(params.get("timestamp"));
        data.add(params.get("nonce"));

        params.put("fp_hash",fp_hash(data,key));
        params.put("generate_epid","1");
        params.put("fname",donation.getName());
        params.put("email",donation.getEmail());
        params.put("ExtraData[successurl]","http://127.0.0.1:8080/donate/response");
        params.put("ExtraData[failedurl]","http://127.0.0.1:8080/donate/response");

        StringBuilder result = new StringBuilder();
        URL url = new URL("https://secure.euplatesc.ro/tdsprocess/tranzactd.php"+httpBuildQuery(params));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        System.out.println(result.toString());
        JSONObject obj = new JSONObject(result.toString());
        if(obj.has("url")) {
            return new PaymentResponseDto(obj.getString("url"), obj.getString("cart_id"));
        }else{
            return new PaymentResponseDto("", "");
        }

    }

    public int getPaymentStatus(WebRequest request){
        ArrayList<String> data = new ArrayList<String>();
        data.add(request.getParameter("amount"));
        data.add(request.getParameter("curr"));
        data.add(request.getParameter("invoice_id"));
        data.add(request.getParameter("ep_id"));
        data.add(request.getParameter("merch_id"));
        data.add(request.getParameter("action"));
        data.add(request.getParameter("message"));
        data.add(request.getParameter("approval"));
        data.add(request.getParameter("timestamp"));
        data.add(request.getParameter("nonce"));
        if(fp_hash(data,key).compareToIgnoreCase(request.getParameter("fp_hash"))==0){
           if(request.getParameter("action").compareTo("0")==0)
           {
               return 1;
           }else{
               return 2;
           }
        }
        return -1;
    }

    private String httpBuildQuery(Map<String, String> params) {

        String encodedURL = params.entrySet().stream()
                .map(entry -> {
                    try {
                        return entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return "";
                })
                .collect(Collectors.joining("&", "?", ""));
        return encodedURL;
    }

    private String nonceGen(int len){
        String AlphaNumericString = "ABCDEF0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    private byte[] hex2byte(String key){
        int len = key.length();
        byte[] bkey = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bkey[i / 2] = (byte) ((Character.digit(key.charAt(i), 16) << 4) + Character.digit(key.charAt(i+1), 16));
        }
        return bkey;
    }

    //RFC2104HMAC
    private String fp_hash(ArrayList s, String key){
        StringBuffer ret = new StringBuffer();
        Formatter formatter = new Formatter();
        String t;
        Integer l;
        for(int i = 0; i < s.size(); i++)
        {
            t = s.get(i).toString().trim();
            if(t.length() == 0)
                ret.append("-");
            else
            {
                l = t.length();
                ret.append(l.toString());
                ret.append(t.toString());
            }
        }
        String data=ret.toString();
        System.out.println(data);
        try
        {
            SecretKeySpec secretKeySpec = new SecretKeySpec(hex2byte(key), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(secretKeySpec);
            for (byte b : mac.doFinal(data.getBytes())) {
                formatter.format("%02x", b);
            }
        }
        catch(InvalidKeyException e){}
        catch (NoSuchAlgorithmException e) {}

        return formatter.toString().toUpperCase();
    }
}
