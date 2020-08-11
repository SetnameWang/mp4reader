package mp4file.moovChild;

import mp4file.Exceptions.unsupportedBoxExeption;

import java.io.IOException;
import java.io.RandomAccessFile;

public class mvhd {
    private long creation_time;
    private long modification_time;
    private int timescale;
    private long duration;

    public mvhd(RandomAccessFile file, mp4file.fullBox fullBox) throws IOException {
        file.seek(fullBox.getDataPointer());
        if (fullBox.getVersion() == 1){
            byte[] longByte = new byte[8];
            byte[] intByte = new byte[4];

            file.read(longByte);
            this.creation_time = mp4file.box.bytesToLong(longByte);
            file.read(longByte);
            this.modification_time = mp4file.box.bytesToLong(longByte);
            file.read(intByte);
            this.timescale = mp4file.box.bytesToInt(longByte);
            file.read(longByte);
            this.duration = mp4file.box.bytesToLong(longByte);
        }else{
            byte[] intByte = new byte[4];
            file.read(intByte);
            this.creation_time = mp4file.box.bytesToInt(intByte);
            file.read(intByte);
            this.modification_time = mp4file.box.bytesToInt(intByte);
            file.read(intByte);
            this.timescale = mp4file.box.bytesToInt(intByte);
            file.read(intByte);
            this.duration = mp4file.box.bytesToInt(intByte);
        }
    }

    public String toString(){
        String output = "creation_time: %s\nmodification_time: %s\ntimescale: %s\nduration:%s";
        return String.format(output, this.creation_time, this.modification_time, this.timescale, this.duration);
    }
}
