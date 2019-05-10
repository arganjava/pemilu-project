import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class RestTest {


    @Test
    public void getEmployees()
    {

        int e = 2;
        int c = 3;
        System.out.println(e-c);


//        final String uri = "https://pemilu2019.kpu.go.id/static/json/wilayah/0.json";
//
//        RestTemplate restTemplate = new RestTemplate();
//
//       // final String uri = "http://localhost:8080/webservices/REST/sample/country";
//        String input = "US";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> request = new HttpEntity<String>(input, headers);
//        String result = restTemplate.postForObject(uri, request, String.class);

       // String result = restTemplate.getForObject(uri, String.class);

       // System.out.println(result);
    }




}
