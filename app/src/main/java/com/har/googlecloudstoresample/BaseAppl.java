package com.har.googlecloudstoresample;

import android.app.Application;

import com.har.asyncvolleylib.Volley;
import com.har.googlecloudstoragelib.DatastoreHandler;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by hareesh on 12/07/2015.
 */
public class BaseAppl extends Application {
  public static final String DATASTORE_DATASET = "devotional-media";
  public static final String DATASTORE_PARTITION_ID = "media";
  public static final String DATASTORE_SERVICE_ACCOUNT = "232635178029-0ql70o67vtsk3j2ulljv2cr2pj12o5ok@developer.gserviceaccount.com";
  private static final String DATASTORE_HOST = "http://localhost:8080";

  @Override
  public void onCreate() {
    super.onCreate();

    //initiate Volley
    Volley.getAsyncQueue(this);

    //initiate Google Cloud Datastore
    try {
//      DatastoreHandler.get(this, DATASTORE_HOST, DATASTORE_DATASET,
      DatastoreHandler.get(this, DATASTORE_DATASET,
          null, DATASTORE_SERVICE_ACCOUNT, R.raw.devotional_media_p12);
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
