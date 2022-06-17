package ru.cleverence.mobilesmarts;

import java.util.HashMap;
import java.util.Map;

public class SkTable {

    private Map<String, String> table = new HashMap<>();

    {
        table.put("4600823525108", "конфеты «Белочка»");
        table.put("4600080467647", "конфеты «Красная Шапочка»");
        table.put("4607005404855", "конфеты «Мишка в лесу»");
    }
    public String getNameByShk(String shk){

        String name = table.get(shk);
        return name;

    }
}
