package com.har.googlecloudstoresample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.har.asyncvolleylib.AsyncTaskRequest;
import com.har.asyncvolleylib.Volley;
import com.har.googlecloudstoragelib.BaseDatastoreEntity;
import com.har.googlecloudstoragelib.IDatastoreEntity;
import com.har.googlecloudstoresample.sample.Department;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DepartmentsFragment extends Fragment
    implements Response.ErrorListener, AsyncTaskRequest.AsyncListener<List<IDatastoreEntity>> {

  private static final String TAG = DepartmentsFragment.class.getSimpleName();

  ListView mListView;
  ArrayAdapter<IDatastoreEntity> mAdapter;
  OnDepartmentSelectedListener mListener;

  public DepartmentsFragment() {

  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof OnDepartmentSelectedListener) {
      mListener = (OnDepartmentSelectedListener) activity;
    } else {
      throw new IllegalArgumentException(
          "Activity must implement DepartmentSelectedListener");
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_departments, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mListView = (ListView) view.findViewById(R.id.listView);
    mAdapter = new ArrayAdapter<>(getActivity().getBaseContext(),
        android.R.layout.simple_list_item_1);
    mListView.setAdapter(mAdapter);
    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListener != null)
          mListener.onDepartmentSelected(
              (Department) parent.getAdapter().getItem(position));
      }
    });
    Log.d(TAG, "onViewCreated");
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Volley.getAsyncQueue().add(
        new AsyncTaskRequest<List<IDatastoreEntity>>(this)
            .setAsyncListener(this));
    Log.d(TAG, "onActivityCreated");
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public void onErrorResponse(VolleyError error) {
    Toast.makeText(this.getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
    error.printStackTrace();
  }

  @Override
  public void onResponse(List<IDatastoreEntity> response) {
    mAdapter.clear();
    mAdapter.addAll(response);
  }

  @Override
  public List<IDatastoreEntity> performRequest(AsyncTaskRequest<List<IDatastoreEntity>> request) throws Exception {
    return BaseDatastoreEntity.getEntities(Department.class);
  }

  public interface OnDepartmentSelectedListener {
    void onDepartmentSelected(Department dept);
  }
}
