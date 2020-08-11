package mp4file;

public enum fullboxType {
    mvhd("mvhd"),
    tkhd("tkhd"),
    mdhd("mdhd"),
    hdlr("hdlr"),
    vmhd("vmhd"),
    smhd("smhd"),
    dinf("dinf"),
    stsd("stsd"),
    stts("stts"),
    ctts("ctts"),
    stsc("stsc"),
    stsz("stsz"),
    stz2("stz2"),
    stco("stco"),
    co64("co64"),
    stss("stss");

    private String type;

    fullboxType(String type){
        this.type = type;
    }

    public String toString(){
        return this.type;
    }

    public boolean equals(String input){
        return this.type.equals(input);
    }

    public boolean equals(boxType input){
        return this.type.equals(input.toString());
    }

    public static fullboxType getType(String input){
        for (fullboxType types : fullboxType.values()){
            if (types.equals(input)){
                return types;
            }
        }
        return null;
    }

    public static boolean contains(String type){
        for (fullboxType types : fullboxType.values()){
            if (types.equals(type)){
                return true;
            }
        }
        return false;
    }
}
