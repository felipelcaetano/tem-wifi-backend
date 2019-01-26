package br.com.temwifi.domains.infra.utils.factory;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class DynamoDBClientFactory {

    /**
     * Gerenate an AmazonDynamoDB client to be used by other classes
     *
     * @return      an AmazonDynamoDB client
     */
	public static AmazonDynamoDB getClient() {

        return  AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    }
}