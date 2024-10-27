package com.wang.rpc.core.serialization;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianSerializerInput;
import com.caucho.hessian.io.HessianSerializerOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Hessian2 implements RpcSerialization{

    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        if (obj == null) {
            throw new NullPointerException();
        }

        byte[] result;
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            HessianSerializerOutput hessianOutput = new HessianSerializerOutput(os);
            hessianOutput.writeObject(obj);
            hessianOutput.flush();
            result = os.toByteArray();
        }

        return result;
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        if (data == null) {
            throw  new NullPointerException();
        }
        T result;

        try(ByteArrayInputStream is = new ByteArrayInputStream(data)) {
            HessianSerializerInput hessianInput = new HessianSerializerInput(is);
            result = (T) hessianInput.readObject(clz);
        }
        return result;
    }
}
