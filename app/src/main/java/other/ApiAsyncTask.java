package other;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import model.MainActivity;

/**
 * An asynchronous task that handles the Drive API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class ApiAsyncTask extends AsyncTask<Void, Void, Void> {
    private MainActivity mActivity;

    /**
     * Constructor.
     * @param activity MainActivity that spawned this task.
     */
    public ApiAsyncTask(MainActivity activity) {
        this.mActivity = activity;
    }

    /**
     * Background task to call Drive API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected Void doInBackground(Void... params) {
        try {
            File file = mActivity.getService().files().get("1euyHlY_MKSznGcPh7itNCayl_-Z_8ouhWFtInxWqoXc").setFields("exportLinks").execute();
            InputStream is = downloadFile(mActivity.getService(), file);
            if(is != null)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                try
                {
                    while((line = reader.readLine()) != null)
                    {
                        System.out.println(line);
                    }
                }catch(IOException io)
                {
                    io.printStackTrace();
                }
            }
        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {

            System.out.println(availabilityException.getConnectionStatusCode());

        } catch (UserRecoverableAuthIOException userRecoverableException) {
            mActivity.startActivityForResult(
                    userRecoverableException.getIntent(),
                    mActivity.getRequestAuthorization());

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Download a file's content.
     *
     * @param service Drive API service instance.
     * @param file Drive File instance.
     * @return InputStream containing the file's content if successful,
     *         {@code null} otherwise.
     */
    public static InputStream downloadFile(Drive service, File file) {
        String downloadUrl = file.getExportLinks().get("text/csv");
        System.out.println(downloadUrl);
        if (downloadUrl != null && downloadUrl.length() > 0) {
            try {
                HttpResponse resp = service.getRequestFactory().buildGetRequest(new GenericUrl(downloadUrl)).execute();
                return resp.getContent();
            } catch (IOException e) {
                // An error occurred.
                e.printStackTrace();
                return null;
            }
        } else {
            // The file doesn't have any content stored on Drive.
            return null;
        }
    }
}