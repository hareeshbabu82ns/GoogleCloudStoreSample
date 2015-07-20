package com.har.googlecloudstoresample;

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
import com.har.googlecloudstoragelib.IDatastoreEntity;
import com.har.googlecloudstoresample.sample.Department;
import com.har.googlecloudstoresample.sample.Student;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class StudentsFragment extends Fragment
    implements Response.ErrorListener, AsyncTaskRequest.AsyncListener<List<IDatastoreEntity>> {

  private static final String TAG = StudentsFragment.class.getSimpleName();

  ListView mListView;
  ArrayAdapter<IDatastoreEntity> mAdapter;

  Department mDept;
  OnStudentSelected mListener;

  public StudentsFragment() {
  }

  public static final StudentsFragment newInstance(Department dept) {
    final StudentsFragment fragment = new StudentsFragment();
    final Bundle bundle = new Bundle();
    bundle.putSerializable(Utils.EXTRA_DEPARTMENT, dept);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mDept = (Department) getArguments().getSerializable(Utils.EXTRA_DEPARTMENT);
    }
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
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_students, container, false);
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
          mListener.onStudentSelected(
              (Student) parent.getAdapter().getItem(position));
      }
    });
    Log.d(TAG, "onViewCreated");
  }

  @Override
  public void onErrorResponse(VolleyError error) {
    Toast.makeText(getActivity(), error.getMessage(),
        Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onResponse(List<IDatastoreEntity> response) {
    mAdapter.clear();
    mAdapter.addAll(response);
  }

  @Override
  public List<IDatastoreEntity> performRequest(AsyncTaskRequest<List<IDatastoreEntity>> request) throws Exception {
    return Student.getEntities(Student.class, mDept.buildKey().build());
  }

  public interface OnStudentSelected {
    void onStudentSelected(Student student);
  }
}
