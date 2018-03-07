package example.com.mobieplay.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import example.com.mobieplay.MediaItem;
import example.com.mobieplay.R;
import example.com.mobieplay.SystemMedjaPlayer;

/**
 * Created by 14819 on 2018/3/4.
 */

public class Fragment1 extends ListFragment {
    private String TAG = Fragment1.class.getName();
    private ListView list;
    private TextView nomedia;
    private ProgressBar progressBar;
    private ArrayList<MediaItem> mediaItems;
    private Context context=null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mediaItems != null & mediaItems.size() > 0) {
                //有设备设置适配器
                    List<? extends Map<String, ?>> list=getData(mediaItems);
                    SimpleAdapter simpleAdapter=new SimpleAdapter(context,list,R.layout.layoutvideo,new String[]{"name", "duration", "size"},new int[]{R.id.tv_name,R.id.tv_time,R.id.tv_size});
                    nomedia.setVisibility(View.GONE);
                    setListAdapter(simpleAdapter);

            }
            //隐藏progressbar
            progressBar.setVisibility(View.GONE);
            Log.d("Number", mediaItems.size() + "");
        }
    };
    /**
     * @描述 在onCreateView中加载布局
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentvideo, container, false);
        context=getActivity();
        list = (ListView) view.findViewById(android.R.id.list);
        nomedia = (TextView) view.findViewById(R.id.tv_text);
        progressBar=(ProgressBar)view.findViewById(R.id.progressbar);
        Log.i(TAG, "--------onCreateView");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "--------onCreate");
        getDataFromLocal();
        //String[] list = {"Class 1","Class 2","class 3","Class 4","Class 5"};
        //adapter = new SimpleAdapter(getActivity(), getData(list), R.layout.layoutvideo, new String[]{"title"}, new int[]{R.id.title});
        // setListAdapter(adapter);
    }

    private void getDataFromLocal() {
        mediaItems = new ArrayList<>();
        new Thread() {

            @Override
            public void run() {
                ContentResolver resolver = getContext().getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                //String uri1 = "content://media/external/images/media/";
                //Uri uri=Uri.parse(uri1);
                Log.d("Uriname", uri.toString());
                String[] objs = {
                        MediaStore.Video.Media.DISPLAY_NAME,//视频文件在sdcard中的名字
                        MediaStore.Video.Media.DURATION,
                        MediaStore.Video.Media.SIZE,
                        MediaStore.Video.Media.DATA,//SDCARD的据对地址
                        MediaStore.Video.Media.ARTIST,//艺术家
                };
                Cursor cursor = resolver.query(uri, objs, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        MediaItem mediaItem = new MediaItem();
                        String name = cursor.getString(0);
                        mediaItem.setName(name);
                        Log.d("Filename", name);
                        long duration = cursor.getLong(1);
                        mediaItem.setDuration(duration);
                        long size = cursor.getLong(2);
                        mediaItem.setSize(size);
                        String data = cursor.getString(3);
                        mediaItem.setData(data);
                        String artist = cursor.getString(4);
                        mediaItem.setArtist(artist);
                        mediaItems.add(mediaItem);
                    }
                    cursor.close();
                }
                //发消息
                Log.d("num" ,mediaItems.size()+"");
                handler.sendEmptyMessage(0);
            }
        }.start();
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
            //Todo
        MediaItem mediaItem=mediaItems.get(position);
        Intent intent = new Intent(context,SystemMedjaPlayer.class);
        intent.setDataAndType(Uri.parse(mediaItem.getData()), "video/mp4");
        startActivity(intent);
        /*MediaItem mediaItem=mediaItems.get(position);
        Intent intent=new Intent(context, SystemMedjaPlayer.class);
        intent.setDataAndType(Uri.parse(mediaItem.getData()),"video/*");
        context.startActivity(intent);*/
    }


    private List<? extends Map<String, ?>> getData(ArrayList<MediaItem> strs) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < strs.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", strs.get(i).getName());
            map.put("duration",strs.get(i).getDuration());
            map.put("size",strs.get(i).getSize());
            list.add(map);

        }

        return list;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "--------onActivityCreated");

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "----------onAttach");
    }
}


