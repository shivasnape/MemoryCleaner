package com.android.lab.baloonperformer.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.Toast;

import com.android.lab.baloonperformer.R;
import com.android.lab.baloonperformer.memoryCleanUtils.ReleasePhoneMemoryTask;
import com.tt.balloonperformerlibrary.BalloonPerformer;
import com.tt.balloonperformerlibrary.configs.Config;
import com.tt.balloonperformerlibrary.ui.widgets.BalloonGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Config.Builder builder = new Config.Builder(MainActivity.this);
        Config config = builder.pullSensitivity(2.0f).lineLength(14).isOnlyDestop(false).flyDuration(3000).balloonCount(10).create();
        BalloonPerformer.getInstance().init(MainActivity.this, config);

        BalloonPerformer.getInstance().show(this);

        BalloonPerformer.getInstance().show(MainActivity.this, new BalloonGroup.OnBalloonFlyedListener() {
            @Override
            public void onBalloonFlyed() {
                releaseMemory(MainActivity.this);
            }
        });

    }

   /* public void start(View view) {
        BalloonPerformer.getInstance().show(MainActivity.this, new BalloonGroup.OnBalloonFlyedListener() {
            @Override
            public void onBalloonFlyed() {
                releaseMemory(MainActivity.this);
            }
        });
    }

    public void stop(View view) {
        BalloonPerformer.getInstance().gone(MainActivity.this);
    }*/

    /**
     * Release memory <Function Description>
     */
    public static void releaseMemory(final Context context) {
        ReleasePhoneMemoryTask releasePhoneMemoryTask = new ReleasePhoneMemoryTask(
                context) {

            @Override
            protected void onPostExecute(Long result) {
                String s;
                if (result <= 0) {
                    s = "Memory Cleaned!..!";
                } else {
                    s = "\n" +
                            "Already cleaned up for you" + "<font color='#4898eb'>"
                            + Formatter.formatShortFileSize(context, result)
                            + "</font>" + "RAM!";
                }
//                ReleaseToast.showToast(context, Html.fromHtml(s));

                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            }
        };
        releasePhoneMemoryTask.execute();
    }

}
