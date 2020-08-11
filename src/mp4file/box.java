package mp4file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mp4file.Exceptions.unsupportedBoxExeption;

public class box {
    private int size;
    private final boxType type;
    private long longSize = 0;
    private long dataPointer;
    private Map<boxType, ArrayList<box>> child = new HashMap<boxType, ArrayList<box>>();
    private Map<boxType, ArrayList<fullBox>> childFull = new HashMap<boxType, ArrayList<fullBox>>();

    public box(RandomAccessFile file, long filePointer) throws IOException, unsupportedBoxExeption {
        // read data from pointer
        file.seek(filePointer);

        // read size and type as byte[]
        byte[] sizeByte = new byte[4];
        byte[] typeByte = new byte[4];

        file.read(sizeByte);
        file.read(typeByte);
        // covert byte[] to int and string
        this.size = bytesToInt(sizeByte);
        String typeStr = new String(typeByte);

        this.type = boxType.getType(typeStr);

        if (this.type == null){
            throw new unsupportedBoxExeption(typeStr);
        }

        // if size = 1 (use longSize save data size), read next 8 bytes
        if (isLongSize()){
            byte[] longSizeByte = new byte[8];
            file.read(longSizeByte);
            this.longSize = bytesToLong(longSizeByte);
        }

        // pointer is the start of data
        this.dataPointer = file.getFilePointer();
        if (containBox.contains(this.type.toString())){
            scanChildBox(file, this.dataPointer, this.size);
        }
    }

    private void scanChildBox(RandomAccessFile file, long filePointer, long maxSize) throws IOException, unsupportedBoxExeption {

        Map<boxType, ArrayList<? extends box>> child = new HashMap<boxType, ArrayList<? extends box>>();

        long startPoint = filePointer;

        byte[] sizeBytes = new byte[4];
        byte[] typeBytes = new byte[4];
        byte[] longerSizeBytes = new byte[8];
        boxType tempType;
        boolean isLongerSize = false;
        file.seek(filePointer);

        while(filePointer < file.length() && filePointer < startPoint + maxSize){
            file.read(sizeBytes);
            file.read(typeBytes);

            if (!boxType.contains(new String(typeBytes))){
                filePointer += bytesToInt(sizeBytes);
                continue;
            }

            tempType = boxType.getType(new String(typeBytes));

            if (bytesToInt(sizeBytes) == 1) {
                file.read(longerSizeBytes);
                isLongerSize = true;
            }

            if (fullboxType.contains(tempType.toString())){
                if (this.childFull.containsKey(tempType)){
                    this.childFull.get(tempType).add(new fullBox(file, filePointer));
                }else{
                    ArrayList<fullBox> temp = new ArrayList<fullBox>();
                    temp.add(new fullBox(file, filePointer));
                    this.childFull.put(tempType, temp);
                }
            }else{
                if (this.child.containsKey(tempType)){
                    this.child.get(tempType).add(new box(file, filePointer));
                }else{
                    ArrayList<box> temp = new ArrayList<box>();
                    temp.add(new box(file, filePointer));
                    this.child.put(tempType, temp);
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
    }



    public boolean isLongSize(){
        return this.size == 1;
    }

    public long getDataSize(){
        long dataSize;
        long headSize;
        if (isLongSize()){
            dataSize = this.longSize;
            headSize = 16;
        }else{
            dataSize = this.size;
            headSize = 8;
        }

        return dataSize - headSize;
    }

    public String getType(){
        return this.type.toString();
    }

    public long getSize(){
        if (isLongSize()){
            return this.longSize;
        }else{
            return this.size;
        }
    }

    public long getDataPointer(){
        return this.dataPointer;
    }

    public Map<boxType, ArrayList<box>> getChildBox(){
        return this.child;
    }

    public Map<boxType, ArrayList<fullBox>> getChildFullBox(){
        return this.childFull;
    }

    public void setDataPointer(long pointer){
        if (pointer > 0){
            this.dataPointer = pointer;
        }
    }

    public void setSize(int size){
        this.size = size;
    }

    public String toString(){
        String output = "type: %s, size: %d";
        return String.format(output, this.type, getSize());
    }

    public static int bytesToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }
}