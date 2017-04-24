/**
 * LiveWallpaper Launcher And Preference Template
 * The MIT License
 * copyright (c) 2011 mdlab.jp 
 * @author itoz
 */
package jp.venus.vtlwhtsy01801;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Preference extends Activity implements OnClickListener {
	
	//private static final String TAG = null;
	int resource_name = R.drawable.siteimage;
    int WC;
    private Bitmap bmp;
    
    private boolean mIsPremium = false;
    private boolean mIsSubscriber = false;
    
    private String[] data = {"Google Play"};
    //private String[][] listdata = { { "no", "Google Play" }, { "name", "Google Playで購入する" } };
    private String[] subdata = {"Google Playで購入する"};
    
    @SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//setupBilling();
		
	    //ListView liset = new ListView(this);
	        //setContentView(liset);
	        //      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        //            android.R.layout.simple_list_item_1, mStrings);
	        //  liset.setAdapter(adapter);

		
		 LinearLayout linearLayout = new LinearLayout(this);
	        linearLayout.setOrientation(LinearLayout.VERTICAL);
	        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
	        setContentView(linearLayout);

/**
	        // アプリ内課金システムテキスト
	        TextView textBilling = new TextView(this);
	        textBilling.setText("有料版の購入");
	        textBilling.setBackgroundColor(Color.parseColor("#666666"));
	        textBilling.setTextColor(Color.WHITE);
	        linearLayout.addView(textBilling);

	        // アプリ内課金システム　機能ボタン
	        Button button2 = new Button(this);
	        button2.setText("Google Play");
	        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
	        		new LinearLayout.LayoutParams(
	        				LinearLayout.LayoutParams.WRAP_CONTENT,
	        				LinearLayout.LayoutParams.WRAP_CONTENT));
	        button2.setLayoutParams(param);
	        param.setMargins(0, 10, 0, 10);
	        button2.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                // クリック時の処理
	            	//setupBilling();
	            }
	        });
	        linearLayout.addView(button2);
**/
	        // サイトテキスト
	        TextView textview = new TextView(this);
	        textview.setText("Information");
	        textview.setBackgroundColor(Color.parseColor("#666666"));
	        textview.setTextColor(Color.WHITE);
	        linearLayout.addView(textview);

	        //入れ子のレイアウトを作成
	        LinearLayout inLinearLayout = new LinearLayout(this);
	        inLinearLayout.setOrientation(LinearLayout.VERTICAL);
	        inLinearLayout.setGravity(Gravity.CENTER);
	        
	        // WindowManager取得
	        WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
	        // Displayインスタンス生成
	        Display dp = wm.getDefaultDisplay();
	        // ディスプレイサイズ取得
	        float dp_w = dp.getWidth();
	        float dp_h = dp.getHeight();
	        // リサイズ画像の高さ
	        //int drow_h = (dp_w / 2) * 3;
	        // 描画始点の高さ
	        //int drow_s = (dp_h - drow_h) / 2;
	        
	        //Log.i("dp_w",":"+dp_w);
	        //Log.i("dp_h",":"+dp_h);
	        //Log.i("drow_h",":"+drow_h);
	        //Log.i("drow_s",":"+drow_s);
	        
	        final int WC = ViewGroup.LayoutParams.WRAP_CONTENT; 

            //読み込み用のオプションオブジェクトを生成
            BitmapFactory.Options options = new BitmapFactory.Options();
            //この値をtrueにすると実際には画像を読み込まず、
            //画像のサイズ情報だけを取得することができます。
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;

            BitmapFactory.decodeResource(getResources(),resource_name, options);
            int optWidth = options.outWidth;
            int optHeight = options.outHeight;
	        	//Log.i("OrgImg.width",":"+optWidth);
	        	//Log.i("OrgImg.height",":"+optHeight);
	        
	        float resWidth = 0;
	        float resHeight = 0;
	        float resRitu = 0;
	        double baseRitu = 1.428;						// 240x320以上の基本拡大率（固定）
	        double img_ritu = 1.6;							// バナー画像サイズ（固定）
	        //double dp_ritu = (double)(dp_w / dp_h);		// ウィンドゥサイズの縦横比
	        	//Log.i("RITU",":"+baseRitu+"/"+img_ritu);
	        
	        // 240x320(最低サイズ)以下、イメージ画像リサイズ計算式
	        if (dp_w <= 240){
		        resWidth = (optWidth - dp_w) / (optWidth / dp_w);
		        	//Log.i("resWidth",":"+resWidth);
		        resRitu = (optWidth / resWidth);
		        	//Log.i("resRitu",":"+resRitu);
		        resHeight = (optHeight / resRitu);
		        	//Log.i("resHeight",":"+resHeight);
	        }
	        else{
	        	resWidth = dp_w / (float)baseRitu;
	        	resHeight = resWidth / (float)img_ritu;
	        	//resHeight = optHeight * (float)dp_ritu;
	        		//Log.i("resWidth/resHeight",":"+resWidth+"/"+resHeight);
	        }

            options.inJustDecodeBounds = false;
            // リソースからbitmapを作成
	        bmp = BitmapFactory.decodeResource(getResources(), resource_name,options);

	        // 画像　実読み込み
	        bmp = Bitmap.createScaledBitmap(bmp, (int)resWidth, (int)resHeight , true);  

	        	//Log.i("ScaleImg.width",":"+bmp.getWidth());
	        	//Log.i("ScaleImg.height",":"+bmp.getHeight());

            ImageView image = new ImageView(this);
	        image.setImageBitmap(bmp);
	        
	        //ImageView imageTest = new ImageView(this);
	        //imageTest.setImageResource(R.drawable.thumbnail);
	        
	        // サイト画像セット
	        inLinearLayout.addView(image, new LayoutParams(WC, WC));
	        
	        //setContentView(image, new LayoutParams(WC, WC));
	        //inLinearLayout.addView(new ImageResizeView(this, resource_name));
	        
	        linearLayout.addView(inLinearLayout);
	        
	        // サイトページ　INTENT遷移
	        Button button1 = new Button(this);
	        button1.setText("サイトページへ");
	        button1.setLayoutParams(new LinearLayout.LayoutParams(
	        	LinearLayout.LayoutParams.WRAP_CONTENT,
	        	LinearLayout.LayoutParams.WRAP_CONTENT));
	        button1.setOnClickListener(this);
	        linearLayout.addView(button1);

	        // Activity終了　プレビュー画面へ戻る
	        Button button3 = new Button(this);
	        button3.setText("プレビュー画面に戻って壁紙設定をする");
	        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,WC));
	        param2.setMargins(0, 30, 0, 30);
	        button3.setLayoutParams(param2);
	        button3.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                // クリック時の処理
	            	finish();
	            }
	        });
	        linearLayout.addView(button3);

