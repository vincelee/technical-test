package xyz.foobar;

import java.lang.reflect.Field;
import java.util.*;

public class DifFService {


    public List<String> calculate(Object original, Object modified) throws DiffException  {

        List<String> messages = new ArrayList<>();

        if (original == null && modified == null)
            throw new DiffException("Both modified and original cannot be null");

        if (original == null) {
            Req1(null, modified,messages);
        }

        if (modified == null) {
            messages.add("Delete: " + original.getClass().getSimpleName());
        }

        if (modified != null && original != null) {
            messages.add("Update: " + modified.getClass().getSimpleName());
            for (Field field : modified.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value1 = null;
                Object value2 = null;
                try {
                    value1 = field.get(original);
                    value2 = field.get(modified);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (value1 != null || value2 != null) {
                    if (!Objects.equals(value1, value2)) {
                        if (value2 == null) {
                            messages.add("Update: " + field.getName());
                            messages.add("Delete: " + original.getClass().getSimpleName());
                        } else if(field.getName().equalsIgnoreCase("friend") || field.getName().equalsIgnoreCase("pet")){
                            if(value1 == null){
                                messages.add("Update: " + field.getName());
                                Req1(null,value2,messages);
                            }else {
                                messages.addAll(Collections.singleton("Update: " + field.getName()));
                                if(field.getName().equalsIgnoreCase("friend") || field.getName().equalsIgnoreCase("pet")){
                                    for (Field friendField : Objects.requireNonNull(value2).getClass().getDeclaredFields()) {
                                        friendField.setAccessible(true);
                                        Object value11 = null;
                                        Object value22 = null;
                                        try {
                                            value11 = friendField.get(value1);
                                            value22 = friendField.get(value2);
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                        if (!Objects.equals(value11, value22)) {
                                            messages.addAll(Collections.singleton(String.format("Update: %s from %s to %s", friendField.getName(), value11, value22)));
                                        }
                                    }
                                }
                            }
                        }
                        else
                            messages.addAll(Collections.singleton(String.format("Update: %s from %s to %s", field.getName(), value1, value2)));
                    }
                }

            }
        }
        return messages;
    }


    private void Req1(Object original, Object modified, List<String> messages) {
        assert modified != null;
        messages.add("Create: " + modified.getClass().getSimpleName());
        for (Field field : modified.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value1 = null;
            try {
                value1 = field.get(modified);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            messages.addAll(Collections.singleton("Create: " + field.getName() + " as " + value1));
        }
    }
}
