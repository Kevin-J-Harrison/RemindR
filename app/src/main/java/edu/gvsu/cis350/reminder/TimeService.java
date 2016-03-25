package edu.gvsu.cis350.reminder;

        import android.app.Service;
        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Handler;
        import android.os.IBinder;
        import android.view.Gravity;
        import android.view.View;
        import android.widget.TextView;
        import android.widget.Toast;
        import java.util.Timer;
        import java.util.TimerTask;

public class TimeService extends Service{

    public static final long INTERVAL = 15000 ; // 15 seconds

    private Handler mHandler = new Handler();

    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        if(mTimer != null) {
            mTimer.cancel();
        } else {
            mTimer = new Timer();
        }
        // schedule toast
        mTimer.schedule(new DisplayToast(), INTERVAL);
    }

    class DisplayToast extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {

                    Toast toast = Toast.makeText(getApplicationContext(), "Reminder Time", Toast.LENGTH_LONG);
                    View toastView = toast.getView();

                    TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                    toastMessage.setTextSize(25);
                    toastMessage.setTextColor(Color.GREEN);
                    toastMessage.setGravity(Gravity.CENTER);
                    toast.show();

                }

            });
        }
    }
}