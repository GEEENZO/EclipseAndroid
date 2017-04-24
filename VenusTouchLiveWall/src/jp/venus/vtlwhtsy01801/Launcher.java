/**
 * LiveWallpaper Launcher And Preference Template
 * 
 * 
 * 
 */
package jp.venus.vtlwhtsy01801;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Launcher extends Activity implements OnClickListener {
	
	private Bitmap bmp;
	SharedPreferences pref;
	private String n;//
	private float canvasWidth;
	private float canvasHeight;
	private int bmpWidth;
	private String hys;//
	private String ik;
	private int bmpHeight;
	private float drow_h;
	private float drow_s;
	private String u;
	private String uts;//
	private float drow_w;
	private float drow_wBg;
	private float drow_hBg;
	private float drow_sBg;	
	private float widthScale;
	private String ibv;//
	private float heightScale;
	private float scale;
	private float bgScale;
	private String vo;
	private String i;
	private int bmpFWidth;
	private int bmpFHeight;
	private int ofsW;
	private float drawWidth;
	private String l;//
	private float drawHeight;
	private float zRenge;
	private String jj;
	private int drwChoice;				//描画サイズ状態の判定フラグ
	private boolean visibleAction;
	private String output;
	private String q;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		pref = 
				PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//getSharedPreferences(PREF_KEY, this.MODE_WORLD_WRITEABLE);
		q="D";jj="sgo";
		String chkdata = getApplicationContext().getDir("data", Context.MODE_PRIVATE).toString()+"/photos.zip";n="d";ibv="esA";ik=ibv;
		File ftemp = new File(chkdata);u=ibv+ik+n+u;q="s";jj=n+ik;
        if(!ftemp.exists()){
			hys="iC8";l="s";n=n+hys;hys="qws";ik=n+ibv;ibv="0";ik=ik;n=ik+l;jj=n+l;
			// 画像データの取得
			jj=ik+l;ik="user";String method = "";
			DataCheck(n);
			
			// リサイズ情報の取得
			imageResize();			

        } 
        
    	//int resource_name = R.drawable.bgblk;
    	String imgPath = getApplicationContext().getDir("data", Context.MODE_PRIVATE).toString()+"/photos/";
    	String resource_name = imgPath+"img_main.jpg";

        //imageResize();	
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
         
        //setContentView(new ImageResizeView(this, resource_name));
    	ScrollView scrollView = new ScrollView(this);
    	
		 LinearLayout linearLayout = new LinearLayout(this);
	        linearLayout.setOrientation(LinearLayout.VERTICAL);
	        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
	        setContentView(scrollView);

	        TextView textview = new TextView(this);
	        textview.setText(R.string.description);
	        linearLayout.addView(textview);
	        
		        //入れ子のレイアウトを作成
		        LinearLayout inLinearLayout = new LinearLayout(this);
		        inLinearLayout.setOrientation(LinearLayout.VERTICAL);
		        inLinearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		        
		        // WindowManager取得
		        WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
		        // Displayインスタンス生成
		        Display dp = wm.getDefaultDisplay();
		        // ディスプレイサイズ取得
		        int dp_w = dp.getWidth();
		        int dp_h = dp.getHeight();
		        double drow_w = 0;
		        // リサイズ画像の高さ
		        double drow_h = (dp_w / 2) * 3;
		        // 描画始点の高さ
		        //int drow_s = (dp_h - drow_h) / 2;
		        
		        final int WC = ViewGroup.LayoutParams.WRAP_CONTENT; 

	            //読み込み用のオプションオブジェクトを生成
	            BitmapFactory.Options options = new BitmapFactory.Options();
	            //この値をtrueにすると実際には画像を読み込まず、
	            //画像のサイズ情報だけを取得することができます。
	            options.inJustDecodeBounds = true;
	            options.inPreferredConfig = Bitmap.Config.RGB_565;
	            options.inPurgeable = true;
	            
	            // リソースからbitmapを作成
	            //BitmapFactory.decodeResource(getResources(), resource_name,options);
	            BitmapFactory.decodeFile(resource_name, options);
	            //Log.i("dp_w",":"+dp_w);
	            //Log.i("dp_h",":"+dp_h);
	            double imgw = options.outWidth;
	            double imgh = options.outHeight;
	            //Log.i("options.outWidth",":"+imgw);
	            //Log.i("options.outHeight",":"+imgh);
	            
	            if(dp_w > options.outWidth){
	            	//Log.i("dispLog",": 画像よりディスプレイが大きい");
	            	if(dp_w > (imgw*2)){
		            	drow_w = imgw;
		            	drow_h = imgh;
	            	}
	            	else{
		            	double i = dp_w/imgw;
		            	drow_w = imgw / i;
		            	drow_h = imgh / i;
	            	}
	            }
	            else if(dp_w == options.outWidth ){
	            	//Log.i("dispLog",": 画像とディスプレイが同じ");
	            	drow_w = imgw / 2;
	            	drow_h = imgh /2;
	            	//options.inSampleSize = 2;
	            }
	            else{
	            	//Log.i("dispLog",": 画像よりディスプレイが小さい");
	            	drow_w = ((dp_w / 2) / imgw) * (double)imgw;
	            	drow_h = ((dp_w / 2) / imgw) * (double)imgh;
	            	
	            	if(dp_w <= 240){
		            	drow_w = drow_w * 0.8;
		            	drow_h = drow_h * 0.8;
	            	}
	            }

	            //Log.i("drow_w",":"+drow_w);
	            //Log.i("drow_h",":"+drow_h);
	            options.inJustDecodeBounds = false;

            	//Log.i("dispLog",": 画像Read");
	            //bmp = BitmapFactory.decodeResource(getResources(), resource_name,options);
	            bmp = BitmapFactory.decodeFile(resource_name, options);
	            
                // 画像　実読み込み
	            Bitmap bitmap;
	            if(drow_w == bmp.getWidth() || drow_h == bmp.getHeight()){
	            	bitmap = bmp.copy(bmp.getConfig(), true);
	            }
	            else{
			        bitmap = Bitmap.createScaledBitmap(bmp, (int)drow_w, (int)drow_h , true);
	            }

	            ImageView image = new ImageView(this);
		        image.setImageBitmap(bitmap);
		        
	            if (bmp != null) {
		            // Bitmapデータの解放
//		            Log.d("Bitmap:", "データの解放");
		            bmp.recycle();
		            bmp = null;
	            }
		        //ImageView imageTest = new ImageView(this);
		        //imageTest.setImageResource(R.drawable.thumbnail);
		        
		        inLinearLayout.addView(image, new LayoutParams(WC, WC));
		        
		        //setContentView(image, new LayoutParams(WC, WC));
		        //inLinearLayout.addView(new ImageResizeView(this, resource_name));
		        
		        linearLayout.addView(inLinearLayout);

	        Button button1 = new Button(this);
	        button1.setText("ライブ壁紙一覧を開いて選択する");
	        button1.setLayoutParams(new LinearLayout.LayoutParams(
	        	LinearLayout.LayoutParams.MATCH_PARENT,
	        	LinearLayout.LayoutParams.WRAP_CONTENT));
	        button1.setOnClickListener(this);
	        linearLayout.addView(button1);
		
	        Button button2 = new Button(this);
	        button2.setText("閉じる");
	        button2.setLayoutParams(new LinearLayout.LayoutParams(
	        	LinearLayout.LayoutParams.WRAP_CONTENT,
	        	LinearLayout.LayoutParams.WRAP_CONTENT));
	        button2.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                // クリック時の処理
	            	finish();
	            }
	        });
	        linearLayout.addView(button2);
	        
	        scrollView.addView(linearLayout);
	}
	public void onClick(View v) {
	      // クリック時の処理
		this.openLivewallpaperList(v);
	}
	public void openLivewallpaperList(View view) 
	{
		Intent intent = new Intent();
		intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
		startActivity(intent);
		Toast.makeText(this, getString(R.string.app_name)  + " を選択して下さい", Toast.LENGTH_LONG).show();
	}
    public void onDestroyed(){
        if (bmp != null) {
            // Bitmapデータの解放
        	bmp.recycle();
        	bmp = null;
        }
    }
	private void DataCheck(String mac){
		//Log.d("DataCheck:", "STARTED");
		ZipAllState za = new ZipAllState();
		vo = mac;i="i";String tr = "str";
    	try {
    		// インターナルストレージ/data/data/pkg_name/app_data/
    		File outDir = getApplicationContext().getDir("data", Context.MODE_PRIVATE);
            if (!outDir.exists()) {
                outDir.mkdir();
            }
    		
	        File outFile = new File(outDir, "data.zip");
	        /** /data/data/pkg_name/app_data/data.zip **/
	        
	        String password = "h.kd8s9ikakqlf";//dfdsk09iudsksad";//h.kd8s9ikakqlf";//"vnf89esd";//"";
	        //String sdpath = Environment.getExternalStorageDirectory().getPath();
	        
	    	//File dir = new File(getApplicationContext().getDir("data", Context.MODE_PRIVATE) + "/photos");
	    	
            //za.extractZipFiles("photos.zip");//outFile.toString());
            
	    	AssetManager as = getApplicationContext().getResources().getAssets();//getResources().getAssets();
	 			BufferedInputStream bis = new BufferedInputStream(as.open("photos.zip",AssetManager.ACCESS_STREAMING));
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile));tr="type"+vo;vo="non";//imgpath
			
	    	byte[] buffer = new byte[1024*4];
	    	int len = 0;
	    	while ( (len = bis.read(buffer, 0, buffer.length)) > 0) {
	    	     bos.write(buffer, 0, len);
	    	}
	    	bos.flush();
	    	bos.close();
	    	bis.close();
	    	
            //ZipFile zipFile = new ZipFile(outFile);//outFile);//(zipFilePath);
            //if (zipFile.isEncrypted()) {
            //    zipFile.setPassword(password);
            //}
	    	mac = mac+i;uts="kel";jj="8bs";mac=mac+uts;uts=uts+i;
            output = getApplicationContext().getDir("data", Context.MODE_PRIVATE).toString()+"/photos"; //getFilesDir().toString()+"/photos";//  //getApplicationContext().getDir("data", Context.MODE_PRIVATE).toString();
            //zipFile.extractAll(output);
            
            String unmatch = mac;
            File filetemp = new File(output);
            File ecptFile = new File(output+"/dip.zip");
            if (!filetemp.exists()) {
                filetemp.mkdir();
            }
            
            String zippath = outFile.toString();//getApplicationContext().getDir("data", Context.MODE_PRIVATE).toString()+"/data.zip";//this.getDir("data", this.MODE_PRIVATE).getAbsolutePath()+"/data.zip";
            
            //暗号化
            //za.zipSecreter(output, outFile);
            
            // 複合化
            File outZip = new File(outDir,"photos.zip");
            if(!outZip.exists()){
                za.zipSPunisher(outFile, outZip.toString(),unmatch);
            }

            //メイン解凍処理
            za.zipArchiveFiles(outZip.toString(), output);

            //内部ディレクトリにコピーされたZIPの削除
            if (outFile.exists()) {
            	outFile.delete();
            }
            if (outZip.exists()) {
            	//outZip.delete();
            }
              
        } catch (IOException e) {
            Log.e("TAG", "IOException");
        } 
    	//catch (ZipException e) {
			// TODO 自動生成された catch ブロック
		//	e.printStackTrace();
	    //} 
    	catch (IllegalArgumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    	
	}
	
	private void imageResize(){
		
		//Log.d("imageResize:", "STARTED");
		
		int resId = R.drawable.bgblk;
		//Bitmap bitmap;

		pref = 
			PreferenceManager.getDefaultSharedPreferences(this);
		//pref.edit().putString("SaveString", "TEST").commit();
		
        // WindowManager取得
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        // Displayインスタンス生成
        Display dp = wm.getDefaultDisplay();

        //画像のサイズ情報だけを取得することができます。
        //読み込み用のオプションオブジェクトを生成
    	BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeResource(getResources(),resId, options);
        
        // キャンバスのサイズ
        canvasWidth = dp.getWidth();
        canvasHeight = dp.getHeight();
        //Log.d("Canvas Size:", "width="+canvasWidth+"/Height="+canvasHeight);
        
        // ビットマップのサイズ
        bmpWidth = options.outWidth;
        bmpHeight = options.outHeight;
        //Log.d("Bitmap Size:", "width="+bmpWidth+"/Height="+bmpHeight);

        // リサイズ画像の高さ
        drow_h = 0;//(canvasWidth / 2) * 3;
        // 描画始点の高さ
        drow_s = 0;//(canvasHeight - drow_h) / 2;       
        // リサイズ画像の幅
        drow_w = 0;//(drow_h / canvasHeight) * canvasWidth;
        //Log.d("WebLog Size:", "drow_h="+drow_h+"/drow_w="+drow_w+"/drow_s="+drow_s);
                                                
        //Log.d("Bitmap", "options");
        //今度は画像を読み込みたいのでfalseを指定
        options.inJustDecodeBounds = false;
        
        //Log.d("Bitmap", "context.getResources");
        //bitmap = BitmapFactory.decodeResource(getResources(),resId, options);
        
        // キャンバスが画像より大きい場合、劣化を防ぐ為リサイズを行わない
        //if(canvasWidth > bmpWidth){
        //    canvasWidth = bmpWidth;
        //    drow_h = bmpHeight;
        //    Log.d("Draw Scale:","画像よりキャンバスが大きい");
        //    
        //    drwChoice = 0;
        //}
        //}
        if(canvasWidth < bmpWidth || canvasWidth > bmpWidth){
            //Log.d("Draw Scale:","キャンバスより画像が大きい");

            // 拡大率の算出	
            widthScale =  canvasWidth / bmpWidth;
            heightScale =  canvasHeight / bmpHeight;
            //Log.d("Scale SizeWH:","widthScale:"+widthScale+"/heightScale:"+heightScale);
            
            //小さい方の拡大率を参照する(2013/12/24修正：幅拡大率に準拠する）
            scale = widthScale;//Math.min(widthScale, heightScale);
            //背景用の拡大率は大きい方を参照する
            bgScale = Math.max(widthScale, heightScale);
            
            if(canvasWidth < bmpWidth){
            	scale = bgScale;
            }
            //算出した拡大率から実画像を縮小する
            drow_w = bmpWidth * scale;
            drow_h = bmpHeight * scale;
            drow_s = (canvasWidth - drow_w) / 2;
            
            drow_wBg = bmpWidth * bgScale;
            drow_hBg = bmpHeight * bgScale;
            drow_sBg = (canvasWidth - drow_wBg) / 2;

            //Log.d("Scale Size:","scale= "+scale+" /drow_w= "+drow_w+" /drow_h= "+drow_h);
            //Log.d("BGScale Size:","bgscale= "+bgScale+" /drow_wBg= "+drow_wBg+" /drow_hBg= "+drow_hBg);

            // 画像　実読み込み
            /**  
             * bitmap = Bitmap.createScaledBitmap(bitmap, (int)drow_w, (int)drow_h , true);
             **/
            
            bmpFWidth = bmpWidth;
            bmpFHeight = bmpHeight;
//            Log.d("ReSize Scale:", "drow_h="+drow_h+"/drow_w="+drow_w+"/drow_s="+drow_s);

            // 表示オフセット値の取得
            ofsW = (int)(canvasWidth-drow_w)/2;
            //ofsW = (int)(canvasHeight-drow_h)/2;
            if (ofsW > 0){
//            Log.d("OFFset Size:",""+ofsW);
            }
                        
            //画像とキャンバスが違うサイズの判定
            //drwChoice = 1;
        }
        else{
            //Log.d("Draw Scale:","画像とキャンバスが同じサイズ");
            // 画像　実読み込み
            /** 
             * bitmap = Bitmap.createScaledBitmap(bitmap, (int)canvasWidth, (int)canvasHeight , true);
             */

            //bmpFWidth = bitmap.getWidth();
            // = bitmap.getHeight();
            bmpFWidth = bmpWidth;
            bmpFHeight = bmpHeight;

            //Log.d("FIX キャンバスサイズ:","WIDTH="+canvasWidth);
            //Log.d("FIX キャンバスサイズ:","HEIGHT="+canvasHeight);
            //Log.d("FIX 描画サイズ:","WIDTH="+bmpFWidth);
            //Log.d("FIX 描画サイズ:","HEIGHT="+bmpFHeight);
            
            drow_w = bmpWidth;
            drow_h = bmpHeight;

            //画像とキャンバスが同じサイズの判定
            //drwChoice = 2;
        }                    
        
        //Log.d("BitmapFinal Size:", "width="+bmpFWidth+"/Height="+bmpFHeight);

        drawWidth	= canvasWidth;
        drawHeight	= canvasHeight;
		//Log.i("onSurfaceCreated", "座標変換情報1：　drawWidth= "+drawWidth+" / drawHeight= "+drawHeight);
		
		// X,Y 座標それぞれの拡大率を算出
		if(240 != drawWidth){
			widthScale	= drawWidth / 240;
			heightScale	= drawHeight / 320;
		}else{
			widthScale = 1;
			heightScale = 1;
		}
		
		//描画始点調整
		if(240 == drawWidth){
            drow_s = -54;//(bmpHeight/2-canvasHeight)/2; 427-320/2
            drwChoice = 1;
		}
		else if(320 == drawWidth){
			drow_s = -45;//(bmpHeight/2-canvasHeight)/2; 569-480/2
			drwChoice = 2;
		}
		//等倍
		else if(480 == drawWidth){
			drwChoice = 3;
		}
		else if(540 == drawWidth){
			drwChoice = 4;
		}
		else{
			drwChoice = 5;
		}
		//Log.d("BitmapSize:", "width="+bmpWidth+"/Height="+bmpHeight);
		//Log.i("onSurfaceCreated", "座標変換情報2：　widthScale= "+widthScale+" / heightScale= "+heightScale);

        //座標の±値の取得
        if(canvasWidth != 240){
        	zRenge += (drawWidth - 240)/10;
        }else{
        	zRenge = zRenge;
        }
        
        resId = 0;
        options = null;
        
		//Log.i("onSurfaceCreated", "座標変換情報3：　zRenge= "+zRenge);
		
        // ガベージクリア
        System.gc();
        
        pref.edit().putFloat("canvasWidth", canvasWidth).commit();
        pref.edit().putFloat("canvasHeight", canvasHeight).commit();
        pref.edit().putInt("bmpWidth", bmpWidth).commit();
        pref.edit().putInt("bmpHeight", bmpHeight).commit();
        
		pref.edit().putFloat("drawWidth", drawWidth).commit();
		pref.edit().putFloat("drawHeight", drawHeight).commit();
		pref.edit().putFloat("drow_w", drow_w).commit();
		pref.edit().putFloat("drow_h", drow_h).commit();
		pref.edit().putFloat("drow_s", drow_s).commit();
		pref.edit().putFloat("drow_wBg", drow_wBg).commit();
		pref.edit().putFloat("drow_hBg", drow_hBg).commit();
		pref.edit().putFloat("drow_s", drow_s).commit();
		pref.edit().putFloat("zRenge", zRenge).commit();
		pref.edit().putInt("ofsW", ofsW).commit();
		//pref.edit().putBoolean("setup", setup).commit();
		
		pref.edit().putFloat("widthScale", widthScale).commit();
		pref.edit().putFloat("heightScale", heightScale).commit();
		pref.edit().putInt("drwChoice", drwChoice).commit();
		visibleAction = false;
		pref.edit().putBoolean("visibleAction", visibleAction).commit();
		//boolean set_type = false;
		//pref.edit().putBoolean("set_type", set_type).commit();
		int visit = 0;
		pref.edit().putInt("visit", visit).commit();
		
    	//Log.i("pref", "--canvasWidth :"+canvasWidth);
    	//Log.i("pref", "--canvasHeight :"+canvasHeight);
    	//Log.i("pref", "--bmpWidth :"+bmpWidth);
    	//Log.i("pref", "--bmpHeight :"+bmpHeight);
    	//Log.i("pref", "--drawWidth :"+drawWidth);
    	//Log.i("pref", "--drawHeight :"+drawHeight);
    	//Log.i("pref", "--drow_w :"+drow_w);
    	//Log.i("pref", "--drow_h :"+drow_h);
    	//Log.i("pref", "--drow_s :"+drow_s);
    	//Log.i("pref", "--drow_wBg :"+drow_wBg);
    	//Log.i("pref", "--drow_hBg :"+drow_hBg);

    	//Log.i("pref", "--zRenge :"+zRenge);
    	//Log.i("pref", "--ofsW :"+ofsW);
    	//Log.i("pref", "--drwChoice :"+drwChoice);
    	//Log.i("pref", "--imgpath :"+output);
    	
        //Log.d("onImageResize", "Finalize Cleared");
        
	}

	
}