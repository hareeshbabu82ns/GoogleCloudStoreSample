package com.har.googlecloudstoresample.sample;

import com.google.api.services.datastore.DatastoreV1;
import com.google.api.services.datastore.DatastoreV1.Entity;
import com.har.googlecloudstoragelib.BaseDatastoreEntity;
import com.har.googlecloudstoragelib.annotations.DatastoreEntity;

import static com.google.api.services.datastore.client.DatastoreHelper.makeProperty;
import static com.google.api.services.datastore.client.DatastoreHelper.makeValue;

/**
 * Created by hareesh on 7/7/15.
 */
@DatastoreEntity(kind = Department.TABLE_KIND)
public class Department extends BaseDatastoreEntity {
  public static final String TABLE_KIND = "department";
  public static final String COLUMN_NAME = "name";
  public static final String COLUMN_DESCRIPTION = "description";

  public String name;
  public String description;

  public Department(long id) {
    super(id);
  }

  public Department() {
    super();
  }

  @Override
  public void setValue(String name, DatastoreV1.Value value) {
    switch (name) {
      case COLUMN_NAME:
        this.name = value.getStringValue();
        break;
      case COLUMN_DESCRIPTION:
        description = value.getStringValue();
        break;
    }
  }

  @Override
  public DatastoreV1.Value getValue(String name) {
    switch (name) {
      case COLUMN_NAME:
        return makeValue(this.name).build();
      case COLUMN_DESCRIPTION:
        return makeValue(description).build();
      default:
        return super.getValue(name);
    }
  }

  @Override
  public void buildProperties(Entity.Builder builder) {
    builder.addProperty(makeProperty(COLUMN_NAME, makeValue(name)))
        .addProperty(makeProperty(COLUMN_DESCRIPTION, makeValue(description)));
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append(COLUMN_NAME).append(" :").append(name).append(" ; ");
    builder.append(COLUMN_DESCRIPTION).append(" :").append(description);
    return builder.toString();
  }
}
