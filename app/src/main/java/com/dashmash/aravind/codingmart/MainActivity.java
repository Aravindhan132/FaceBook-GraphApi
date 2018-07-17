package com.dashmash.aravind.codingmart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
	
	private ShareDialog shareDialog;
	private String name, surname, imageUrl;
	private String TAG = "MainActivity";
	
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView( R.layout.activity_main );
		
		
		Bundle inBundle = getIntent().getExtras();
		name = inBundle.getString( "name" );
		surname = inBundle.getString( "surname" );
		imageUrl = inBundle.getString( "imageUrl" );
		
		
		TextView nameView = (TextView) findViewById( R.id.nameAndSurname );
		nameView.setText( "" + name + " " + surname );
		
		
		CircleImageView imageView = (CircleImageView) findViewById( R.id.profileImage );
		
	
		
		Picasso.with(this).load(imageUrl).into(imageView);
	}
	
	
	@Override
	public void onClick( View view )
	{
		switch ( view.getId() )
		{
			
			case R.id.fb_logo:
			case R.id.share:
				share();
				break;
			
			case R.id.getPosts:
				getPosts();
				break;
			
			
			case R.id.write:
				writePost();
				break;
			
			case R.id.logout:
				logout();
				break;
		}
	}
	
	private void writePost()
	{
		
		startActivity( new Intent( MainActivity.this, WritePostActivity.class ) );
		
	}
	
	private void share()
	{

	}
	
	
	private void getPosts()
	{
		new GraphRequest(
				AccessToken.getCurrentAccessToken(), "/me/posts", null, HttpMethod.GET,
				new GraphRequest.Callback()
				{
					public void onCompleted( GraphResponse response )
					{
						//Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                        Intent i=new Intent(MainActivity.this,Post.class);
                        startActivity(i);
						Log.e( TAG, response.toString() );
					}
				}
		).executeAsync();
	}
	
	
	private void logout()
	{
		LoginManager.getInstance().logOut();
		Intent login = new Intent( MainActivity.this, LoginActivity.class );
		startActivity( login );
		finish();
	}
}
