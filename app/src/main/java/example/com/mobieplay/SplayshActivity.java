package example.com.mobieplay;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;


public class SplayshActivity extends AppCompatActivity {
private Handler handler=new Handler();
private List<PermissionItem> permissionItems = new ArrayList<PermissionItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splaysh);
        permissionItems.add(new PermissionItem(Manifest.permission.ACCESS_NETWORK_STATE, "网络", R.drawable.permission_ic_contacts));
        permissionItems.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, "存储", R.drawable.permission_ic_storage));
        HiPermission.create(SplayshActivity.this)
                .permissions(permissionItems)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onDeny(String permission, int position) {
                        onFinish();
                    }

                    @Override
                    public void onGuarantee(String permission, int position) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //延迟两秒执行活动，执行主线程
                                StartActivity();
                            }
                        },2000);
                    }
                });

    }


    private void StartActivity() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        StartActivity();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
