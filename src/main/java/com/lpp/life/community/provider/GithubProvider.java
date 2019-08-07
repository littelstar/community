package com.lpp.life.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lpp.life.community.dto.AcessTokenDto;
import com.lpp.life.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import sun.nio.cs.US_ASCII;

import java.io.IOException;

/**
 *
 */
@Component
public class GithubProvider {

    public String getAcessToken(AcessTokenDto acessTokenDto){
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(acessTokenDto));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String s = response.body().string();
                String s1 = s.split("&")[0].split("=")[1];
                return s1;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String useString = response.body().string();
            System.out.println(useString);
            GithubUser user = JSON.parseObject(useString,GithubUser.class);
            return  user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
