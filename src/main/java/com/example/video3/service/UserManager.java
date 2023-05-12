package com.example.video3.service;

import com.example.video3.document.User;
import com.example.video3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import com.squareup.okhttp.*;
//import org.springframework.web.bind.annotation.RequestBody;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.text.MessageFormat;

@Service
public class UserManager implements UserDetailsManager {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDetails user) {
        ((User) user).setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save((User) user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormat.format("username {0} not found", username)
                ));
    }

    /*private void anything( String string){


        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"collection\":\"<COLLECTION_NAME>\",\n    \"database\":\"<DATABASE_NAME>\",\n    \"dataSource\":\"Cluster0\",\n    \"projection\": {\"_id\": 1}\n\n}");
        Request request = new Request.Builder()
                .url("https://eu-central-1.aws.data.mongodb-api.com/app/data-uzosm/endpoint/data/v1/action/findOne")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Access-Control-Request-Headers", "*")
                .addHeader("api-key", "<API_KEY>")
                .build();
        Response response = client.newCall(request).execute();
    }*/






        public static void findUser(String firstName) {
            String API_KEY = "soLAuuAfFEkWtnCUbT5bJkoWFNM449fXhxrUPKNBNhvS60gork6mcZzl3lhhhoWx";
            String ENDPOINT_URL = "https://eu-central-1.aws.data.mongodb-api.com/app/data-uzosm/endpoint/data/v1/action/findOne";
            String CONTENT_TYPE = "application/json";
            HttpClient httpClient = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost(ENDPOINT_URL);

            // Set headers
            httpPost.setHeader("Content-Type", CONTENT_TYPE);
            httpPost.setHeader("api-key", API_KEY);

            // Set request body
            String requestBody = "{\n"
                    + "    \"dataSource\": \"Cluster0\",\n"
                    + "    \"database\": \"userapp\",\n"
                    + "    \"collection\": \"user\",\n"
                    + "    \"filter\": {\n"
                    + "        \"firstName\": \"" + firstName + "\"\n"
                    + "    }\n"
                    + "}";
            StringEntity requestEntity = new StringEntity(requestBody, "UTF-8");
            httpPost.setEntity(requestEntity);

            try {
                // Execute the request and get the response
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity responseEntity = response.getEntity();

                // Print the response body
                System.out.println(EntityUtils.toString(responseEntity));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       /* public static void main(String [] args){
            findUser("youssef");
        }*/

}
