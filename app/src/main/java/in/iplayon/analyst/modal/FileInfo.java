package in.iplayon.analyst.modal;

import android.net.Uri;

/**
 * Created by shalinibr on 12/6/17.
 */

public class FileInfo {
    private String title;
    private Uri uri;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public boolean equals(Object o) {
        if (!(o instanceof FileInfo)) {

            return false;
        }
        FileInfo other = (FileInfo) o;
        if(title != null)
            return title.equalsIgnoreCase(other.getTitle());
        return false;
    }

    @Override
    public String toString() {
        return this.title;
    }
    public int hashCode() {
        return title.hashCode();
    }
}
