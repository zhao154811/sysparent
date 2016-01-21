package test;

import com.wenyu.oauth.model.User;
import com.wenyu.oauth.service.NewUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableWebMvc
@ContextConfiguration(value = {"classpath:applicationcontext-consumer.xml"})
public class DubboTest {
    @Resource
    private NewUserService dubboUserService;

    @Test
    public void dubboTest() throws UnsupportedEncodingException {
        dubboUserService.login(new User(), "test");
    }


}