package mp4file;

public enum containBox{
    stbl("stbl"),
    moov("moov"),
    trak("trak"),
    mdia("mdia"),
    minf("minf"),
    dinf("dinf"),
    edts("edts"),
    mvex("mvex"),
    moof("moof"),
    traf("traf"),
    mfra("mfra"),
    skip("skip");

    private String type;

    containBox(String type){
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

    public static boxType getType(String input){
        for (boxType types : boxType.values()){
            if (types.equals(input)){
                return types;
            }
        }
        return null;
    }

    public static boolean contains(String type){
        for (containBox types : containBox.values()){
            if (types.equals(type)){
                return true;
            }
        }
        return false;
    }
}
