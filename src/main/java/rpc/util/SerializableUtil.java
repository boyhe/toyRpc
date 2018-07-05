package rpc.util;


import java.io.*;

public class SerializableUtil {
    public static <T> byte[] encode(T obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        return bos.toByteArray();
    }

    public static Object decode(byte[] msg) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(msg);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }

}
