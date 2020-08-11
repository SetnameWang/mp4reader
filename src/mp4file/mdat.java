package mp4file;

import mp4file.Exceptions.unsupportedBoxExeption;

import java.io.IOException;
import java.io.RandomAccessFile;

public class mdat extends box{
    public mdat(RandomAccessFile file, long filePointer) throws IOException, unsupportedBoxExeption {
        super(file, filePointer);

    }
}
