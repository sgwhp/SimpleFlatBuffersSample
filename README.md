# SimpleFlatBuffersSample
## FlatBuffers相关资料

 1. 项目主页：<https://github.com/google/flatbuffers>
 2. 详细介绍：<http://google.github.io/flatbuffers/>
 
据说FlatBuffers（一下简称fb）是一个高效的序列化与反序列化工具，连facebook也用上了它。接下来就看看这个东西到底怎么使用。
项目主页里也没有拎包入住的jar包，up主花了一定时间才弄明白。废话不多说，直接上干货。
## 1、编译
首先把整个源码下载下来，接下来第一部要做的就是把编译出flatc工具来，它会帮我们生成相关代码。有关building的官方介绍猛击：<http://google.github.io/flatbuffers/md__building.html>。以ubuntu为例，如果没安装cmake的，先执行：
`sudo apt-get install cmake`
然后cd到源码的父目录，执行：

```
cmake 目录名称
make
```
生成flatc文件后就表示编译完了。
## 2、定义Model
新建一个文件夹，把刚才编译生成的flact和源码中的java目录拷进去。
先把需要序列化的对象结构给定义好。fb使用idl来定义，官方的简单例子：<http://google.github.io/flatbuffers/md__schemas.html>
我们自己定义一个新的对象（TestObj.fbs）吧：

```
namespace my.test;//包名

attribute "priority";

/** 性别 */
enum Gender:byte (bit_flags) { Male = 0, Female = 1, }

/**人物*/
table Person {
  name:string;
  gender:Gender;
  age:int;
}

/**班级*/
table Klass {
  name:string (id: 0);
  students:[Person] (id: 1);
  teacher:Person (id: 2);
}

root_type Klass;
```
上面就定义了一个班级对象，其中有名称、学生列别、老师这三个成员变量，而学生和老师又是人物对象。
为毛不直接用java或者其他语言来定义呢？看完下一步就知道了。
## 3、生成代码
以java为例，源项目中有一个generate_code.sh文件，这就是生成相关代码的脚本（语法介绍：<http://google.github.io/flatbuffers/md__compiler.html>）。因为我们只需要生成java，所以可以去掉一些没用的参数。简化为：

```
./flatc -j --gen-mutable --no-includes TestObj.fbs
./flatc -b --schema TestObj.fbs
```
执行完毕后会生成my目录，里面就是根据TestObj.fbs生成的java类。分别包括Klass.java、Person.java、Gender.java。这些对象都生成了很多取值、赋值的方法。了解Android Parcel的看到这种代码可能就不那么陌生，这两种模式挺像的。都是在序列化的时候把成员按照一定顺序打包到buffer里面，然后一股脑写出去。反序列化的时候再根据传过来的buffer和成员的顺序读取对应的值。当然细节层面上肯定会更复杂一些，这里暂不深入分析。总之，我们现在已经有model了。
## 4、堆代码
为了验证，我们先创建一个Klass对象，然后序列化写到文件中，再从文件读取序列化的数据，反序列化成Klass对象，最后把它输出。

```
import java.io.*;
import java.nio.ByteBuffer;
import my.test.*;
import com.google.flatbuffers.FlatBufferBuilder;

public class Test{

  public static void main(String[] args){
    FlatBufferBuilder fbb = new FlatBufferBuilder(1);
    int klassName = fbb.createString("class a");
    int personName1 = fbb.createString("Wall-e");
    int personName2 = fbb.createString("Eva");
    int teacherName = fbb.createString("grass");
    int stu = Klass.createStudentsVector(fbb, new int[]{
      Person.createPerson(fbb, personName1, Gender.Male, 2)
      , Person.createPerson(fbb, personName2, Gender.Female, 1)
        });
    int teacher = Person.createPerson(fbb, teacherName, Gender.Female, 0);
    Klass.startKlass(fbb); 
    Klass.addName(fbb, klassName);
    Klass.addStudents(fbb, stu);
    //this will throw an exception
    //klass.addTeacher(fbb, Person.createPerson(fbb, teacherName, Gender.Female, 0));
    Klass.addTeacher(fbb, teacher);
    int kls = Klass.endKlass(fbb);
    Klass.finishKlassBuffer(fbb, kls);
    try {
       DataOutputStream os = new DataOutputStream(new FileOutputStream(
                                           "klassdata.kls"));
       os.write(fbb.dataBuffer().array(), fbb.dataBuffer().position(), fbb.offset());
       os.close();
    } catch(java.io.IOException e) {
       System.out.println("FlatBuffers test: couldn't write file");
       return;
    }

    File file = new File("klassdata.kls");
    byte[] data = null;
    RandomAccessFile f = null;
    try{
      f = new RandomAccessFile(file, "r");
      data = new byte[(int)f.length()];
      f.readFully(data);
      f.close();
    } catch(IOException e){
      System.out.println("read data failed");
      return;
    }
    ByteBuffer bb = ByteBuffer.wrap(data);
    Klass klass = Klass.getRootAsKlass(bb)
    toJson(klass);
}
```
编译执行这个java代码吧，记得要引用之前生成的my.test包，源项目也有一个编译执行java代码的脚本文件，稍微修改一下就可以使用了：

```
javac -classpath ${testdir}/java:${testdir} Test.java
java -classpath ${testdir}/java:${testdir} Test
```
最后可以看到屏幕上输出：
```
{
name: "class a",
students: [
{name: "Wall-e", 
gender: "male",
age: 2
},
{name: "Eva", 
gender: "female",
age: 1
}
],
teacher: {name: "grass", 
gender: "female",
age: 0
}
```
大功告成。
