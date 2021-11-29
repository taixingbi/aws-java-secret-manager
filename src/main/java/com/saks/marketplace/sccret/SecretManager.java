package com.saks.marketplace.sccret;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

import java.util.Arrays;

public class SecretManager {
    static final String secretName = "secretName";
    static final String secretKeyUser = "secretKeyUser";
    static final String secretKeyPassword = "secretKeyPassword";

    public static String[] process(){
        System.out.println("SecretManager");

        Region region = Region.US_EAST_1;
        SecretsManagerClient secretsClient = SecretsManagerClient.builder()
            .region(region)
            .build();

        String[] secrets = getValue(secretsClient);
        secretsClient.close();
        System.out.println("secrets: " + Arrays.toString(secrets));
        return secrets;
    }

    public static String[] getValue(SecretsManagerClient secretsClient) {
        String[] secrets = new String[2];
        try {
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

            GetSecretValueResponse valueResponse = secretsClient.getSecretValue(valueRequest);
            String secretString = valueResponse.secretString();
            Gson gson = new Gson();
            JsonElement element = gson.fromJson(secretString, JsonElement.class);
            JsonObject jsonObject = element.getAsJsonObject();
            secrets[0] = jsonObject.get(secretKeyUser).getAsString();
            secrets[1] = jsonObject.get(secretKeyPassword).getAsString();
        } catch (SecretsManagerException e) {
            System.out.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return secrets;
    }
}
