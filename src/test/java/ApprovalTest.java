import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import com.alibaba.fastjson.JSON;
import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

/**
 * <br/>
 * Created on : 2023-01-08 09:49
 * @author mac
 */
public class ApprovalTest {

	@Test
	void testList() {
		String[] names = {"Llewellyn", "James", "Dan", "Jason", "Katrina"};
		Arrays.sort(names);
		Approvals.verifyAll("", names);
	}

	@Test
	void test_json() {
		String[] names = {"Llewellyn", "James", "Dan", "Jason", "Katrina"};
		Approvals.verify(JSON.toJSONString(names));
	}

	@Test
	void test_give_coupon() {
		ByteArrayOutputStream output = new ApprovalUtilities().writeSystemOutToStringBuffer();

		Approvals.verify(SystemOutUtils.convert(output));
	}

}
