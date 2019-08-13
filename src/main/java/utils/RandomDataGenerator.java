package utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class RandomDataGenerator {

    public static String randomize(String value) {
        if (StringUtils.isNumeric(value)){
            int max = 9;
            int min = 2;
            return ((new Random().nextInt(max - min + 1) + min)+randomNumeric(value.length()-1)).replace('1', '2').replace('0', '3'); // .replace('1', '2') - for phone numbers
        }
        String at = "@";
        if (value.contains(at)){
            String [] splited = value.split(at);
            return splited[0]+ getRandomNumberString() + at + splited[1];
        }

        return value + getRandomNumberString();
    }

    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return String.format("%06d", number);
    }


}
