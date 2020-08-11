package mp4file;

import mp4file.Exceptions.unsupportedBoxExeption;
import mp4file.moovChild.*;

import java.io.IOException;
import java.io.RandomAccessFile;

public class moov extends box {

    private mvhd mvhd;

    public moov(RandomAccessFile file, long filePointer) throws IOException, unsupportedBoxExeption {
        super(file, filePointer);
        this.mvhd = new mvhd(file, getChildFullBox().get(boxType.getType("mvhd")).get(0));
        getChildFullBox().remove(boxType.getType("mvhd"));
    }

    public mvhd getMvhd(){
        return this.mvhd;
    }
}
