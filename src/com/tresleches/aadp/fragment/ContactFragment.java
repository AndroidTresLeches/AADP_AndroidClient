package com.tresleches.aadp.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tresleches.aadp.R;
import com.tresleches.aadp.activity.VolunteerEventsActivity;
import com.tresleches.aadp.adapter.ContactArrayAdapter;
import com.tresleches.aadp.model.Contact;

public class ContactFragment extends Fragment {
	private ArrayAdapter<Contact> aContacts;
	private ArrayList<Contact> contacts;
	private String category;
	private Contact contact;

	public ContactFragment() {

	}

	public static ContactFragment newInstance(String category) {
		ContactFragment contactFragment = new ContactFragment();
		Bundle args = new Bundle();
		args.putString("category", category);
		contactFragment.setArguments(args);
		return contactFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contacts = new ArrayList<Contact>();
		aContacts = new ContactArrayAdapter(getActivity(), contacts);
		category = getArguments().getString("category");
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_contact, container, false);
		ListView lvContact = (ListView) v.findViewById(R.id.lvContact);
		lvContact.setAdapter(aContacts);
		getContacts(category);
		lvContact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent i = new Intent(getActivity(), VolunteerEventsActivity.class);
				contact = contacts.get(position);
				i.putExtra("contact", contact.getFirstName());
				startActivity(i);
				getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
		return v;
	}

	public void getContacts(String category) {
		// Define the class we would like to query
		ParseQuery<Contact> query = ParseQuery.getQuery(Contact.class);
		// Define our query conditions
		query.whereEqualTo("category", category);

		query.findInBackground(new FindCallback<Contact>() {
			public void done(List<Contact> objects, ParseException e) {
				if (e == null) {
					// The query was successful.
					addAll(objects);
				} else {
					// Something went wrong.
					Log.d("ERROR", e.toString());
				}
			}
		});
	}

	public void addAll(List<Contact> contacts) {
		aContacts.addAll(contacts);
	}
}
