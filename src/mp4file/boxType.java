package mp4file;

public enum boxType {
    ftyp("ftyp"),
    pdin("pdin"),
    moov("moov"),
        mvhd("mvhd"),
        trak("trak"),
            tkhd("tkhd"),
            tref("tref"),
            edts("edts"),
                elst("elst"),
            mdia("mdia"),
                mdhd("mdhd"),
                hdlr("hdlr"),
                minf("minf"),
                    vmhd("vmhd"),
                    smhd("smhd"),
                    hmhd("hmhd"),
                    nmhd("nmhd"),
                    dinf("dinf"),
                        dref("dref"),
                    stbl("stbl"),
                        stsd("stsd"),
                        stts("stts"),
                        ctts("ctts"),
                        stsc("stsc"),
                        stsz("stsz"),
                        stz2("stz2"),
                        stco("stco"),
                        co64("co64"),
                        stss("stss"),
                        stsh("stsh"),
                        padb("padb"),
                        stdp("stdp"),
                        sdtp("sdtp"),
                        sbgp("sbgp"),
                        sgpd("sgpd"),
                        subs("subs"),
    sidx("sidx"),
        mvex("mvex"),
            mehd("mehd"),
            trex("trex"),
        ipmc("ipmc"),
    moof("moof"),
        mfhd("mfhd"),
        traf("traf"),
            tfhd("tfhd"),
            trun("trun"),
            //sdtp("sdtp"),
            //sbgp("sbgp"),
            //subs("subs"),
    mfra("mfra"),
        tfra("tfra"),
        mfro("mfro"),
    mdat("mdat"),
    free("free"),
    skip("skip"),
        udta("udta");

    private String type;

    boxType(String type){
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
        for (boxType types : boxType.values()){
            if (types.equals(type)){
                return true;
            }
        }
        return false;
    }
}
