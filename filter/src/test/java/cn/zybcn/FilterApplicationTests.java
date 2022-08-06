package cn.zybcn;

import cn.zybcn.facade.FilteringHandler;
import cn.zybcn.filter.Filter;
import cn.zybcn.startategy.OrderedFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class FilterApplicationTests {

    @Test
    void contextLoads() {

        Filter<String> stringFilter = (e, chain)->{
            System.out.println("执行过滤器1");
            List<String> collect = e.stream().filter((data) -> data.endsWith("a")).collect(Collectors.toList());
            return chain.filter(collect);
        };

        Filter<String> stringFilter1 = (e,chain)->{
            System.out.println("执行过滤器2");
            List<String> collect = e.stream().filter((data) -> data.startsWith("b")).collect(Collectors.toList());
            return chain.filter(collect);
        };


        OrderedFilter<String> stringOrderedFilter = new OrderedFilter<>( (e,chain)->{
            System.out.println("执行过滤器3");
            List<String> collect = e.stream().filter((data) -> data.startsWith("c")).collect(Collectors.toList());
            return chain.filter(collect);
        }, 2);

        ArrayList<String> objects = new ArrayList<>();
        objects.add("a");
        objects.add("b");
        objects.add("c");
        objects.add("d");

        Collection<String> handle =
                FilteringHandler.<String>createHandler(stringFilter, stringFilter1,stringOrderedFilter).handle(objects);
        System.out.println(handle);
    }

}
