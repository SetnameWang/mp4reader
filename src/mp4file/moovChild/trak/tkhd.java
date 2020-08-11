package mp4file.moovChild.trak;

import mp4file.Exceptions.unsupportedBoxExeption;

import java.io.IOException;
import java.io.RandomAccessFile;

public class tkhd extends mp4file.fullBox{

    public tkhd(RandomAccessFile file, long filePointer) throws IOException, unsupportedBoxExeption {
        super(file, filePointer);
    }
}
