package com.moflying.playground;

import com.fasterxml.jackson.core.type.TypeReference;
import com.moflying.playground.animals.Animal;
import com.moflying.playground.entities.FoodSalesInfo;
import com.moflying.playground.utils.JsonUtil;
import com.moflying.playground.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonPlayground {
    /**
     * 从包含 Map 数据结构的 Json 字符串中，提取出 Map 结构体
     */
    private static void parseJsonMap() {
        String jsonMapString =
                "{\"2016-10-01\": \"30\",\"2016-10-02\":\"51\",\"2016-10-03\":\"68\"}";

        // Result:
        //     2016-10-01: 30
        //     2016-10-02: 51
        //     2016-10-03: 68
        MapPlayground.iterateOverMapV1(parseMapFromJson(jsonMapString));
    }

    /**
     * 从 JSON 字符串中提取 Map 数据结构
     * @param mapStringJson 包含 Map 数据结构的 JSON 字符串
     * @return Map 数据结构
     */
    private static Map<String, String> parseMapFromJson(String mapStringJson) {
        Map<String, String> dailyStatsMap = new HashMap<>();
        if (StringUtil.isEmpty(mapStringJson)) {
            return dailyStatsMap;
        }

        TypeReference<Map<String, String>> typeReference =
                new TypeReference<Map<String, String>>() {};
        dailyStatsMap = JsonUtil.read(mapStringJson, typeReference);

        return dailyStatsMap;
    }

    /**
     * 从包含 List 数据结构的 Json 字符串中，提取出 List 结构体
     */
    private static void parseJsonList1() {
        String jsonListString =
                "[{\"id\":3,\"name\":\"cat\",\"gender\":\"FEMALE\",\"color\":\"YELLOW\"}," +
                        "{\"id\":5,\"name\":\"dog\",\"gender\":\"MAIL\",\"color\":\"BLACK\"}]";

        List<Animal> animalList = JsonUtil.readList(jsonListString, Animal.class);
        // Result:
        //     [{id=3, name=cat, gender=FEMALE, color=YELLOW}, {id=5, name=dog, gender=MAIL, color=BLACK}]
        System.out.println(animalList);
    }

    /**
     * 尝试通过从 JSON 字符串中解析对象列表
     */
    private static void parseJsonList2() {
        String foodBasicVolumeInfoJSON =
                "[{\"foodId\":\"111111\",\"foodName\":\"糖醋里脊\",\"totalSalesVolume\":\"17\"}," +
                        "{\"foodId\":\"222222\",\"foodName\":\"宫保鸡丁\",\"totalSalesVolume\":\"34\"}]";

        List<FoodSalesInfo> foodBasicVolumeInfoList =
                JsonUtil.readList(foodBasicVolumeInfoJSON, FoodSalesInfo.class);

        // Result:
        //     [{foodId=111111, foodName=糖醋里脊, totalSalesVolume=17},
        //      {foodId=222222, foodName=宫保鸡丁, totalSalesVolume=34}]
        System.out.println(foodBasicVolumeInfoList);
    }

    /**
     * 尝试从 JSON 字符串中解析空对象列表（及 JSON 字符串为空对象时报错的情况）
     *
     * - Error if content of json is object but we want to parse list of object
     * - Error if content of json is blank list of object but we want to parse object
     */
    private static void playParseEmptyListOrObject() {
        TypeReference<List<Animal>> typeReference =
                new TypeReference<List<Animal>>() {};

        // Parse list of object from "[]"
        // Result 1:
        //     []
        List<Animal> animalList = JsonUtil.read("[]", typeReference);
        System.out.println("Result 1: " + JsonUtil.write(animalList));

        // Cannot parse list of object from "{}"
        // Result 2:
        //     java.lang.IllegalStateException:
        //         Cannot convert json string to [com.moflying.playground.JsonPlayground$2@224edc67]
        try {
            List<Animal> animalList2 = JsonUtil.read("{}", typeReference);
        } catch (IllegalStateException e) {
            System.out.println("Result 2: " + e);
        }

        // Parse object from "{}"
        // Result 3:
        //     {"id":0,"name":null,"gender":null,"color":null}
        Animal animal = JsonUtil.read("{}", Animal.class);
        System.out.println("Result 3: " + JsonUtil.write(animal));
    }

    public static void main(String[] args) {
//        parseJsonMap();
//        parseJsonList1();
//        parseJsonList2();
        playParseEmptyListOrObject();
    }
}
