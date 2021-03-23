import com.bjpowernode.crm.utils.MD5Util;
import org.junit.Test;

public class MyTest {
    @Test
    public static void main(String[] args) {
        String pwd = "15182650761";
        String aa = MD5Util.getMD5(pwd);
        System.out.println(aa);
    }
}
