import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	void should_test_map() {
		Map<String, String> map = Map.of("a", "a", "b", "b");
		Approvals.verify(map);
	}

	/**
	 * 直接toString()
	 */
	@Test
	void should_test_person() {
		Person p = new Person();
		p.setAge(10);
		p.setName("naem");
		Approvals.verify(p);
	}

	@Test
	void test_json() {
		String[] names = {"Llewellyn", "James", "Dan", "Jason", "Katrina"};
		Approvals.verify(JSON.toJSONString(names));
	}

	@Test
	void should_test_sysout_inline_method() {
		ByteArrayOutputStream output = new ApprovalUtilities().writeSystemOutToStringBuffer();

		PersonService personService = new PersonService();
		personService.test(new Person(1, "name"));

		Approvals.verify(SystemOutUtils.convert(output));
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class Person {
		private int age;
		private String name;
	}

	@Slf4j
	static class PersonService {

		public void test(Person person) {
			System.out.println("this is a person age : " + person.getAge());
		}

	}

}
