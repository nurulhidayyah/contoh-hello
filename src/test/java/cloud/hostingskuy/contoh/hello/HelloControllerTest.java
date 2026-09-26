package cloud.hostingskuy.contoh.hello;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HelloControllerTest {

    @Test
    void menyapa() {
        assertThat(new HelloController().hello()).containsEntry("pesan", "halo dari Jenkins");
    }
}
