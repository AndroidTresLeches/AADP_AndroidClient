package com.tresleches.aadp.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.tresleches.aadp.interfaces.AADPTask;

/**
 * An infrastructure Class manages following 
 * 1. Create a progress dialog box for an Activity or Fragment.
 * 2. Checks for Network Availability
 * @author Parag Sagar
 *
 */
public class AADPTaskManager {
	
	protected ProgressDialog dialog;
	protected AADPTask task ;
	protected Context context;
	
	public AADPTaskManager(AADPTask obj, Context context){
		this.task = obj;
		this.context= context;
	}
	
	public void execute() {
		//1
		onPreExecute();
		if (NetworkUtils.isNetworkAvailable(context)) {
			performTask();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			performOfflineTask();
		}
		onPostExecute();
	}
	

	/**
	 * This Method is called by Execute before processing of task starts. 
	 * Override this method if you want to change default behavior 
	 * Do call super - otherwise Progress Dialog wont behave  properly
	 */
	protected void onPreExecute() {
		
		dialog = new ProgressDialog(context);
		dialog.setMessage("Loading, please wait");
		dialog.setTitle("Loading ");
		dialog.show();
		dialog.setCancelable(false);
	}

	/**
	 * The Method is called by Execute when there is a Network
	 * Override this method in your AADPTaskManager Type in case you want to do multiple things 
	 * Also override this method if you have Multiple Tasks in one class (Activity/Fragment).
	 * Do not call super 
	 */

	protected Object performTask() {
		
		task.performTask();
		return null;
	}
	
	/**
	 * The Method is called by Execute when there is No Network
	 * Override this method in your AADPTaskManager Type in case you want to do multiple things 
	 * Also override this method if you have Multiple Tasks in one class (Activity/Fragment).
	 * Do not call super 
	 */
	protected void performOfflineTask() {
		task.performOfflineTask();
		
	}
	
	
	/**
	 * This Method is called by Execute when all processing is done. 
	 * Override this method if you want to change default behavior 
	 * Do call super - otherwise Progress Dialog wont behave  properly
	 */
	protected void onPostExecute(){
		
		if(dialog!=null) dialog.cancel();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		if(dialog!=null) dialog.cancel();
	}
	
}
