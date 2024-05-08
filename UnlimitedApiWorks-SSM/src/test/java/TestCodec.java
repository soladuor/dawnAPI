import com.soladuor.utils.crypto.AnalogCryptoJS;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class TestCodec {
    @Test
    public void test1() {
        String str = myPath("", 1);
        System.out.println(str);
    }

    public String myPath(String str, int times) {
        for (int i = 0; i < times; i++) {
            str = DigestUtils.md5Hex(str).toUpperCase();
        }
        return str;
    }

    @Test
    public void testAES() {
        String plaintext = "Hello, world";
        String passphrase = "dTUvPrClrXwho&%q]N+*ZDF*O]OZAo";
        String encrypt = AnalogCryptoJS.encrypt_AES_CBC(plaintext, passphrase);
        System.out.println("hello-enc = " + encrypt); // U2FsdGVkX18zrOB/HZlV5DeZirUDqu5lTvRh7iXf6nM=
        String decrypt = AnalogCryptoJS.decrypt_AES_CBC(encrypt, passphrase);
        System.out.println("hello-dec = " + decrypt); // Hello, world
    }

}
