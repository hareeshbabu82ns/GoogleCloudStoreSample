package com.har.googlecloudstoresample.sample;

import com.google.api.services.datastore.DatastoreV1;
import com.google.api.services.datastore.DatastoreV1.Entity;
import com.google.api.services.datastore.client.DatastoreHelper;
import com.har.googlecloudstoragelib.BaseDatastoreEntity;
import com.har.googlecloudstoragelib.annotations.DatastoreEntity;

import java.util.Date;

import static com.google.api.services.datastore.client.DatastoreHelper.makeProperty;
import static com.google.api.services.datastore.client.DatastoreHelper.makeValue;

/**
 * Created by hareesh on 7/7/15.
 */
@DatastoreEntity(kind = Student.TABLE_KIND, kindParent = Department.TABLE_KIND)
public class Student extends BaseDatastoreEntity {
  public static final String TABLE_KIND = "student";
  public static final String COLUMN_NAME = "name";
  public static final String COLUMN_DATE_OF_BIRTH = "dob";
  public static final String COLUMN_DATE_OF_JOINING = "doj";
  public static final String COLUMN_DEPARTMENT = "dept";

  public String name;
  public Date dob;
  public Date doj;

  public Student() {
    super();
  }

  public Student(long parentID) {
    super(0, parentID);
  }

  public Student(long id, long parentID) {
    super(id, parentID);
  }

  @Override
  protected void buildProperties(Entity.Builder builder) {
    builder.addProperty(makeProperty(COLUMN_NAME, makeValue(name)))
        .addProperty(makeProperty(COLUMN_DATE_OF_BIRTH, makeValue(dob)))
        .addProperty(makeProperty(COLUMN_DATE_OF_JOINING, makeValue(doj)))
        .addProperty(makeProperty(COLUMN_DEPARTMENT, makeValue(mParentID)));
  }

  @Override
  public void setValue(String name, DatastoreV1.Value value) {
    switch (name) {
      case COLUMN_NAME:
        this.name = value.getStringValue();
        break;
      case COLUMN_DEPARTMENT:
        mParentID = value.getIntegerValue();
        break;
      case COLUMN_DATE_OF_BIRTH:
        dob = new Date(DatastoreHelper.getTimestamp(value));
        break;
      case COLUMN_DATE_OF_JOINING:
        doj = new Date(DatastoreHelper.getTimestamp(value));
        break;
    }
  }

  @Override
  public DatastoreV1.Value getValue(String name) {
    switch (name) {
      case COLUMN_NAME:
        return makeValue(this.name).build();
      case COLUMN_DATE_OF_BIRTH:
        return makeValue(dob.getTime()).build();
      case COLUMN_DATE_OF_JOINING:
        return makeValue(doj.getTime()).build();
      default:
        return super.getValue(name);
    }
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append(COLUMN_NAME).append(" :").append(name).append(" ; ");
    builder.append(COLUMN_DATE_OF_BIRTH).append(" :").append(dob);
    builder.append(COLUMN_DATE_OF_JOINING).append(" :").append(doj);
    builder.append(COLUMN_PARENT_KEY).append(" :").append(mParentID);
    return builder.toString();
  }
}