/**	        
	        // アプリ内課金システムテキスト2
	        TextView textBilling2 = new TextView(this);
	        textBilling2.setText("有料版の購入");
	        textBilling2.setBackgroundColor(Color.parseColor("#666666"));
	        linearLayout.addView(textBilling2);

	        // リストビューテスト
	        ListView list  = new ListView(this);
	        try {
		        linearLayout.addView(list, new LayoutParams(WC, WC));
		        ArrayAdapter<String> arrayAdapter
		            = new ArrayAdapter<String>(this, R.layout.rowdata, data);
		        list.setAdapter(arrayAdapter);
		        // 選択する要素の位置の指定(今回は、3を指定 (0始まり))
		        list.setSelection(3);
		        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		        	@Override
		            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		                //ここに処理を書く
		        		//setupBilling();
		            }
		        });
	        }
	        catch (Exception e){
	        	Log.d("ArrayAdapter", "Failed.ArrayAdapter");
	        }
	        
	        // リストビューテスト2
	        // ListView に表示する文字列を生成
	        try{
		        final List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
	            Map<String, String> map = new HashMap<String, String>();
	            map.put("main", data[0]);
	            map.put("sub", subdata[0]);
	            list2.add(map);
		        SimpleAdapter  spladapter = new SimpleAdapter(this, list2, R.layout.rowtwdata,
		                new String[] { "main", "sub" }, new int[] { R.id.main, R.id.sub });
		        ListView listview2  = new ListView(this);
		        linearLayout.addView(listview2, new LayoutParams(WC, WC));
		        listview2.setAdapter(spladapter);
		        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		        	@Override
		            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		                //ここに処理を書く
		        		//setupBilling();
		            }
		        });
	        }
	        catch (Exception e){
	        	Log.d("SimpleAdapter", "Failed.SimpleAdapter");
	        }
	        
	        // Activity終了　プレビュー画面へ戻る2
	        Button button4 = new Button(this);
	        button4.setText("プレビュー画面に戻って壁紙設定をする");
	        button4.setLayoutParams(param2);
	        button4.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                // クリック時の処理
	            	finish();
	            }
	        });
	        linearLayout.addView(button4);
**/
	}

		//getPreferenceManager().setSharedPreferencesName(VenusTouchLiveWall.SHARED_PREFS_NAME);
		//addPreferencesFromResource(R.xml.wallpaper_settings);
		//getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        // 利用者
        //android.preference.Preference preference = findPreference("user");
        //((ChangePreference) preference).setImageResource(R.drawable.ic_launcher);
        //preference.setIntent(new Intent(MenuTop.this, UserInfo.class));
 
        // アプリ設定
        //preference = findPreference("setupApp");
        //((ChangePreference) preference).setImageResource(R.drawable.ic_launcher);
        //preference.setIntent(new Intent(MenuTop.this, SetupApp.class));
 
        // デザイン設定
        //preference = findPreference("setupDesign");
        //((ChangePreference) preference).setImageResource(R.drawable.ic_launcher);
        //preference.setIntent(new Intent(MenuTop.this, SetupDesign.class));

	public void onClick(View v) {
	      // クリック時の処理
		this.openSiteInfo(v);
	}
	
	public void openSiteInfo(View view) 
	{
		Intent intent = new Intent();
		intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://sexyv.jp/default.asp?d=507"));
		//intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
		startActivity(intent);
		Toast.makeText(this, "提供元サイトへ移動します", Toast.LENGTH_LONG).show();
	}
	
    public void onDestroyed(){
        if (bmp != null) {
            // Bitmapデータの解放
        	bmp.recycle();
        	bmp = null;
        }
    }

	
}
