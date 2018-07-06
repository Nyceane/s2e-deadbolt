package com.demo.s2elock;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cheekiat.slideview.SlideView;

public class MainActivity extends AppCompatActivity {

    TcpClient mTcpClient;
    SlideView mSlideView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlideView = (SlideView) findViewById(R.id.slide_view);
        mSlideView.setOnFinishListener(new SlideView.OnFinishListener() {
            @Override
            public void onFinish() {
                //someting to do
                if(mTcpClient != null)
                {
                    mTcpClient.sendMessage("1");
                    mSlideView.reset();
                }
            }
        });

    }

    protected void onResume() {
        super.onResume();
        if(mTcpClient == null)
        {
            new ConnectTask().execute("");
        }
    }

    protected void onPause() {
        super.onPause();
        if (mTcpClient != null) {
            mTcpClient.stopClient();
            mTcpClient = null;
        }
    }


    public class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {

            //we create a TCPClient object
            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    //publishProgress(message);
                }
            });
            mTcpClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            Log.d("test", "response " + values[0]);
            //process server response here....

        }
    }
}
