package mp4file;

import mp4file.Exceptions.unsupportedBoxExeption;

import java.io.IOException;
import java.io.RandomAccessFile;

public class fullBox extends box{
    private int version;
    private byte[] flags = new byte[3];


    public fullBox(RandomAccessFile file, long filePointer) throws IOException, unsupportedBoxExeption {
        super(file, filePointer);

        if (!fullboxType.contains(getType())){
            throw new unsupportedBoxExeption(getType());
        }

        file.seek(super.getDataPointer());
        byte[] versionByte = new byte[1];

        file.read(versionByte);
        this.version = versionByte[0] & 0xFF;
        file.read(this.flags);

        super.setDataPointer(file.getFilePointer());
    }

    public int getVersion(){
        return this.version;
    }


    public long getDataSize(){
        return super.getDataSize() - 4;
    }
}
