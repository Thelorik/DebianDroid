package com.debian.debiandroid.content;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.debian.debiandroid.ItemDetailFragment;
import com.debian.debiandroid.ListDisplayFragment;
import com.debian.debiandroid.R;
import com.debian.debiandroid.apiLayer.DFTP;

public class DFTPFragment extends ItemDetailFragment {
	
	private DFTP dftp;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.dftp_item_detail, container, false);
  		
    	getSherlockActivity().getSupportActionBar().setTitle(getString(R.string.dftp));
    	
    	ArrayList<String> dftpScripts = new ArrayList<String>(Arrays.asList(getString(R.string.new_packages), 
    			getString(R.string.removed_packages), getString(R.string.deferred_packages)));
    	
    	dftp = new DFTP(getSherlockActivity());
    	
    	ListView listview = (ListView) rootView.findViewById(R.id.dftpListView);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getSherlockActivity(),
				R.layout.listchild, new ArrayList<String>(dftpScripts));
		
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				// start listdisplayfragment with the dftp results as arguments
				new SearchInfoTask().execute(item);
			}
		});
		
        return rootView;
    }
	
	class SearchInfoTask extends AsyncTask<String, Void, Void> {
		private ProgressDialog progressDialog;
		String title="", header=""; 
		ArrayList<ArrayList<String>> items = new ArrayList<ArrayList<String>>();
		
		protected void onPreExecute() {
			   super.onPreExecute();
			   progressDialog = ProgressDialog.show(getSherlockActivity(), 
					   getString(R.string.searching), getString(R.string.searching_info) + ". " + getString(R.string.please_wait) + "...", true, false);  
			}
			
			protected Void doInBackground(String... params) {
				if( params[0].equals(getString(R.string.new_packages)) ) {
					items = dftp.getNewPackages();
					header = items.get(0).size() + " " + getString(R.string.new_packages);
					title = getString(R.string.new_packages);
				} else if( params[0].equals(getString(R.string.removed_packages)) ) {
					items = dftp.getRemovedPackages();
					header = items.get(0).size() + " " + getString(R.string.removed_packages);
					title = getString(R.string.removed_packages);
				} else if( params[0].equals(getString(R.string.deferred_packages)) ) {
					items = dftp.getDeferredPackages();
					header = items.get(0).size() + " " + getString(R.string.deferred_packages) ;
					title = getString(R.string.deferred_packages);
				}
				return null;
			}
			
			protected void onPostExecute (Void result) {
				progressDialog.dismiss();
				ItemDetailFragment fragment = new ListDisplayFragment();
				
				Bundle arguments = new Bundle();
				arguments.putString(ListDisplayFragment.LIST_HEADER_ID, header);
				arguments.putString(ListDisplayFragment.LIST_TITLE_ID, title);
				arguments.putSerializable(ListDisplayFragment.LIST_ITEMS_ID, items);
				
		        fragment.setArguments(arguments);
				
		    	getSherlockActivity().getSupportFragmentManager().beginTransaction()
		    	.replace(R.id.item_detail_container, fragment).addToBackStack(null).commit();
			}
	}
}
