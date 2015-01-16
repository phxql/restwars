package restwars.service.security.impl;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Pbkdf2PasswordStorageTest {
    @Test
    public void test() throws Exception {
        Pbkdf2PasswordStorage sut = new Pbkdf2PasswordStorage();

        String hash = sut.store("foobar");
        assertThat(sut.verify("foobar", hash), is(true));
        assertThat(sut.verify("baz", hash), is(false));
    }
}