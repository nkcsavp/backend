package nkcs.avp.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public static ResponseEntity<String> Response(Integer statusCode, String info) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map.put("msg", info);
        map.put("code", statusCode.toString());
        try {
            return ResponseEntity.status(statusCode).header("Content-Type","application/json;charset=utf-8").body(objectMapper.writeValueAsString(map));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Error");
        }
    }

    public static ResponseEntity<String> Response(String info) {
        return Response(200, info);
    }
}
