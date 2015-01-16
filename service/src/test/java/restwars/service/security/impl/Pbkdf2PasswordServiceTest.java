package restwars.service.security.impl;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Pbkdf2PasswordServiceTest {
    @Test
    public void test() throws Exception {
        Pbkdf2PasswordService sut = new Pbkdf2PasswordService(1000);

        String hash = sut.hash("foobar");
        assertThat(sut.verify("foobar", hash), is(true));
        assertThat(sut.verify("baz", hash), is(false));
    }
}