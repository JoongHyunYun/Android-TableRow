package com.project.make.format;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.make.ListActivity;
import com.project.make.album.MultiPhotoSelectActivity;

import com.project.make.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FormatActivity extends Activity {
	
	String name="",check="";
	int cnt=0;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_format);
		
		name = getIntent().getStringExtra("Name");
		check= getIntent().getStringExtra("check");
		//Toast.makeText(getApplicationContext(), name+"\\"+check, Toast.LENGTH_SHORT).show();
		
		final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;
		
		/*** Rows 1 ***/
		map = new HashMap<String, String>();
		map.put("ImageID", "A pan");
		map.put("ImageDesc", "1000x1000");
		map.put("ImagePath", "pic_a");
		MyArrList.add(map);
		
		/*** Rows 2 ***/
		map = new HashMap<String, String>();
		map.put("ImageID", "B pan");
		map.put("ImageDesc", "1312x1000");
		map.put("ImagePath", "pic_b");
		MyArrList.add(map);	
		
		/*** Rows 3 ***/
		map = new HashMap<String, String>();
		map.put("ImageID", "C pan");
		map.put("ImageDesc", "704x1000");
		map.put("ImagePath", "pic_c");
		MyArrList.add(map);
		
		/***** Update Button *****/
		Button up_btn=(Button)findViewById(R.id.updateBtn);
		
		if("update".equals(check)){
			up_btn.setVisibility(View.VISIBLE);
			
			up_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					Uri u = Uri.parse("http://localhost:8080/web/application/Mobile/Apk/Album.apk");
					i.setData(u);
					startActivity(i);
				}
			});
		}else{	
			up_btn.setVisibility(View.INVISIBLE);
		}
		/**** 내서재 Button ****/
		Button list_btn=(Button)findViewById(R.id.listBtn);
		list_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if("bin".equals(name)){
					Toast.makeText(getApplicationContext(), "ID값이 없습니다.", Toast.LENGTH_SHORT).show();
				}else{
					Intent in = new Intent(FormatActivity.this,ListActivity.class);
					in.putExtra("Name", name);
					startActivity(in);
				}
			}
		});
				
		 // listView1
        final ListView lstView1 = (ListView)findViewById(R.id.listView1); 
       // lstView1.setDivider(null);	
        lstView1.setAdapter(new ImageAdapter(this,MyArrList));

        // OnClick
        final AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        lstView1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				final int position, long id) {
				
                View layout = inflater.inflate(R.layout.custom_fullimage_dialog,
                        (ViewGroup) findViewById(R.id.layout_root));
               
                ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
                final EditText title_text = (EditText) layout.findViewById(R.id.titleText); 
				int ResID = getResources().getIdentifier(MyArrList.get(position).get("ImagePath"), "drawable", FormatActivity.this.getPackageName());
				image.setImageResource(ResID);
				
                imageDialog.setIcon(android.R.drawable.btn_star_big_on);	
        		imageDialog.setTitle("Type : " + MyArrList.get(position).get("ImageDesc"));
                imageDialog.setView(layout);
                
                imageDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                	@Override
					public void onClick(DialogInterface dialog, int which) {
                		
                		String panName = MyArrList.get(position).get("ImageDesc").toString();
                		String title = title_text.getText().toString();
                		
                		if(title.isEmpty()){
                			Toast.makeText(getApplicationContext(), "제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                		}else{
	                    	EnterAlbum(panName,title);
	                    	dialog.dismiss();
                		}
                    	
                    }
                });
                
                imageDialog.setNegativeButton(R.string.canceler, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					//	Toast.makeText(FormatActivity.this, "취소", Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}
				});
                
                imageDialog.create();
                imageDialog.show(); 
                imageDialog.setCancelable(false);
		    	
			}
		});
	}
       
	public class ImageAdapter extends BaseAdapter 
    {
        private Context context;
        private ArrayList<HashMap<String, String>> MyArr = new ArrayList<HashMap<String, String>>();

        public ImageAdapter(Context c, ArrayList<HashMap<String, String>> list) 
        {
            context = c;
            MyArr = list;
        }
 
        public int getCount() {
            return MyArr.size();
        }
 
        public Object getItem(int position) {
            return position;
        }
 
        public long getItemId(int position) {
            return position;
        }
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.activity_column, null); 
			}
		 
			// ColImage listview 에 row 해당
			ImageView imageView = (ImageView) convertView.findViewById(R.id.ColImgPath);
			imageView.getLayoutParams().height = 250;
			imageView.getLayoutParams().width = 250;
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			int ResID = context.getResources().getIdentifier(MyArr.get(position).get("ImagePath"), "drawable", context.getPackageName());
			imageView.setImageResource(ResID);
				
			// ColPosition
			TextView txtPosition = (TextView) convertView.findViewById(R.id.ColImgID);
			txtPosition.setPadding(10, 0, 0, 0);
			txtPosition.setText("ID : " + MyArr.get(position).get("ImageID"));
			
			// ColPicname
			TextView txtPicName = (TextView) convertView.findViewById(R.id.ColImgDesc);
			txtPicName.setPadding(50, 0, 0, 0);
			txtPicName.setText("Type : " + MyArr.get(position).get("ImageDesc"));

			return convertView;
				
		}

    } 
	
public void EnterAlbum(String panName,String title){
		
		String pan="";
		
		Intent i = new Intent(FormatActivity.this, MultiPhotoSelectActivity.class);
		
		if(panName=="1000x1000"){
			pan = "1000,1000";
		}else if(panName=="1312x1000"){
			pan = "1312,1000";
		}else if(panName=="704x1000"){
			pan = "704,1000";
		}
	    
		i.putExtra("Name", name);
		i.putExtra("PanType",pan);
		i.putExtra("Title", title);
		
		//Toast.makeText(getApplicationContext(), pan+"::"+title+"::"+name, Toast.LENGTH_SHORT).show();
    	startActivity(i);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			if(cnt==1){
				//Toast.makeText(getApplicationContext(), ""+cnt, Toast.LENGTH_SHORT).show();
				moveTaskToBack(true);
				finish();
				//android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0); //그냥 재부팅
			}else{
				cnt++;
				Toast.makeText(getApplicationContext(), "빠르게 2번 클릭시 종료됩니다.", Toast.LENGTH_SHORT).show();
			   return true;
			}
			//break;
		}
		return super.onKeyDown(keyCode, event);
	}
}		
        
