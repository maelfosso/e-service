package org.pasteur_yaounde.e_service.abstrait;

import java.io.File;

/**
 * Created by malko on 16/08/16.
 */
public abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String albumName);
}
