package com.example.trendingtweets;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;











import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import android.R.drawable;
import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.os.AsyncTask;

public class MainActivity extends Activity{
	
	private String url = "http://api.whatthetrend.com/api/v2/trends.json";
	private ListView trending = null;
	private GestureDetector detect;
	private int count=0;
	private LiveCard mLiveCard = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		trending = (ListView) this.findViewById(R.id.trending);
		detect = createGestureDetector(this);
		loadlist(-1);
	}
	
	private GestureDetector createGestureDetector(Context context)
    {
        GestureDetector gestureDetector = new GestureDetector(context);
        //Create a base listener for generic gestures
        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
           
        	@Override
            public boolean onGesture(Gesture gesture) {
               
                handleGesture(gesture);

                // LONG_PRESS, SWIPE_DOWN, SWIPE_LEFT, SWIPE_RIGHT, SWIPE_UP,
                // TAP, THREE_LONG_PRESS, THREE_TAP, TWO_LONG_PRESS, TWO_SWIPE_DOWN,
                // TWO_SWIPE_LEFT, TWO_SWIPE_RIGHT, TWO_SWIPE_UP, TWO_TAP;

                if (gesture == Gesture.TAP) 
                {
                    handleGestureTap();                   
                    return true;
                } 
               
                else if (gesture == Gesture.TWO_TAP) 
                {
                    handleGestureDoubleTap();
                    return true;
                } 
               
                else if (gesture == Gesture.SWIPE_RIGHT) 
                {
                    handleGestureSwipeRight();
                    return true;
                } 
                
                else if (gesture == Gesture.SWIPE_LEFT) 
                {
                    handleGestureSwipeLeft();
                    return true;
                } // etc...
                
                return false;
            }
        });
        gestureDetector.setFingerListener(new GestureDetector.FingerListener() 
        {
            @Override
            public void onFingerCountChanged(int previousCount, int currentCount) 
            {
                // do something on finger count changes
               // Log.i("onFingerCountChanged()");
            }
        });
        
        gestureDetector.setScrollListener(new GestureDetector.ScrollListener() 
        {
            @Override
            public boolean onScroll(float displacement, float delta, float velocity) 
            {
                // do something on scrolling
              //  Log.i("onScroll()");
                // ????
                return false;
            }
        });
        
        return gestureDetector;
    }
	
	private void handleGestureTap()
    {
		
		this.openOptionsMenu();
		
    }
	
	


    private void handleGestureSwipeRight()
    {
        trending.scrollListBy(trending.getHeight()*-1);
    }

    private void handleGestureSwipeLeft()
    {
        trending.scrollListBy(trending.getHeight());
    }
    private void handleGestureDoubleTap()
    {
		setTitle("Now Trending - World");
		getActionBar().setIcon(R.drawable.ic_twitter);
	    count++;
	  
	    //world
	    if(count==0)
	    {	loadlist(0); }
	    
	    else if(count==1)
	    {					
	    	loadlist(23424748); 
	    	setTitle("Now Trending - Australia");
	    	getActionBar().setIcon(R.drawable.downunder);
	    }
	    
	    else if(count==2)
	    {					
	    	loadlist(23424775); 
	    	setTitle("Now Trending - Canada");
	    	getActionBar().setIcon(R.drawable.syrupeh);
	    }
	    
	    else if(count==3)
	    {	
	    	loadlist(23424853); 
			setTitle("Now Trending - India");
			getActionBar().setIcon(R.drawable.namo);
	    }
	    
	    else if(count==4)
	    {					
	    	loadlist(23424846); 
	    	setTitle("Now Trending - Indonesia");
	    	getActionBar().setIcon(R.drawable.indo);
	    }
	    
	    else if(count==5)
	    {					
	    	loadlist(23424803); 
	    	setTitle("Now Trending - Ireland");
	    	getActionBar().setIcon(R.drawable.potatoes);
	    }
	    
	    else if(count==6)
	    {	
	    	loadlist(23424853); 
			setTitle("Now Trending - Italy");
			getActionBar().setIcon(R.drawable.mario);
		}
	    
	    else if(count==7)
	    {					
	    	loadlist(23424908); 
	    	setTitle("Now Trending - Nigeria");
	    	getActionBar().setIcon(R.drawable.nigeria);
	    }
	    
	    
	    else if(count==8)
	    {	
	    	loadlist(23424922); 
			setTitle("Now Trending - Pakistan");
			getActionBar().setIcon(R.drawable.pakistan);
		}
	    
	    else if(count==9)
	    {					
	    	loadlist(23424934); 
	    	setTitle("Now Trending - Philippines");
	    	getActionBar().setIcon(R.drawable.philippines);
	    }
	    

	  
	    else if(count==10)
	    {				
	    	loadlist(23424868);
	    	getActionBar().setIcon(R.drawable.kris);
    		setTitle("Now Trending - South Korea");
    	}
	   
	    else if(count==11)
	    {
			loadlist(23424975); 
			setTitle("Now Trending - UK"); 
			getActionBar().setIcon(R.drawable.queen);
	    	
	    }
	    
	    else
	    {					
			loadlist(23424977);
			setTitle("Now Trending - USA"); 
			getActionBar().setIcon(R.drawable.chrisevans);
	    	count=-1;
	    }
	    
    	
    }
	
	 @Override
	public boolean onGenericMotionEvent(MotionEvent event)
	{
		 if (detect != null) 
		 {
			 return detect.onMotionEvent(event);
	     }
	        
		 return super.onGenericMotionEvent(event);
	}
	
	private void handleGesture(Gesture gesture)
    {
        //if(Log.D) Log.d("handleGesture() called with gesture = " + gesture);
        if(gesture != null) 
        {
        	showGestureInfoToast(gesture);
        }
    }
	
	private void showGestureInfoToast(Gesture gesture)
    {
        if(gesture != null) 
        {
            int duration = Toast.LENGTH_SHORT;
            String text = "Gesture:\n" + ((gesture == null) ? "" : gesture.toString());
            Toast toast = Toast.makeText(this, text, duration);
            //toast.show();
        }
    }
	 
	 private void loadlist(int woeid)
	 {
		 RequestTask gettwitter = new RequestTask()
		 {
				@Override
				protected void onPostExecute(String result) 
				{
					JSONObject obj = null;
					
					try 
					{
						obj = new JSONObject(result);
					} 
					catch (JSONException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					List<String> trendlist = new ArrayList<String>();
					try 
					{
						JSONArray trends = (JSONArray) obj.get("trends");
						for (int i = 0; i<trends.length(); i++)
						{
							JSONObject trend = (JSONObject) trends.get(i);
							trendlist.add((i+1) + ". " + (String) trend.get("name"));
						}
					} 
					catch (JSONException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// TODO Auto-generated method stub
					filllist(trendlist);
					
				}
			};
			
			
			
			String twitterurl = url;
			if(woeid>0)
				twitterurl = String.format("%s?woeid=%s", twitterurl, woeid);
			
			gettwitter.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, twitterurl);	
	 }
	
   

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
      //  openOptionsMenu();
    }


    @Override
    public void onOptionsMenuClosed(Menu menu) {
        // Nothing else to do, closing the activity.
       // finish();
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id)
		{
			case R.id.World:
				loadlist(0);
				setTitle("Now Trending - World");
				getActionBar().setIcon(R.drawable.ic_twitter);
				return true;
				
			case R.id.USA:
				loadlist(23424977);
				setTitle("Now Trending - USA"); 
				getActionBar().setIcon(R.drawable.chrisevans);
				return true;

			case R.id.India:
				loadlist(23424853); 
				setTitle("Now Trending - India");
				getActionBar().setIcon(R.drawable.namo);
				return true;

			case R.id.Ireland:
				loadlist(23424803); 
				setTitle("Now Trending - Ireland");
				getActionBar().setIcon(R.drawable.potatoes);
				return true;	
				
				
			case R.id.Italy:
				loadlist(23424853); 
				setTitle("Now Trending - Italy");
				getActionBar().setIcon(R.drawable.mario);
				return true;
			
			case R.id.Indonesia:
				loadlist(23424846); 
				setTitle("Now Trending - Indonesia");
				getActionBar().setIcon(R.drawable.indo);
				return true;	
			
			case R.id.Nigeria:
				loadlist(23424908); 
				setTitle("Now Trending - Nigeria");
				getActionBar().setIcon(R.drawable.nigeria);
				return true;	
				
			case R.id.Pakistan:
				loadlist(23424922); 
				setTitle("Now Trending - Pakistan");
				getActionBar().setIcon(R.drawable.pakistan);
				return true;	

			case R.id.Philippines:
				loadlist(23424934); 
				setTitle("Now Trending - Philippines");
				getActionBar().setIcon(R.drawable.philippines);
				return true;	
				
			case R.id.Australia:
				loadlist(23424748); 
				setTitle("Now Trending - Australia");
				getActionBar().setIcon(R.drawable.downunder);
				return true;	
				
			case R.id.South_Korea:
				loadlist(23424868);
	        	getActionBar().setIcon(R.drawable.kris);
	        	setTitle("Now Trending - South Korea");
				return true;
				
			case R.id.Canada:
				loadlist(23424775); 
				setTitle("Now Trending - Canada");
				getActionBar().setIcon(R.drawable.syrupeh);
				return true;
				
			case R.id.UK:
				loadlist(23424975); 
				setTitle("Now Trending - UK"); 
				getActionBar().setIcon(R.drawable.queen);
				return true;
				
				
				
			default:
				return super.onOptionsItemSelected(item);
		}
		
		
		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment 
	{

		public PlaceholderFragment() 
		{
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.fragment_main, container,false);
			return rootView;
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
		boolean handled = false;
		
		
		switch(keyCode){
		case KeyEvent.KEYCODE_DPAD_LEFT:
			trending.setSelection(trending.getSelectedItemPosition()+4);
			handled=true;
			break;
		case KeyEvent.KEYCODE_DPAD_CENTER:
			handled=true;
			break;
		}
		
		if(!handled)
			return super.onKeyUp(keyCode, event);
		else
			return true;
		
	}

	private void filllist(List<String> stuff)
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
	              android.R.layout.simple_list_item_1, android.R.id.text1, stuff);
		 trending.setAdapter(adapter);
	}
}
