package com.dashmash.aravind.codingmart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import java.util.Arrays;

public class WritePostActivity extends AppCompatActivity implements View.OnClickListener
{
	
	EditText postText;
	
	Button postButton, logoutButton;
	
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView( R.layout.activity_write_post );
		
		
		postText = (EditText) findViewById( R.id.write_post_text );
		
		postButton = (Button) findViewById( R.id.post_button );
		
		LoginManager.getInstance().logInWithPublishPermissions( this, Arrays.asList( "publish_actions" ) );
		
		
	}
	
	@Override
	public void onClick( View v )
	{
		
		switch ( v.getId() )
		{
			
			case R.id.post_button:
				post();
				break;
			
			
		}
		
	}
	
	private void post()
	{
		
		Toast.makeText( this, "Posting to facebook...", Toast.LENGTH_SHORT ).show();
		
		
		String userGeneratedText = postText.getText().toString();
		
		
		if ( userGeneratedText == null || userGeneratedText.equals( "" ) )
		{
			
			
			Toast.makeText( this, "Enter some text.", Toast.LENGTH_SHORT ).show();
			
		}
		else
		{
			
			Bundle params = new Bundle();
			params.putString( "message", userGeneratedText );
			
			
			/* make the API call */
			new GraphRequest(
					AccessToken.getCurrentAccessToken(),
					"/me/feed",
					params,
					HttpMethod.POST,
					new GraphRequest.Callback()
					{
						public void onCompleted( GraphResponse response )
						{
							
							
							Log.d( "WritePost", "onCompleted: " + response.getRawResponse() );
							
							if ( response != null )
							{
								Toast.makeText( WritePostActivity.this, "Posted successfully", Toast.LENGTH_SHORT ).show();
							}
							else
							{
								Toast.makeText( WritePostActivity.this, "Error posting", Toast.LENGTH_SHORT ).show();
							}
							
							
							finish();
						}
						
						
					}
			).executeAsync();
			
		}
	}
	
	
}
