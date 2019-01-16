package org.hideoutgroup.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 董文强
 * @Time 2019/1/16 21:16
 */
@Service
class SigningKeyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SigningKeyService.class);


    private Map<String,byte[]> keys;

    @PostConstruct
    public void init(){
        keys = new HashMap<>();
    }

    public Key getKey(String userId) {
        byte[] keyByte = keys.get(userId);
        if(keyByte == null){
            return null;
        }else{
            return unserialize(keyByte);
        }

    }

    public Boolean addKey(String userId, Key signingKey) {
        keys.put(userId,serialize(signingKey));
        return true;
    }




    /**
     * 序列化
     *
     * @param object
     * @return
     */
    private   byte[] serialize(Key object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    private   Key unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Key) ois.readObject();
        } catch (Exception e) {

        }
        return null;
    }
}
