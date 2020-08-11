import mp4file.Exceptions.unsupportedBoxExeption;
import mp4file.filedecoder;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class reader {
    private RandomAccessFile file;
    private filedecoder filedecoder;
    public debug debug;

    public static void main(String[] var){
        String path = "res/video.mp4";
        try {
            // reader
            reader fileReader = new reader(path);

            fileReader.getFile().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public reader(String path) throws IOException, unsupportedBoxExeption {
        this.file = new RandomAccessFile(new File(path), "r");
        this.filedecoder = new filedecoder(this.file);


        this.debug = new debug(true);
    }

    public filedecoder getFiledecoder() {
        return this.filedecoder;
    }

    public RandomAccessFile getFile(){return this.file;}
}
