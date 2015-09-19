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
    Klass klass = Klass.getRootAsKlass(bb);
    StringBuilder sb = new StringBuilder();
    toJson(klass, sb);
    }

  static void toJson(Klass kls, StringBuilder sb){
    sb.append("{\nname: \"").append(kls.name()).append("\",");
    sb.append("\nstudents: [");
    final int N = kls.studentsLength();
    Person p;
    int i = 0;
    for(; i < N; i++){
      sb.append("\n");
      p = kls.students(i);
      toJson(p, sb);
      sb.append(",");
         }
    if(i > 0){
      sb.deleteCharAt(sb.length()-1);
         }
    sb.append("\n],");
    sb.append("\nteacher: ");
    toJson(kls.teacher(), sb);
    System.out.println(sb.toString());
     }

  static void toJson(Person person, StringBuilder sb){
    sb.append("{name: \"").append(person.name()).append("\", ")
    .append("\ngender: \"");
    switch(person.gender()){
    case Gender.Male:
      sb.append("male\"");
      break;
    case Gender.Female:
      sb.append("female\"");
      break;
         }
    sb.append(",\nage: ").append(person.age()).append("\n}");
     }
}
