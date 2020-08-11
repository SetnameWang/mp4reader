package mp4file.Exceptions;

public class unsupportedBoxExeption extends Exception{
    public unsupportedBoxExeption(String error){
        super("unsupported box [" + error + "], this mp4 file is not supported yet");
    }
}
