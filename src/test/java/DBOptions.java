import com.yukens.datasource.options.DB;
import com.yukens.datasource.test.User;
import com.yukens.datasource.utils.ReflectUtils;
import org.junit.Test;


public class DBOptions {

    @Test
    public void dbInstance() {
        for (int i = 0; i < 1; i++) {
            User user = new User();
            user.setAge(20);
            user.setBirthday("2021/01/11");
            user.setId(i+1);
            user.setName("李想"  + i);
            System.out.println(ReflectUtils.Object2Map(user));
            System.out.println(ReflectUtils.getPrimaryKey(user));
        }

    }

    @Test
    public void updateSql() {
        User user = new User();
        user.setAge(20);
        user.setBirthday("2021/01/11");
        user.setId(1);
        user.setName("李想");

        DB.INSTANCE.update(user);
    }

}
