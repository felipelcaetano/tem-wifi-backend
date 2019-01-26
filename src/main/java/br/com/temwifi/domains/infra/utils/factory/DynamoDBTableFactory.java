package br.com.temwifi.domains.infra.utils.factory;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Table;

public class DynamoDBTableFactory {

    /**
     * Generate an instance of DynamoDB Table based on DynamoDBClientFactory and the given name
     *
     * @param tableName
     * @return              an instance of Table
     */
    public static Table getTable(String tableName) {

        return new DynamoDB(DynamoDBClientFactory.getClient()).getTable(tableName);
    }

    /**
     * Generate an instance of DynamoDB Index based on DynamoDBClientFactory and the given name and index
     *
     * @param tableName
     * @return              an instance of Index
     */
    public static Index getIndex(String tableName, String indexName) {

        return new DynamoDB(DynamoDBClientFactory.getClient()).getTable(tableName).getIndex(indexName);
    }
}
