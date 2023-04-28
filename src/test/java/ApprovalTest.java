import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.approvaltests.JsonApprovals;
import org.approvaltests.approvers.FileApprover;
import org.approvaltests.combinations.CombinationApprovals;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.core.Options;
import org.approvaltests.core.VerifyResult;
import org.json.JSONException;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * <br/>
 * Created on : 2023-01-08 09:49
 * @author mac
 */
@Slf4j
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

	public static String myMethod(int arg) {
		if (arg <= 0) {
			return "less or equal than zero";
		} else if (arg % 2 == 0) {
			return "even";
		} else {
			return "odd";
		}
	}

	@Test
	void test_combination_appraoval() {
		CombinationApprovals.verifyAllCombinations(
				ApprovalTest::myMethod, // 测试方法
				new Integer[] {-1, 0, 1, 2} // 参数列表
		);
	}

	@Test
	void test_combination_appraoval_1() {
		CombinationApprovals.verifyBestCoveringPairs(
				ApprovalTest::myMethod, // 测试方法
				new Integer[] {-1, 0, 1, 2} // 参数列表
		);
	}

	@TestFactory
	Stream<DynamicTest> generateTestCases(){

		Stream<Integer> inputStream = IntStream.range(0, 10).boxed();
		Function<Integer, String> displayName = input -> "Test input: " + input + " should be smaller than 10";
		ThrowingConsumer<Integer> testExecutor = input -> assertTrue(input < 10);

		return DynamicTest.stream(inputStream.iterator(), displayName, testExecutor);
	}

	@TestFactory
	Collection<DynamicTest> dynamicTestsFromCollection() {
		// 自己在外层做笛卡尔乘积？排列组合所有情况，返回一个List
		return Arrays.asList(
				dynamicTest("1st dynamic test", () -> assertTrue(Objects.nonNull(1))),
				dynamicTest("2nd dynamic test", () -> assertTrue(Objects.nonNull(1)))
		);
	}


	@TestFactory
	DynamicContainer dynamicTestsFromStream() {
		return DynamicContainer.dynamicContainer("DynamicContainer",
				Stream.of(
						DynamicTest.dynamicTest("1st container test",
								() -> assertTrue(Objects.nonNull(1))),
						DynamicTest.dynamicTest("2nd container test",
								() -> assertTrue(Objects.nonNull(1))
						)));
	}

	@Test
	void should_compare_with_json() {
		Map<String, String> map1 = Maps.newLinkedHashMap();
		map1.put("2", "2");
		map1.put("1", "1");
		// JsonApprovals.verifyJson(Fastjson2Utils.toJSONString(map1));

		Options options = new Options();
		options = options.withComparator((actualFile, exceptFile) -> {
			if (!exceptFile.exists()) {
				return FileApprover.approveTextFile(actualFile, exceptFile);
			}
			try {
				String actualString = Files.readString(Paths.get(actualFile.getAbsolutePath()));
				String expectString = Files.readString(Paths.get(exceptFile.getAbsolutePath()));
				JSONAssert.assertEquals(expectString, actualString, false);
				return VerifyResult.SUCCESS;
			} catch (IOException | JSONException e) {
				log.info("json断言异常", e);
				return VerifyResult.FAILURE;
			}
		});

		JsonApprovals.verifyJson(JSON.toJSONString(map1), options);

	}


	void should_(ApprovalWriter writer) {

		PersonService personService = mock(PersonService.class);

		ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);

		personService.test(new Person());

		verify(personService).test(argumentCaptor.capture());


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
