package br.com.temwifi.domains.infra.utils.factory;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class DynamoDBClientFactory {
	
	public static AmazonDynamoDB getClient() {

        return  AmazonDynamoDBClientBuilder.standard().withRegion(Regions.SA_EAST_1).build();
    }
}