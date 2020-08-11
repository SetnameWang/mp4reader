package mp4file;

import mp4file.Exceptions.unsupportedBoxExeption;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class filedecoder {
    private ftyp ftyp;
    private mdat mdat;
    private moov moov;
    private boolean isfMp4 = false;
    private Map<String, ArrayList<box>> others = new HashMap<String, ArrayList<box>>();

    /**
     * @param file randomAccessFile object of mp4 file
     * @throws IOException throw if file not fund or file pointer out of size of file
     */
    public filedecoder(RandomAccessFile file) throws IOException, unsupportedBoxExeption {
        // read ftyp first
        this.ftyp = new ftyp(file, 0);

        long filePointer = this.ftyp.getSize();
        if (filePointer == -1){
            filePointer = 0;
        }

        byte[] sizeBytes = new byte[4];
        byte[] typeBytes = new byte[4];
        byte[] longerSizeBytes = new byte[8];
        boolean isLongerSize = false;
        file.seek(filePointer);

        while(filePointer < file.length()){
            file.read(sizeBytes);
            file.read(typeBytes);

            if (box.bytesToInt(sizeBytes) == 1){
                file.read(longerSizeBytes);
                isLongerSize = true;
            }
            if ((new String(typeBytes)).equals("moov")){
                this.moov = new moov(file, filePointer);
            }else if ((new String(typeBytes)).equals("mdat")){
                this.mdat = new mdat(file, filePointer);
            }else{
                if (others.containsKey(new String(typeBytes))){
                    others.get(new String(typeBytes)).add(new box(file, filePointer));
                }else{
                    ArrayList<box> temp = new ArrayList<box>();
                    temp.add(new box(file, filePointer));
                    others.put(new String(typeBytes), temp);
                }
            }

            if (isLongerSize){
                filePointer += box.bytesToLong(longerSizeBytes);
            }else{
                filePointer += box.bytesToInt(sizeBytes);
            }

            file.seek(filePointer);

            isLongerSize = false;
        }

        if (this.moov.getChildBox().get(boxType.getType("mvex")) != null){
            this.isfMp4 = true;
        }
    }

    public boolean isfMp4(){
        return this.isfMp4;
    }

    public ftyp getFtyp(){
        return this.ftyp;
    }

    public moov getMoov(){
        return this.moov;
    }
}
