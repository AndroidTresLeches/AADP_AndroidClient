package com.tresleches.aadp.interfaces;


/**
 * Interface to identify and perfomrming a Task.
 * Task can be any activity or fragment which needs Dialog for Progress. 
 * @author Parag Sagar
 *
 */
public interface AADPTask {
	
	public void performTask();

	public void performOfflineTask();

}
