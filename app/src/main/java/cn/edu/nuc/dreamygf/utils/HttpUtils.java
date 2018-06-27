package cn.edu.nuc.dreamygf.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import cn.edu.nuc.dreamygf.bean.ChatMessage;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 51164 on 2018/5/22.
 */

public class HttpUtils {
    private static final String URL = "http://openapi.tuling123.com/openapi/api/v2";
    private static final String APIKEY = "239607b9d0174644ba2bcac79064c447";
    private static final String USERID = "ef54dc871b1fa075";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "HttpUtils";
    private static String result = null;

    /**
     * 发送消息并得到返回的ChatMessage
     * @param msg
     * @return
     */
    public static ChatMessage sendMessage(String msg) {

        ChatMessage chatMessage = new ChatMessage();
        String message=doPost(msg);
        chatMessage.setMsg(message);
        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOMING);
        return chatMessage;
    }

    public static String doPost(final String msg) {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//            JSONObject perception=new JSONObject();
//            JSONArray perceptionArray=new JSONArray();
//            JSONObject inputText=new JSONObject();
//            inputText.put("inputText",msg);
//            perceptionArray.put(inputText);
//            perception.put("perception",perceptionArray);
//            JSONObject userInfo=new JSONObject();
//            JSONArray userInfoArray=new JSONArray();
//            JSONObject apiKey=new JSONObject();
//            apiKey.put("apiKey",APIKEY);
//            JSONObject userId=new JSONObject();
//            userId.put("userId",USERID);
//            userInfoArray.put(apiKey);
//            userInfoArray.put(userId);
//            userInfo.put("userInfo",userInfoArray);
//            JSONArray toSend=new JSONArray();
//            toSend.put(perception);
//            toSend.put(userInfo);
                    String toSendString = null;
                    try {
                    JSONObject json = new JSONObject();
                    JSONObject perception = new JSONObject();
                    JSONObject inputText = new JSONObject();
                    inputText.put("text", msg);
                    perception.put("inputText", inputText);
                    json.put("perception", perception);
                    JSONObject userInfo = new JSONObject();
                    userInfo.put("apiKey", APIKEY);
                    userInfo.put("userId", USERID);
                    json.put("userInfo", userInfo);
                    toSendString = json.toString();
                    Log.d(TAG, "doPost: " + toSendString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, toSendString);
                Request request = new Request.Builder().url(URL).post(requestBody).build();

                try {
                    Response response = client.newCall(request).execute();
                    String resultString = response.body().string();
                    Log.d(TAG, "run: " + resultString);
                    try {
                        JSONObject jsonObject = new JSONObject(resultString);
                        JSONArray resultsArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < resultsArray.length(); i++) {
                            JSONObject resultObject = resultsArray.getJSONObject(i);
                            String type = resultObject.getString("resultType");
                            Log.d(TAG, "run: " + type);
                            if (type.equals("text")) {
                                result = resultObject.getJSONObject("values").getString("text");
                                break;
                            }
                            //if (type.equals("image")) {
                            //    result = resultObject.getJSONObject("values").getString("image");
                            //}
                        }
                        Log.d(TAG, "run: " + result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, "run: error");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "run: error");
                }
                return result;
            }

      //  }).start();


}