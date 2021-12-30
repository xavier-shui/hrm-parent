package cn.xavier.hrm.properties;

import cn.xavier.hrm.AuthApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {AuthApp.class})
@RunWith(SpringRunner.class)
public class OAuth2ClientTest {
    @Autowired
    private OAuth2Client oAuth2Client;

    @Test
    public void testProperties() throws Exception {
        System.out.println(oAuth2Client.getclientDetails(0).getClientId());
        System.out.println(oAuth2Client.getclientDetails(0).getClientSecret());
        System.out.println(oAuth2Client.getclientDetails(1).getClientId());
        System.out.println(oAuth2Client.getclientDetails(1).getClientSecret()
        );
    }
}