package com.har.googlecloudstoresample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.har.googlecloudstoresample.sample.Department;

public class DepartmentsActivity extends AppCompatActivity
    implements DepartmentsFragment.OnDepartmentSelectedListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_departments);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_departments, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onDepartmentSelected(Department dept) {
    Intent intent = new Intent(this, StudentsActivity.class);
    intent.putExtra(Utils.EXTRA_DEPARTMENT, dept);
    startActivity(intent);
//    Toast.makeText(DepartmentsActivity.this, dept.name, Toast.LENGTH_SHORT).show();
  }
}
