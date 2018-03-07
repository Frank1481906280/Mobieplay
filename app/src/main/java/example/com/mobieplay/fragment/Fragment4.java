package example.com.mobieplay.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.mobieplay.R;
import example.com.mobieplay.SystemMedjaPlayer;

/**
 * Created by 14819 on 2018/3/4.
 */

public class Fragment4 extends ListFragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private String TAG = Fragment2.class.getName();
    private ListView list;
    private int i;
    private TextView nomedia;
    private ProgressBar progressBar;
    private ArrayList<UriData> uriDataArrayList;
    private Context context = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (uriDataArrayList != null & uriDataArrayList.size() > 0) {
                List<? extends Map<String, ?>> list = getData(uriDataArrayList);
                SimpleAdapter simpleAdapter = new SimpleAdapter(context, list, R.layout.layoutnetaudio, new String[]{"text"}, new int[]{R.id.tv_name});
                nomedia.setVisibility(View.GONE);
                setListAdapter(simpleAdapter);
            } else {
                nomedia.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.GONE);
        }
    };


    /**
     * @描述 在onCreateView中加载布局
     */
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentnetvideo, container, false);
        context = getActivity();
        list = (ListView) view.findViewById(android.R.id.list);
        nomedia = (TextView) view.findViewById(R.id.tv_text);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        Log.i(TAG, "--------onCreateView");
        i = (int)(Math.random() * 100);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "--------onCreate");
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        RequestVideoDataFromNet();
        //new MyTask().execute(PATH);
    }

    private void RequestVideoDataFromNet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL("https://www.apiopen.top/satinApi?type="+i+"&page="+i);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(0);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setReadTimeout(5000);
                    InputStream in = httpURLConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    analysisJsonData(response.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private void analysisJsonData(String string) {
        uriDataArrayList = new ArrayList<UriData>();
        try {
            JSONObject Object = new JSONObject(string);
            String Data = Object.getString("data");
            JSONArray jsonArray = new JSONArray(Data);
            for (int i = 0; i < jsonArray.length(); i++) {
                UriData uriData = new UriData();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int type=jsonObject.getInt("type");
                if (type==29){
                    String text = jsonObject.getString("text");
                    uriData.setText(text);
                    uriDataArrayList.add(uriData);
                }
            }
            Log.d("Number", uriDataArrayList.size() + "");
            //Log.d("DataFromNet",Data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Todo

    }


    private List<? extends Map<String, ?>> getData(ArrayList<UriData> strs) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < strs.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", strs.get(i).getText());
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


