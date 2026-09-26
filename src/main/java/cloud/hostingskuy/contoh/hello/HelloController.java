package cloud.hostingskuy.contoh.hello;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloController {

    @GetMapping("/")
    Map<String, String> hello() {
        return Map.of("pesan", "kode rusak");
    }
}
