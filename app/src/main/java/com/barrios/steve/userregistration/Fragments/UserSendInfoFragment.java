package com.barrios.steve.userregistration.Fragments;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.barrios.steve.userregistration.Model.UserRegistration;
import com.barrios.steve.userregistration.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class UserSendInfoFragment extends Fragment {

    private static final String USER = "user";
    private static final String TAG = "UserSendInfoFragment";
    private Uri mUri;
    private UserRegistration mUserRegistration;
    private TextView mTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null)
        {
            mUserRegistration = (UserRegistration) getArguments().getParcelable(USER);
            //Toast.makeText(getContext(), mUserRegistration.getEmail(), Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.usersendinfofragment, container, false);

        mTextView = v.findViewById(R.id.user_send_info_fragment_text_view);

        AsyncTask<Void, Void, String> task;
        task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                try {
                    return getUrlString(buildJson());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "went here");
                return null;
            }

            @Override
            protected void onPostExecute(String aVoid) {
                super.onPostExecute(aVoid);
                if(aVoid != null)
                    mTextView.setText(aVoid);
            }
        };
        task.execute();
        return v;
    }

    public static Fragment newInstance(UserRegistration userRegistration)
    {
        Fragment fragment = new UserSendInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER, userRegistration);
        fragment.setArguments(bundle);

        return fragment;
    }

    private String buildJson()
    {
        JSONObject info = new JSONObject();

        try {
            info.put("name", String.format("%s %s %s", mUserRegistration.getFirstName(), mUserRegistration.getMiddlename(), mUserRegistration.getLastName()));
            info.put("email", mUserRegistration.getEmail());
            info.put("password", mUserRegistration.getPassword());
            info.put("zipcode", mUserRegistration.getZipcode());
            info.put("height", String.format("%s %s", mUserRegistration.getHeightFt(), mUserRegistration.getHeightIn()));
            info.put("gender", mUserRegistration.getGender());
            info.put("birthday", mUserRegistration.getBirthday());
            info.put("genderInterest", mUserRegistration.getGenderInterest());
            info.put("minAge", mUserRegistration.getMinage());
            info.put("maxAge", mUserRegistration.getMaxage());
            info.put("race", mUserRegistration.getRace());
            info.put("religion", mUserRegistration.getReligion());

            Bitmap bm = mUserRegistration.getBitmap();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();

// encode byteArray to Base64 string
            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

            info.put("image", encodedImage);

            JSONObject temp = new JSONObject();
            temp.put("registration", info);

            info = temp;

            Log.d(TAG, temp.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return info.toString();
    }

    public String getUrlBytes(String json) throws IOException {

        String postData = "";
        URL url = new URL(Uri.parse("https://external.dev.pheramor.com/").buildUpon().build().toString());
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");

        OutputStream out = null;

        try {

            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
            os.writeBytes(json);

            os.flush();
            os.close();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + "https://external.dev.pheramor.com/");
            }
            else
            {
                //Log.d(TAG, "worked");
            }

            /*OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(json);
            wr.flush();*/
            /*out = new BufferedOutputStream(connection.getOutputStream());

            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(out, "UTF-8"));

            writer.write(json);

            writer.flush();

            writer.close();

            out.close();*/

            //connection.connect();


            /*DataOutputStream outputStream= new DataOutputStream(connection.getOutputStream());
            Log.d(TAG,"failed here");
            outputStream.writeBytes("PostData=" + json);

            outputStream.flush();
            outputStream.close();*/

            InputStream in = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);


            int inputStreamData = inputStreamReader.read();
            while (inputStreamData != -1) {
                char currentData = (char) inputStreamData;
                inputStreamData = inputStreamReader.read();
                postData += currentData;
            }

        } finally {
            connection.disconnect();
        }

        return postData;
    }

    public String getUrlString(String urlSpec) throws IOException {
        return getUrlBytes(urlSpec);
    }
}
