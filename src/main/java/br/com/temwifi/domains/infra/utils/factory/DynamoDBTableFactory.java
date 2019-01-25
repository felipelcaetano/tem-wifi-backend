package br.com.temwifi.domains.infra.utils.factory;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Table;

public class DynamoDBTableFactory {

    public static Table getTable(String tableName) {

        return new DynamoDB(DynamoDBClientFactory.getClient()).getTable(tableName);
    }

    public static Index getIndex(String tableName, String indexName) {

        return new DynamoDB(DynamoDBClientFactory.getClient()).getTable(tableName).getIndex(indexName);
    }
}
