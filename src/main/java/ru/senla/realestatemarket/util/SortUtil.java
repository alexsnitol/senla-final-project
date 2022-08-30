package ru.senla.realestatemarket.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@UtilityClass
public class SortUtil {

    /**
     *
     * @param sortQuery is query in next form: column1,direction;column2,direction
     * <pre>
     * Example 1: firstName,desc
     * </pre>
     * <pre>
     * Example 2: firstName,desc;lastName,desc;balance,asc
     * </pre>
     */
    public static Sort parseSortQuery(String sortQuery) {
        String[] sortParams = sortQuery.split(";");

        List<Sort.Order> orderList = new ArrayList<>(sortParams.length);
        for(String params : sortParams) {
            String[] paramsSplit = params.split(",");

            if (paramsSplit.length != 2) {
                log.warn(
                        "Invalid sort parameters: " + Arrays.toString(paramsSplit) + ". These parameters are skipped."
                );
                continue;
            }

            String property = paramsSplit[0];
            Sort.Direction direction = getSortDirection(paramsSplit[1]);

            orderList.add(new Sort.Order(direction, property));
        }
        return Sort.by(orderList);
    }

    public static Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

}
