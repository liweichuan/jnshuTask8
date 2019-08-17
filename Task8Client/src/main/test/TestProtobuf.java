import com.google.protobuf.InvalidProtocolBufferException;
import com.jnshu.pojo.PersonOuterClass;


public class TestProtobuf {
    public static void main(String[] args) {
        System.out.println("===== 构建一个Person模型开始 =====");
        PersonOuterClass.Person.Builder personbuilder = PersonOuterClass.Person.newBuilder();
        personbuilder.setId(1);
        personbuilder.setName("李伟川");
        personbuilder.setStudentId("5507");
        personbuilder.setType("java工程师");
        personbuilder.setUpdateTime(111055500);
        personbuilder.setCreateTime(111022532);
        personbuilder.setUpdateBy("李伟川");
        personbuilder.setCreateBy("李伟川");

        PersonOuterClass.Person person = personbuilder.build();
        System.out.println(person.toString());
        System.out.println("===== 构建Person模型结束 =====");

        System.out.println("===== Person Byte 开始=====");
        for(byte b : person.toByteArray()){
            System.out.print(b);
        }
        System.out.println("\n" + "bytes长度" + person.toByteString().size());
        System.out.println("===== Person Byte 结束 =====");

        System.out.println("===== 使用Person 反序列化生成对象开始 =====");
        PersonOuterClass.Person person1 = null;
        try {
            person1 = PersonOuterClass.Person.parseFrom(person.toByteArray());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        System.out.print(person1.toString());
        System.out.println("===== 使用Person 反序列化生成对象结束 =====");

    }
}
