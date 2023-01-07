package kreasikode.ayonyolo.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;

import java.io.File;

import kreasikode.ayonyolo.config.Constant;

public class ImageChooser {

    private ResultData resultData;

    public void chooseImage() {
        try {

            File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "BARBER");

            if (!imageStorageDir.exists()) {
                imageStorageDir.mkdirs();
            }
            File file = new File(imageStorageDir + File.separator + "IMG_" + System.currentTimeMillis() + ".jpg");
            Uri mCapturedImageURI = Uri.fromFile(file);

            final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");

            Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});

            resultData = new ResultData(chooserIntent, Constant.FILECHOOSER_RESULTCODE, mCapturedImageURI);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ResultData {

        private final Intent intent;
        private final int resultCode;
        private final Uri uri;

        public ResultData(Intent intent, int resultCode, Uri uri) {
            this.intent = intent;
            this.resultCode = resultCode;
            this.uri = uri;
        }

        public Intent getIntent() {
            return intent;
        }

        public int getResultCode() {
            return resultCode;
        }

        public Uri getUri() {
            return uri;
        }
    }

    public ResultData getResultData() {
        return resultData;
    }
}
