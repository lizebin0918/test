package com.lzb.javers;

import com.alibaba.fastjson.JSON;
import org.javers.core.CommitIdGenerator;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.javers.core.diff.ListCompareAlgorithm.LEVENSHTEIN_DISTANCE;

/**
 * 测试容器<br/>
 * Created on : 2021-11-15 16:09
 *
 * @author lizebin
 */
public class TestCollection {

    public static void main(String[] args) {

        Set<String> set1 = Set.of("1", "2");
        Set<String> set2 = Set.of("2", "1");

        Javers javers = JaversBuilder.javers().withCommitIdGenerator(CommitIdGenerator.RANDOM).build();

        Diff diff = javers.compare(set1, set2);
        System.out.println(JSON.toJSONString(diff.getChanges()));

        List<String> list1 = List.of("1", "2", "3");
        List<String> list2 = List.of("1", "3", "2");

        //System.out.println(Objects.equals(list1, list2));
        //System.out.println(JSON.toJSONString(javers.compare(list1, list2).getChanges()));

        Person p1 = new Person();
        p1.setId(1);
        p1.setName("lizebin");
        p1.setAge(18);
        p1.setAddresseList(Arrays.asList(new Address("天河区")));

        Person p2 = new Person();
        p2.setId(1);
        p2.setName("liangwanyi");
        p1.setAge(18);
        p2.setAddresseList(Arrays.asList(new Address("天河区"), new Address("南沙"), new Address("番禺区")));

        /**
         * There are three main types of Changes:
         *
         * NewObject — when an object is present only in the right graph, 新增对象
         * ObjectRemoved — when an object is present only in the left graph, 删除对象
         * PropertyChange — most common — a changed property (field or getter). 属性改变
         * PropertyChange has the following subtypes:
         *
         * ContainerChange — list of changed items in Set, List or Array,
         * MapChange — list of changed Map entries,
         * ReferenceChange — changed Entity reference,
         * ValueChange — changed Primitive or Value.
         */
        Diff diff1 = JaversBuilder.javers().withListCompareAlgorithm(LEVENSHTEIN_DISTANCE).build().compare(p1, p2);

        System.out.println("changes.size() = " + diff1.getChanges().size());
        for (Change change : diff1.getChanges()) {
            System.out.println("------------------" + change.getClass() + "-----------------------");
            /*System.out.println(JSON.toJSONString(change));
            System.out.println("------------------");*/
            if (change instanceof ValueChange) {
                ValueChange vc = (ValueChange) change;
                /**
                 * {"affectedGlobalId":{"cdoId":1,"typeName":"com.lzb.javers.Person"},"affectedLocalId":1,
                 * "affectedObject":{"addresseList":[{"name":"天河区"}],"age":18,"id":1,"name":"lizebin"},"changeType":"PROPERTY_VALUE_CHANGED",
                 * "commitMetadata":null,"left":18,"propertyAdded":false,"propertyName":"age","propertyNameWithPath":"age","propertyRemoved":false,"propertyValueChanged":true}
                 */
                System.out.println(JSON.toJSONString(vc));
            }
        }
        System.out.println(JSON.toJSONString(diff1.getChanges()));
        System.out.println(diff1.prettyPrint());

    }

}
