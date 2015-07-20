package com.har.googlecloudstoresample.datastore;

import com.google.api.client.util.Lists;
import com.google.api.services.datastore.DatastoreV1;
import com.google.api.services.datastore.client.DatastoreException;
import com.har.googlecloudstoragelib.BaseDatastoreEntity;
import com.har.googlecloudstoragelib.DatastoreHandler;
import com.har.googlecloudstoresample.sample.Department;

import java.util.List;

/**
 * Created by hareesh on 12/07/2015.
 */
public class DatastoreHelper {
  public static List<Department> getDepartments() throws DatastoreException {
    final List<DatastoreV1.Entity> entities = DatastoreHandler.newQueryBuilder()
        .setKind(Department.TABLE_KIND)
        .runQueryGetEntities();
    final List<Department> departments = Lists.newArrayList();
    for (DatastoreV1.Entity entity : entities) {
      final Department dept = (Department) BaseDatastoreEntity.buildFromEntity(
          entity, Department.class);
      if (dept != null)
        departments.add(dept);
    }
    return departments;
  }

}
