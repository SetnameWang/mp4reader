package mp4file;

import mp4file.Exceptions.unsupportedBoxExeption;

import javax.print.DocFlavor;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ftyp extends box {

    private String major_brand;
    private int minor_version;
    private String[] compatible_brands;

    public ftyp(RandomAccessFile file, long filePointer) throws IOException, unsupportedBoxExeption {
        super(file, filePointer);

        if (!super.getType().equals("ftyp")){
            this.major_brand = "mp41";
            this.minor_version = 0;
            this.compatible_brands = new String[1];
            this.compatible_brands[0] = "mp41";
            super.setSize(-1);
            return;
        }

        file.seek(super.getDataPointer());

        byte[] brandBytes = new byte[4];
        byte[] versionByte = new byte[4];
        byte[] compatibleByte = new byte[4];

        file.read(brandBytes);
        file.read(versionByte);

        this.major_brand = new String(brandBytes);
        this.minor_version = bytesToInt(versionByte);

        compatible_brands = new String[((int) super.getDataSize() - 8) / 4];

        for(int i = 0; i < ((int) super.getDataSize() - 8) / 4; i++){
            file.read(compatibleByte);
            this.compatible_brands[i] = new String(compatibleByte);
            compatibleByte = new byte[4];
        }
    }

    public String toString(){
        String output = "major_brand: %s\nminor_version: %d\ncompatible_brands: %s";
        StringBuilder compatible = new StringBuilder();
        for(String brands : compatible_brands){
            compatible.append(brands).append(", ");
        }
        return String.format(output, this.major_brand, this.minor_version, compatible.toString());
    }
}
