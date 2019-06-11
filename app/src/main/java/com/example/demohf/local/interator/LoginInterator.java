package com.example.demohf.local.interator;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.example.demohf.local.vo.Result;
import com.example.demohf.local.vo.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginInterator extends AsyncTask<String, Void, Result> {

    @Override
    protected Result doInBackground(String... strings) {
        @Nullable
        String strurl = strings[0];
        String line;
        try {
            URL url = new URL(strurl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            try {
                con.setConnectTimeout(Server.CONNECT_TIME_OUT);
                con.setReadTimeout(Server.READ_TIME_OUT);
                con.setRequestMethod(Server.REQUEST_METHOD_POST);
                con.connect();
            } catch (ConnectException e) {
                e.printStackTrace();
                return new Result("false");
            }//ConnectException end

            InputStreamReader isr = new InputStreamReader(con.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            isr.close();
            con.disconnect();


            try {
                JSONObject jo = new JSONObject(sb.toString());
                return new Result(jo.getString("result"));
            } catch (JSONException e) {
                e.printStackTrace();
                return new Result("false");
            }//JSONException end

        } catch (Exception e) {
            e.printStackTrace();
            return new Result("false");
        } // Exception end

    }//doInBackground end

}//LgoinInterator end
