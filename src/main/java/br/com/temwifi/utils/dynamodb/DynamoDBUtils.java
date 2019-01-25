package br.com.temwifi.utils.dynamodb;

public class DynamoDBUtils {

    public static String concatFilterExpression(String atual, String nova) {
        if(atual == null) {
            return nova;
        }

        return atual.concat(" AND ").concat(nova);
    }
}
