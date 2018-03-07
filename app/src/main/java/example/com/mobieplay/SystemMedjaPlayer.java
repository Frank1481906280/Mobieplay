package example.com.mobieplay;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class SystemMedjaPlayer extends AppCompatActivity {
    private VideoView videoView;
    private Uri uri;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        videoView.stopPlayback();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        videoView.destroyDrawingCache();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_medja_player);
        videoView=(VideoView)findViewById(R.id.video_view);
        uri=getIntent().getData();
        videoView.setOnPreparedListener(new MyOnPreparedListener());
        videoView.setOnErrorListener(new MyOnErrorListener());
        videoView.setOnCompletionListener(new MyOnCompletionListener());
        videoView.setMediaController(new MediaController(this));
        if (uri!=null){
            videoView.setVideoURI(uri);
        }
    }
    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
                videoView.start();//开始播放
        }
    }

    private class MyOnErrorListener implements MediaPlayer.OnErrorListener {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            Toast.makeText(SystemMedjaPlayer.this,"播放错误",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText(SystemMedjaPlayer.this,"播放完成",Toast.LENGTH_SHORT).show();
        }
    }
}
