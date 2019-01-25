package br.com.temwifi.domains.infra.utils.factory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DynamoDBMapperFactory {
	
	public static DynamoDBMapper getMapper() {

        AmazonDynamoDB client = DynamoDBClientFactory.getClient();
        return new DynamoDBMapper(client);
    }
}