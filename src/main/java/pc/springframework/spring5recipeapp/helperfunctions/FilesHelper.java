package pc.springframework.spring5recipeapp.helperfunctions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilesHelper {

    public static Byte[] byteToObject(byte[] file) {
        Byte[] byteObjects = new Byte[file.length];
        int i = 0;
        for (byte b : file)
            byteObjects[i++] = b; //Autoboxing
        return byteObjects;
    }

    public static byte[] byteObjectToByte(Byte[] file){
        byte[] byteArray = new byte[file.length];
        int i = 0;

        for (Byte wrappedByte : file){
            byteArray[i++] = wrappedByte; //auto unboxing
        }
        return byteArray;
    }


}
