package com.example.mtc_register;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int unicode = 0x1F60B;
    String messageToServer = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv1 = (TextView) findViewById(R.id.textView1);

        TextView v = findViewById(R.id.textView1);
        TextView v1 = findViewById(R.id.textView2);
        TextView v3 = findViewById(R.id.editText);
//        v3.setVisibility(View.VISIBLE);
//        v.setVisibility(View.INVISIBLE);


        final Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                startAskingServer();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 10000);


//        String jsonTest = "{ \n" +
//                "\"emoji1\": \"U+1F617\", \n" +
//                "\"emoji2\": \"U+1F929\", \n" +
//                "\"emoji3\": \"U+1F60D\", \n" +
//                "\"emoji4\": \"U+1F619\", \n" +
//                "\"emoji5\": \"U+1F61C\" \n" +
//                "}";
//        String[] emojies = new String[0];
//        try {
//            JSONObject jo = new JSONObject(jsonTest);
//             emojies = parseJson(jo);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String finalString = "";
//        for(int i = 0; i < emojies.length; i++){
//            finalString += emojies[i]+ " ";
//        }
//        tv1.setText(finalString);

    }
    public void onClickRegister(View v){
        EditText edTest  = (EditText) findViewById(R.id.editText) ;
      //  EditText ed = ((EditText) v);
        messageToServer = edTest.getText().toString();
    }

    private void startAskingServer() {
        final TextView checking = findViewById(R.id.textView);
        final int[] i = {0};
        final DoBackgroundTask task = new DoBackgroundTask();

            task.execute();


//        final TextView textView = (TextView) findViewById(R.id.textView);
//
//
//// Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url ="http://192.168.1.65:8000"; // 8000
//
//// Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        textView.setText("Response is: "+ response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
//            }
//        });
//
//// Add the request to the RequestQueue.
//        queue.add(stringRequest);
    }

    public String getEmojiByUnicode(String unicode){
        unicode = "0x" + unicode.substring(2);
        Integer unc = Integer.decode(unicode);
        return new String(Character.toChars(unc));
    }

    public String[] parseJson(JSONObject jo){
        String str = "emoji";
        String forLoop = "";
        String[] arr_final = new String[jo.length()];
           for(int i = 0; i < jo.length(); i ++){
               forLoop = str + (i+1);
               try {
                   arr_final[i] =  jo.getString(forLoop);
                   arr_final[i] = getEmojiByUnicode(arr_final[i]);
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
           return arr_final;

    }


 class DoBackgroundTask extends AsyncTask<String, String, String> {
     TcpClient clt = null;
     @Override
    protected String doInBackground(String... params) {
        final TextView v =   findViewById(R.id.textView1);
        final TextView checkin = findViewById(R.id.textView);
         final TextView v1 =   findViewById(R.id.textView2);

          clt  = new TcpClient(new TcpClient.OnMessageReceived() {
            @Override
            //here the messageReceived method is implemented
            public void messageReceived(String message) {
                if(message.equals("000")){
                    return;
                }
                v.invalidate();
                v.setText( getUnicodeAsOneStrin(message));
                v1.setText("Your emojis are");

            }


        }, messageToServer);
       clt.run();
//        String response = "";
//        String dataToSend = "";
//       // Log.i("FROM STATS SERVICE DoBackgroundTask", dataToSend);
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost("http://192.168.1.66:8005");
//
//        try {
//            httpPost.setEntity(new StringEntity(dataToSend, "UTF-8"));
//
//            // Set up the header types needed to properly transfer JSON
//            httpPost.setHeader("Content-Type", "application/json");
//            httpPost.setHeader("Accept-Encoding", "application/json");
//            httpPost.setHeader("Accept-Language", "en-US");
//
//            // Execute POST
//            HttpResponse httpResponse = httpClient.execute(httpPost);
//            HttpEntity responseEntity = httpResponse.getEntity();
//            if (responseEntity != null) {
//                response = EntityUtils.toString(responseEntity);
//            } else {
//                response = "{\"NO DATA:\"NO DATA\"}";
//            }
//        } catch (ClientProtocolException e) {
//            response = "{\"ERROR\":" + e.getMessage().toString() + "}";
//        } catch (IOException e) {
//            response = "{\"ERROR\":" + e.getMessage().toString() + "}";
//        }
//        return response;
        return "";
    }
    public String getUnicodeAsOneStrin(String message) {
           String[] arr = message.split(";");
           String res = "";
           for(int i = 0; i < arr.length; i++){
               res = res + " " +  getEmojiByUnicode(arr[i]);
           }
            return res;
    }

    @Override
    protected void onPostExecute(String result) {
                TextView v = findViewById(R.id.textView1);
                TextView v1 = findViewById(R.id.textView2);
        TextView v3 = findViewById(R.id.textView);
        (findViewById(R.id.editText)).setVisibility(View.INVISIBLE);
        (findViewById(R.id.button2)).setVisibility(View.INVISIBLE);
                v1.setVisibility(View.VISIBLE);

                v.setVisibility(View.VISIBLE);
                v3.setVisibility(View.INVISIBLE);

    }
    public String getEmojiByUnicode(String unicode){
        unicode = "0x" + unicode.substring(2);
        Integer unc = Integer.decode(unicode);
        return new String(Character.toChars(unc));
    }
}
}

class TcpClient {

    public static final String TAG = TcpClient.class.getSimpleName();
    public static final String SERVER_IP = "10.100.111.148"; //server IP address
    public static final int SERVER_PORT = 8005;
    // message to send to the server
    private String mServerMessage;
    // sends message received notifications
    private OnMessageReceived mMessageListener = null;
    // while this is true, the server will continue running
    private boolean mRun = false;
    // used to send messages
    private PrintWriter mBufferOut;
    // used to read messages from the server
    private BufferedReader mBufferIn;
    private String messageToServer = "";

    /**
     * Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public TcpClient(OnMessageReceived listener,String message) {
        mMessageListener = listener;
        messageToServer = message;
    }

    /**
     * Sends the message entered by client to the server
     *
     * @param message text entered by client
     */
    public void sendMessage(String message) {
        if (mBufferOut != null && !mBufferOut.checkError()) {
            mBufferOut.println(message);
            mBufferOut.flush();
        }
    }

    /**
     * Close the connection and release the members
     */
    public void stopClient() {

        mRun = false;

        if (mBufferOut != null) {
            mBufferOut.flush();
            mBufferOut.close();
        }

        mMessageListener = null;
        mBufferIn = null;
        mBufferOut = null;
        mServerMessage = null;
    }

    public void run() {

        mRun = true;

        try {
            //here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

            Log.d("TCP Client", "C: Connecting...");

            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, SERVER_PORT);

            try {

                //sends the message to the server
                mBufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                //receives the message which the server sends back
                mBufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                sendMessage("App");
                //in this while the client listens for the messages sent by the server
                while (mRun) {

                    mServerMessage = mBufferIn.readLine();

                    if (mServerMessage != null && mMessageListener != null) {
                        //call the method messageReceived from MyActivity class
                        mMessageListener.messageReceived(mServerMessage);

                        stopClient();
                    }

                }

                Log.d("RESPONSE FROM SERVER", "S: Received Message: '" + mServerMessage + "'");

            } catch (Exception e) {
                Log.e("TCP", "S: Error", e);
            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }

        } catch (Exception e) {
            Log.e("TCP", "C: Error", e);
        }

    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the Activity
    //class at on AsyncTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }

}
