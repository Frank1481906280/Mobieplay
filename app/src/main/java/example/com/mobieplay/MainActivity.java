package example.com.mobieplay;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import example.com.mobieplay.fragment.Fragment1;
import example.com.mobieplay.fragment.Fragment2;
import example.com.mobieplay.fragment.Fragment3;
import example.com.mobieplay.fragment.Fragment4;


public class MainActivity extends FragmentActivity {

    private FrameLayout fl_main_content;
    private RadioGroup rg_bottom_tag;
    private  int position;
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
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
        setContentView(R.layout.activity_main);
        fl_main_content=(FrameLayout) findViewById(R.id.fl_main_content);
        rg_bottom_tag=(RadioGroup) findViewById(R.id.rg_bottom_tag);

        //设置radiogroup的监听
        rg_bottom_tag.setOnCheckedChangeListener(new MyOncheckedChangeListener());
        rg_bottom_tag.check(R.id.rb_video);//默认选中第一个
    }

    class MyOncheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                default:
                    position=0;break;
                case R.id.rb_audio:
                    position=1;break;
                case R.id.rb_net_video:
                    position=2;break;
                case R.id.rb_net_audio:
                    position=3;break;
            }
            setFragment(position);
        }
    }

    private void setFragment(int i) {
        switch (i){
            default:
                FragmentManager manager=getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction=manager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_main_content,new Fragment1());
                fragmentTransaction.commit();
                break;
            case 1:
                FragmentManager manager1=getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction ft1=manager1.beginTransaction();
                ft1.replace(R.id.fl_main_content,new Fragment2());
                ft1.commit();
                break;
            case 2:
                FragmentManager manager2=getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction ft2=manager2.beginTransaction();
                ft2.replace(R.id.fl_main_content,new Fragment3());
                ft2.commit();

                break;
            case 3:
                FragmentManager manager3=getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction ft3=manager3.beginTransaction();
                ft3.replace(R.id.fl_main_content,new Fragment4());
                ft3.commit();
                break;
        }

    }
}




